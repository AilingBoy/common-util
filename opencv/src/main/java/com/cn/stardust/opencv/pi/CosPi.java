package com.cn.stardust.opencv.pi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stardust
 * @date 2020/9/29 14:36
 */
public class CosPi extends AbstractPi {

    public CosPi(Integer num) {
        super(num);
    }

    @Override
    strictfp List<Double> initData() {
        List<Double> doubles = new ArrayList<>();
        for(int i = 0 ; i < num ; i ++){
            doubles.add(Math.cos(2*i*Math.PI/num));
        }
        return doubles;
    }
}
