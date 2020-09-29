package com.cn.stardust.tool.hotloader.basedo;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 *  具体实现Live接口类
 *  同时使用内部类Son测试加载情况
 *
 * @author stardust
 * @version 1.0.0
 *
 */
public class People implements Live {

    @Override
    public void run() {
        System.out.println(getName() + "run ......");
        new Son("STARK", 56).say();
    }

    @Override
    public String getName() {
        return "People ";
    }

    class Son {

        private String name;

        private Integer age;

        private Son(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public void say() {
            System.out.println(toString());
        }

        @Override
        public String toString() {
            return "Son{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
