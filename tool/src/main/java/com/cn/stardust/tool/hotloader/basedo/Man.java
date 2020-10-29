package com.cn.stardust.tool.hotloader.basedo;

import org.springframework.util.StopWatch;

/**
 * @author stardust
 * @date 2020/10/23 16:57
 */
public class Man extends People{

    @Override
    public void run() {
        System.out.println(this.getClass().getName());
        try {
            System.out.println("打印日志!");
            check();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public void check()throws Exception{
        StopWatch stopWatch = new StopWatch("时间");
        stopWatch.start("1");
        Thread.sleep(100L);
        stopWatch.stop();
        stopWatch.start("2");
        Thread.sleep(500L);
        stopWatch.stop();
        stopWatch.start("3");
        Thread.sleep(1000L);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
