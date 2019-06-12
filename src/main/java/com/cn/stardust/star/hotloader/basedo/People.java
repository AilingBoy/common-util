package com.cn.stardust.star.hotloader.basedo;

/**
 * Description: com.cn.stardust.star
 * Created by Oracle on 2019/6/12 19:16
 */
public class People implements Live {

    @Override
    public void run() {
        System.out.println("People run ......");
        System.out.println(getName());
        new Son("STARK", 56).say();
    }

    @Override
    public String getName() {
        return "People saying something true !";
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
