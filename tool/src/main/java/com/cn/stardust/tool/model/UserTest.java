package com.cn.stardust.tool.model;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

/**
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 * 2021/4/30 9:49
 * 序列化测试
 * FastJson 序列化会导致原class的字段顺序改变
 * ObjectMapper 序列化结果则完整保留原class 的字段顺序,包含继承关系
 *
 */
public class UserTest {

    public static void main(String[] args)throws Exception {
        User user = new User();
        user.setGender("男");
        user.setFamily("星际");
        user.setBorn(new Date());
        user.setId("jgrgudfhi2hiuh323f");
        user.setName("测试项目");
        user.setAge(14);
        user.setHobby("兴趣爱好");
        user.setCreateAt(new Date());
        user.setUpdateAt(new Date());
        user.setArchive(0);
        System.out.println(JSON.toJSONString(user));
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(user));
    }

}
