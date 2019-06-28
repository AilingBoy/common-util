package com.cn.stardust.star.codegen.sql;

import com.cn.stardust.star.codegen.ClassMetaData;

import java.util.List;

/**
 * https://github.com/oraclexing
 * <p>
 * FIXME 暂时不支持oracle数据库代码生成
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
