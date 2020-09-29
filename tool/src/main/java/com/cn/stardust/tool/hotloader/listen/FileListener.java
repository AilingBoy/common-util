package com.cn.stardust.tool.hotloader.listen;

import com.cn.stardust.tool.hotloader.basedo.Live;
import com.cn.stardust.tool.hotloader.classload.FileClassLoader;
import com.cn.stardust.tool.hotloader.compile.JCompiler;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.nio.file.Files;
import java.util.Map;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 * 文件监听器，监听指定目录下的文件变化
 *
 * @author stardust
 * @version 1.0.0
 *
 */
public class FileListener {

    public final static String EXT = ".java";
    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    /**
     * 文件监听周期,单位毫秒
     */
    private static long interval = 1000;

    /**
     * 启动监听
     *
     * @param filePath 待监听的文件目录
     * @param interval 监听周期
     * @throws Exception
     */
    public void listen(String filePath, Long interval) throws Exception {
        FileFilter filter = FileFilterUtils.and(FileFilterUtils.trueFileFilter());
        FileAlterationObserver observer = new FileAlterationObserver(filePath, filter);
        observer.addListener(new FileAlterationListenerAdaptor() {
            @Override
            public void onDirectoryCreate(File directory) {
                System.out.println("onDirectoryCreate");
                super.onDirectoryCreate(directory);
            }

            @Override
            public void onDirectoryDelete(File directory) {
                System.out.println("onDirectoryDelete");
                super.onDirectoryDelete(directory);
            }

            @Override
            public void onFileChange(File file) {
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
                    if (file.getAbsolutePath().endsWith(EXT)) {
                        run(compile(file));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.onFileCreate(file);
            }

            @Override
            public void onFileDelete(File file) {
                System.out.println("onFileDelete----------->" + file.getAbsolutePath());
                super.onFileDelete(file);
            }

            @Override
            public void onStart(FileAlterationObserver observer) {
                super.onStart(observer);
            }
        });
        if (null == interval || interval < 1) {
            interval = FileListener.interval;
        }
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval);
        monitor.addObserver(observer);
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
            /**
             * 跳过实现{@link Live}接口的内部类,仅仅返回外部类的全名
             */
            if (!key.contains("$")) {
                className = key;
            }
        }
        return className;
    }

    /**
     * 及时执行修改后的文件
     *
     * @param className 类的名称
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