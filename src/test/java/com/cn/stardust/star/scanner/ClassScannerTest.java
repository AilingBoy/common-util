package com.cn.stardust.star.scanner;

import com.alibaba.fastjson.JSON;
import com.cn.stardust.star.Common;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 * 按照给定class 进行包扫描的单元测试
 *
 *
 * @author stardust
 * @version 1.0.0
 */
public class ClassScannerTest {

    @Test
    public void scannerTest()throws Exception{
        Package pack = ClassScanner.scan(Common.class);
        System.out.println(JSON.toJSONString(pack));
        List list = Lists.newArrayList();
        ClassScanner.getAllClass(list,pack);
        System.out.println("finished!");
    }

}
