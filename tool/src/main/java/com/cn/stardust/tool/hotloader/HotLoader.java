package com.cn.stardust.tool.hotloader;

import com.cn.stardust.tool.hotloader.listen.FileListener;

/**
 * https://github.com/KnowNoUnknown
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
     * @param interval 监听周期
     * @return 默认启动成功，返回true,若失败，直接抛出异常
     * @throws Exception
     */
    public static Boolean start(String dir,Long interval) throws Exception {
        FileListener fileListener = new FileListener();
        fileListener.listen(dir, interval);
        return Boolean.TRUE;
    }
}