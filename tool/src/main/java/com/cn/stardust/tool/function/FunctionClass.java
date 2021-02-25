package com.cn.stardust.tool.function;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 函数式编程
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 * 2021/2/24 17:36
 */
public class FunctionClass {

    public static void main(String[] args) throws Exception{
        FunctionClass functionClass = new FunctionClass();
        List<Observer> observers = new ArrayList<>();
        observers.add(functionClass.getLengthObserver());
        observers.add(functionClass.getPointIndexObserver());
        functionClass.checkObserver(observers,"htloos.ada");
    }

    public Observer getLengthObserver(){
        return this::countLength;
    }

    public Observer getPointIndexObserver(){
        return this::countPointIndex;
    }

    private int countLength(String content){
        if(StringUtils.isEmpty(content)){
            return 0;
        }
        return content.length();
    }

    private int countPointIndex(String content){
        if(StringUtils.isEmpty(content)){
            return 0;
        }
        return content.lastIndexOf('.');
    }

    public void checkObserver(List<Observer> observers,String content){
        for(Observer observer : observers){
            System.out.println(observer.getClass().getName()+"---"+observer.init(content));
        }
    }
}