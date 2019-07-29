package com.cn.stardust.star.codegen;

import com.cn.stardust.star.Common;
import com.cn.stardust.star.codegen.typeconvert.DataTypeConvert;
import com.google.common.collect.Lists;
import org.junit.Test;

/**
 * https://github.com/oraclexing
 * <p>
 * spring 框架代码生成器
 *
 * @author stardust
 * @version 1.0.0
 */
public class CodeGenerateTest {

    @Test
    public void generateTest()throws Exception{
            CodeGenerate codeGenerate = CodeGenerate.getMysqlInstance("127.0.0.1",
                    "common_data","common","common",new DataTypeConvert());
        codeGenerate.generate(Lists.newArrayList("user1","websites1"),"common_utils\\src\\main\\java\\com\\cn\\stardust\\star", Common.class);
    }
}