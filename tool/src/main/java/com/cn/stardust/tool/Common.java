package com.cn.stardust.tool;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 *
 */
public class Common {

    public static void main(String[] args) {
        Common common = new Common();
        String result = common.calculate(20.1,32.6,(a,b)->(a.doubleValue()*b.doubleValue()),a->a.toString());
        System.out.println(result);
    }

    public String calculate(Number a, Number b, BiFunction<Number,Number,Number> biFunction, Function<Number,String> function){
        return biFunction.andThen(function).apply(a,b);
    }
}
