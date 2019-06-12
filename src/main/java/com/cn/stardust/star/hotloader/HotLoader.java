package com.cn.stardust.star.hotloader;

import com.cn.stardust.star.hotloader.listen.FileListener;

/**
 * Description: com.cn.stardust.star
 * Created by Oracle on 2019/6/12 19:16
 */
public class HotLoader {

    /**
     * 启动
     * @param dir 动态加载的文件夹
     * @throws Exception
     */
    public static Boolean start(String dir) throws Exception {
        FileListener fileListener = new FileListener();
        fileListener.listen(dir);
        return Boolean.TRUE;
    }
}