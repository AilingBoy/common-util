package com.cn.stardust.tool.aviator;

import com.googlecode.aviator.AviatorEvaluator;

public class GoogleAviator {

    /**
     * java 表达式执行
     * @param args
     */
    public static void main(String[] args) {
        Long result = (Long) AviatorEvaluator.execute("1+2+3");
        System.out.println(result);
        Boolean bResult = (Boolean)AviatorEvaluator.execute("1==2");
        System.out.println(bResult);
    }
}