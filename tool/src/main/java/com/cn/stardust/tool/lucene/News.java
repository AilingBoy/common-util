package com.cn.stardust.tool.lucene;

import lombok.Data;

/**
 * 新闻类
 * @author stardust
 * @date 2020/10/13 9:38
 */
@Data
public class News {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 来源
     */
    private String source;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签
     */
    private String tag;

    /**
     * 回复数
     */
    private Integer reply;

}