package com.cn.stardust.star.hotloader.classload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.ConcurrentHashMap;

/**
 * https://github.com/oraclexing
 * <p>
 *  自定义类加载器用于加载动态编译的类
 *
 * @author stardust
 * @version 1.0.0
 *
 */
public class FileClassLoader extends ClassLoader {

    /**
     * 保存所有热加载的类
     */
    private static ConcurrentHashMap<String, Class<?>> clazzMap = new ConcurrentHashMap<>();

    public FileClassLoader() {
    }

    public FileClassLoader(ClassLoader parent) {
        super(parent);
    }

    /**
     * 从该加载器中加载指定类
     *
     * @param className
     * @return
     */
    public static Class<?> getClass(String className) {
        return clazzMap.get(className);
    }

    /**
     * 从.class文件加载类
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Files.copy(new File(name).toPath(), outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            byte[] bytes = outputStream.toByteArray();
            Class<?> c = this.defineClass(name, bytes, 0, bytes.length);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    /**
     * 直接从字节数组加载
     *
     * @return
     * @throws Exception
     */
    public Class<?> loadClass(String className, byte[] bytes) throws Exception {
        Class<?> c ;
        try {
            c = this.defineClass(className, bytes, 0, bytes.length);
            clazzMap.put(className, c);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        c = super.findClass(className);
        clazzMap.put(className, c);
        return c;
    }
}