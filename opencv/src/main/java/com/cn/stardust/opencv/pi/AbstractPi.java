package com.cn.stardust.opencv.pi;

import java.util.List;

/**
 * @author stardust
 * @date 2020/9/29 14:39
 */
public abstract class AbstractPi {

    /**
     * 细分弧度之后的正切/余切值
     */
    private List<Double> datas;

    /**
     * 圆周等分的份数
     */
    protected Integer num;

    public AbstractPi(Integer num) {
        this.num = num;
        this.datas = initData();
    }

    /**
     * 初始化正切/余切值
     * @return
     */
    abstract List<Double> initData();
}
