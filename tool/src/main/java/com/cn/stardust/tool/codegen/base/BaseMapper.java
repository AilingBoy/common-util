package com.cn.stardust.tool.codegen.base;


import java.util.List;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 * Mapper 的基类，被其他mapper 实现
 *
 * @author stardust
 * @version 1.0.0
 */
public interface BaseMapper<T> {

    /**
     * 增加
     * @param t
     */
    Long insert(T t);

    /**
     * 删除
     * @param id
     * @return
     */
    Boolean delete(Long id);

    /**
     * 更新
     * @param t
     * @return
     */
    Boolean update(T t);

    /**
     * 查询
     * @param t
     * @return
     */
    List<T> select(T t);

    /**
     * 通过id 查询
     * @param id
     * @return
     */
    T selectById(Long id);

}