package com.cn.stardust.tool.model;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 * 2021/4/30 9:47
 */
@Data
public class User extends People{

    private String id;

    private Integer archive;

    private Date createAt;

    private Date updateAt;

    private String name;

    private String hobby;

    private Integer age;

}
