package com.cn.stardust.star.codegen.generate;

import com.google.common.base.Strings;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 * 生成器中，常见字符集合
 *
 * @author stardust
 * @version 1.0.0
 */
public class Character {

    /**
     * windows 下的换行符
     */
    public static final String LINE_FEED = "\r\n";

    /**
     * 左大括号
     */
    public static final String OPEN_BRACE = "{";

    /**
     * 右大括号
     */
    public static final String CLOSE_BRACE = "}";

    /**
     * 左圆括号
     */
    public static final String OPEN_PAREN = "(";

    /**
     * 右圆括号
     */
    public static final String COLSE_PAREN = ")";

    /**
     * 左尖括号
     */
    public static final String OPEN_ANGULAR_BRACKETS = "<";

    /**
     * 右尖括号
     */
    public static final String CLOSE_ANGULAR_BRACKETS = ">";

    /**
     * 空格符
     */
    public static final String SPACE = " ";

    /**
     * 分号
     */
    public static final String SEMICOLON = ";";

    /**
     * 获取特定长度的空格字符串
     * @param count
     * @return
     */
    public static String getSpace(int count){
        return Strings.repeat(SPACE,count);
    }

    /**
     * 获取特定长度换行字符串
     * @param count
     * @return
     */
    public static String getLineFeed(int count){
        return Strings.repeat(LINE_FEED,count);
    }
}