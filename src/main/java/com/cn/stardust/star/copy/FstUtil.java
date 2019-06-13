package com.cn.stardust.star.copy;


import org.nustaq.serialization.FSTConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Description: com.cn.stardust.star
 * Created by Oracle on 2019/6/12 19:16
 *
 * <p>
 * 序列化工具类,支持多线程下操作
 * 快速序列化反序列化Util
 * 通过序列化实现对象的深度复制
 * </p>
 */
public class FstUtil {


    private static FSTConfiguration fstConfiguration;

    private static ThreadLocal<FSTConfiguration> fstConfigurationThreadLocal = new ThreadLocal();

    static {
        fstConfiguration = FSTConfiguration.createDefaultConfiguration();
    }


    /**
     * 序列化
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> byte[] serializer(T t) {
        return Optional.ofNullable(fstConfigurationThreadLocal.get()).orElseGet(() ->
        {
            fstConfigurationThreadLocal.set(fstConfiguration);
            return fstConfigurationThreadLocal.get();
        }).asByteArray(t);
    }

    /**
     * 反序列化
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> T unserializer(byte[] data) {
        return (T) Optional.ofNullable(fstConfigurationThreadLocal.get()).orElseGet(() ->
        {
            fstConfigurationThreadLocal.set(fstConfiguration);
            return fstConfigurationThreadLocal.get();
        })
                .asObject(data);
    }


    /**
     * 基于序列化的对象深度复制
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T copyObject(T t) {
        if (null == t) return null;
        return unserializer(serializer(t));
    }


    /**
     * 对象集合复制
     *
     * @param list
     * @return
     */
    public static <T> List<T> copyObject(List<T> list) {
        if (null == list) return null;
        return unserializer(serializer(list));
    }

    /**
     * 对象数组复制
     *
     * @param arrs
     * @param <T>
     * @return
     */
    public static <T> T[] copyObject(T[] arrs) {
        if (null == arrs) return null;
        return unserializer(serializer(arrs));
    }

    /**
     * 对象set集合复制
     *
     * @param set
     * @param <T>
     * @return
     */
    public static <T> Set<T> copyObject(Set<T> set) {
        if (null == set) return null;
        return unserializer(serializer(set));
    }
}