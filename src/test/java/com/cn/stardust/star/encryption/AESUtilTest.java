package com.cn.stardust.star.encryption;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 */
public class AESUtilTest {

    /**
     * 多线程并发测试AES加解密
     * @throws Exception
     */
    @Test
    public void ThreadPoolAESTest()throws Exception{
        Long time = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(5);
        Runnable runnable = ()->{
            try {
                for (int i = 0 ; i < 100 ; i++){
                    AESTest(i);
                }
                countDownLatch.countDown();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName()+"报错...............");
                countDownLatch.countDown();
            }};
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
        countDownLatch.await();
        System.out.println("消耗时间：——————"+(System.currentTimeMillis()-time));
    }

    public void AESTest(int i){
        String content = Thread.currentThread().getName()+"——————>"+i;
        System.out.println("加密前：" + content);
        String KEY = "000000——————"+i;
        System.out.println(content +" 加密密钥和解密密钥：" + KEY);
        String encrypt = AESUtil.encrypt(content, KEY);
        System.out.println(Thread.currentThread().getName()+"——————>("+i+")加密后：" + encrypt);
        String decrypt = AESUtil.decrypt(encrypt, KEY);
        System.out.println("解密后：" + decrypt);

    }
}
