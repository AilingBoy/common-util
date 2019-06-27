package com.cn.stardust.star.codegen.sql;

import com.cn.stardust.star.codegen.Metadata;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

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
    public Map<String, List<Metadata>> query() {
        return null;
    }

    @Override
    public void close(Connection con) {

    }
}
