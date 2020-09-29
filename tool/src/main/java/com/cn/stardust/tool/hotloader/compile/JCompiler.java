package com.cn.stardust.tool.hotloader.compile;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 * 编译器，编译.java源码为.class字节码
 *
 * @author stardust
 * @version 1.0.0
 *
 */
public class JCompiler {

    /**
     * 内存中编译，返回类名称与字节码映射
     *
     * @param javaName .java 文件名
     * @param javaSrc .java 文件内容
     * @return 类名称 到 byte[] 映射集合
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