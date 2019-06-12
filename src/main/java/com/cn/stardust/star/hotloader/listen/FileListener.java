package com.cn.stardust.star.hotloader.listen;

import com.cn.stardust.star.hotloader.basedo.Live;
import com.cn.stardust.star.hotloader.classload.FileClassLoader;
import com.cn.stardust.star.hotloader.compile.JCompiler;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.nio.file.Files;
import java.util.Map;

public class FileListener {

    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    public final static String EXT = ".java";

    public void listen(String filePath) throws Exception {
        FileFilter filter = FileFilterUtils.and(FileFilterUtils.trueFileFilter());
        FileAlterationObserver filealtertionObserver = new FileAlterationObserver(filePath, filter);
        filealtertionObserver.addListener(new FileAlterationListenerAdaptor() {
            @Override
            public void onDirectoryCreate(File directory) {
                // TODO Auto-generated method stub
                System.out.println("onDirectoryCreate");
                super.onDirectoryCreate(directory);
            }

            @Override
            public void onDirectoryDelete(File directory) {
                // TODO Auto-generated method stub
                System.out.println("onDirectoryDelete");
                super.onDirectoryDelete(directory);
            }

            @Override
            public void onFileChange(File file) {
                // TODO Auto-generated method stub
                System.out.println("onFileChange-------------->" + file.getName());
                try {
                    run(compile(file));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.onFileChange(file);
            }

            @Override
            public void onFileCreate(File file) {
                System.out.println("onFileCreate" + file.getAbsoluteFile());
                try {
                    if(file.getAbsolutePath().endsWith(EXT)){
                        run(compile(file));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.onFileCreate(file);
            }

            @Override
            public void onFileDelete(File file) {
                // TODO Auto-generated method stub
                System.out.println("onFileDelete----------->" + file.getAbsolutePath());
                super.onFileDelete(file);
            }

            @Override
            public void onStart(FileAlterationObserver observer) {
                // TODO Auto-generated method stub
                super.onStart(observer);
            }
        });
        FileAlterationMonitor monitor = new FileAlterationMonitor(1000);
        monitor.addObserver(filealtertionObserver);
        monitor.start();
    }

    /**
     * 编译.java文件
     *
     * @param file 待编译.java文件
     * @return 类名称
     * @throws Exception
     */
    private String compile(File file) throws Exception {
        Files.copy(file.toPath(), byteArrayOutputStream);
        Map<String, byte[]> resultMap = JCompiler.compiler(file.getName(), byteArrayOutputStream.toString());
        byteArrayOutputStream.reset();
        FileClassLoader fileClassLoader = new FileClassLoader();
        String className = null;
        for (String key : resultMap.keySet()) {
            fileClassLoader.loadClass(key, resultMap.get(key));
            if (!key.contains("$")) {
                className = key;
            }
        }
        return className;
    }

    /**
     * 及时执行修改后的文件
     *
     * @param className
     * @throws Exception
     */
    private void run(String className) throws Exception {
        Class clazz = FileClassLoader.getClass(className);
        if (Live.class.isAssignableFrom(clazz)) {
            Live live = (Live) clazz.newInstance();
            live.run();
        }
    }
}