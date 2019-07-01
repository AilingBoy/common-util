package com.cn.stardust.star.codegen.sql;

import com.cn.stardust.star.codegen.metadata.ClassMetaData;

import java.util.List;

/**
 * https://github.com/oraclexing
 * <p>
 * Oracle sql查询器
 * FIXME 暂时不支持oracle数据库代码生成 ,后面也不想支持了，不想写了，主要是怕麻烦,   ;(
 *
 * @author stardust
 * @version 1.0.0
 */
public class OracleQuery extends Query {

    @Override
    public List<ClassMetaData> query() {
        return null;
    }

}
