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
    protected List<Double> datas;

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

    public strictfp Double getData(List<Number> numbers,int index){
        Double result = 0.0;
        for(int i = 0 ; i < numbers.size() ; i++){
            result += numbers.get(i).doubleValue() * datas.get((i*index)%datas.size());
        }
        return result;
    }

    public strictfp Double getData(Integer index){
        return datas.get(index);
    }
}
