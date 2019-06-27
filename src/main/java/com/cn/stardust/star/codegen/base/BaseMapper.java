package com.cn.stardust.star.codegen.base;


import java.util.List;

/**
 * https://github.com/oraclexing
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 */
public interface BaseMapper<T> {

    /**
     * 增加
     * @param t
     */
    void insert(T t);

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