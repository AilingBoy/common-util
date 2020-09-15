package com.cn.stardust.star.codegen;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 *  驼峰式字符串转换
 *
 * @author stardust
 * @version 1.0.0
 */
final public class CamelCaseConvert {

    /**
     * 字符串转换小写驼峰
     * @param content
     * @return
     */
    public static String toLowerCamelCase(String content){
        if(null == content){
            return null;
        }
        content = content.toLowerCase();
        String[] array = content.split("_");
        StringBuffer buffer = new StringBuffer();
        buffer.append(array[0]);
        if(array.length>1){
            for( int i = 1 ; i < array.length ; i++ ) {
                buffer.append((array[i].charAt(0)+"").toUpperCase())
                        .append(array[i].substring(1));
            }
        }
        return buffer.toString();
    }

    /**
     * 字符串转大写驼峰
     * @param content
     * @return
     */
    public static String toUpperCamelCase(String content){
        if(null == content){
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append((content.charAt(0)+"").toUpperCase());
        buffer.append(toLowerCamelCase(content.substring(1)));
        return buffer.toString();
    }
}