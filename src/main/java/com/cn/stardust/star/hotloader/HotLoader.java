package com.cn.stardust.star.hotloader;

import com.cn.stardust.star.hotloader.listen.FileListener;

/**
 * https://github.com/oraclexing
 * <p>
 * 动态加载启动入口
 *
 * @author stardust
 * @version 1.0.0
 *
 */
public class HotLoader {

    /**
     *
     * @param dir 待监听的文件变化目录
     * @return 默认启动成功，返回true,若失败，直接抛出异常
     * @throws Exception
     */
    public static Boolean start(String dir) throws Exception {
        FileListener fileListener = new FileListener();
        fileListener.listen(dir, null);
        return Boolean.TRUE;
    }
}