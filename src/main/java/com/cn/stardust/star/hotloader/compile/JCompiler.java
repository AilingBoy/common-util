package com.cn.stardust.star.hotloader.compile;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * Description: com.cn.stardust.star
 * Created by Oracle on 2019/6/12 19:16
 */
public class JCompiler {

    /**
     * 编译 .java 内容为 .class内容
     *
     * @param file       待编译的.java文件
     * @param outPutPath .class文件输出位置
     * @return
     */
    @Deprecated
    public static String compiler(File file, String outPutPath) {
        outPutPath = null == outPutPath ?
                JCompiler.class.getResource("/").getPath() : outPutPath;
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager stdManager = compiler.getStandardFileManager(null, null, null);
        Iterable it = stdManager.getJavaFileObjects(file);
        JavaCompiler.CompilationTask task = compiler.getTask(null, stdManager, null, Arrays.asList("-d", outPutPath),
                null, it);
        if (task.call()) {
            return "";
        }
        return null;
    }

    /**
     * 内存中编译，热加载执行
     *
     * @param javaName
     * @param javaSrc
     * @return
     */
    public static Map<String, byte[]> compiler(String javaName, String javaSrc) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager stdManager = compiler.getStandardFileManager(null, null, null);
        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {
            JavaFileObject javaFileObject = MemoryJavaFileManager.makeStringSource(javaName, javaSrc);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, Arrays.asList(javaFileObject));
            if (task.call()) {
                return manager.getClassBytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}