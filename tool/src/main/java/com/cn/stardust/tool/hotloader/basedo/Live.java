package com.cn.stardust.tool.hotloader.basedo;

import com.cn.stardust.tool.hotloader.listen.FileListener;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 *  所有需要动态加载的都必须实现该接口，待编译加载成功后会立刻执行run方法
 *  请参考代码 {@link FileListener#run(String)}
 *
 * @author stardust
 * @version 1.0.0
 *
 */
public interface Live {

    /**
     * 用于启动执行
     */
    void run();

    /**
     * 返回具体实现类的名称，方便自己内存中管理维护
     * @return
     */
    String getName();

}
