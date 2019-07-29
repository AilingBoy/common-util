package com.cn.stardust.star.codegen.generate;

import com.cn.stardust.star.codegen.CamelCaseConvert;

import java.io.File;

/**
 * https://github.com/oraclexing
 * <p>
 * mapper 层生成器
 *
 * @author stardust
 * @version 1.0.0
 */
final class MapperGenerator extends AbstractGenerator {

    static MapperGenerator  mapperGenerator = new MapperGenerator();

    private MapperGenerator() {}

    @Override
    public String getImportInfo() {
        return "import org.apache.ibatis.annotations.*;"+ Character.LINE_FEED +
               "import org.apache.ibatis.mapping.StatementType;" + Character.LINE_FEED +
               "import com.cn.stardust.star.codegen.base.BaseMapper;" + Character.LINE_FEED +
               "import java.util.List;"+Character.getLineFeed(2);
    }

    @Override
    public String getClassInfo() {
        return "@Mapper"+Character.LINE_FEED+
                "public interface "+ classMetaData.getClassName() +"Mapper extends BaseMapper"
                + Character.OPEN_ANGULAR_BRACKETS + classMetaData.getClassName()
                + Character.CLOSE_ANGULAR_BRACKETS + Character.OPEN_BRACE
                + Character.getLineFeed(2);
    }

    @Override
    public String getFieldsInfo() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(Character.getSpace(4)+"String baseColum = \"");
        classMetaData.getFieldMetaDatas().forEach(e -> buffer.append(e.getName()+','));
        buffer.append("\"");
        buffer.setCharAt(buffer.length()-1,';');
        buffer.append(Character.getLineFeed(2));
        return buffer.toString();
    }

    @Override
    public String getMethodsInfo() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getAddMethod() + Character.getLineFeed(2));
        buffer.append(getRemoveMethod() + Character.getLineFeed(2));
        buffer.append(getModifyMethod() + Character.getLineFeed(2));
        buffer.append(getSearchMethod() + Character.getLineFeed(2));
        buffer.append(getSelectByIdMethod() + Character.getLineFeed(2));
        return buffer.toString();
    }


    private String getAddMethod(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(Character.getSpace(4) + "@Override"+Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "@Insert(\"insert into ");
        buffer.append(classMetaData.getTableName()+"(");
        buffer.append(classMetaData.getFieldMetaDatas().stream().map(e ->"`"+e.getName()+"`").reduce((a,b)->a+","+b).get());
        buffer.append(") ");
        buffer.append("value(");
        buffer.append(classMetaData.getFieldMetaDatas().stream().map(e ->"#{"+e.getFieldName()+"}").reduce((a,b)->a+","+b).get());
        buffer.append(")");
        buffer.append("\")");
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "@SelectKey(before=false,keyProperty=\"id\",resultType=Long.class,statementType= StatementType.STATEMENT,statement=\"SELECT LAST_INSERT_ID() AS id\")");
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "Long insert(");
        buffer.append(classMetaData.getClassName());
        buffer.append(Character.SPACE + CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName()));
        buffer.append(");");
        return buffer.toString();
    }


    private String getRemoveMethod(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(Character.getSpace(4) + "@Override"+Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "@Update(\"update ");
        buffer.append(classMetaData.getTableName()+",");
        buffer.append(" set update_at = now(),archive = 1 where id = #{id} and archive = 0 \")");
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "Boolean delete(Long id);");
        return buffer.toString();
    }


    public String getModifyMethod(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(Character.getSpace(4) + "@Override"+Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "@Update({\"<script> update ");
        buffer.append(classMetaData.getTableName()+" set \" +");
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(12)+"\"<if test = 'archive != null'> archive = #{archive},</if>\"+");
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(12) + "// TODO ADD MORE CONDITIONS! ");
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(12) + "\"update_at = now() where archive = 0 and id = #{id} </script>\"})");
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "Boolean update" + Character.OPEN_PAREN + classMetaData.getClassName() + Character.SPACE
                + CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName())
                + Character.COLSE_PAREN + Character.SEMICOLON);
        return buffer.toString();
    }

    public String getSearchMethod(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(Character.getSpace(4) + "@Override"+Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "@Select({\"<script> select * from ");
        buffer.append(classMetaData.getTableName()+" where \" +");
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(12) + "\"<if test = 'archive != null'> archive = #{archive} and </if>\"+");
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(12) + "// TODO ADD MORE CONDITIONS! ");
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(12) + "\"1 = 1 order by id desc </script>\"})");
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "List"+ Character.OPEN_ANGULAR_BRACKETS + classMetaData.getClassName()+ Character.CLOSE_ANGULAR_BRACKETS);
        buffer.append(Character.SPACE);
        buffer.append("select"+ Character.OPEN_PAREN + classMetaData.getClassName() + Character.SPACE
                + CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName())
                + Character.COLSE_PAREN + Character.SEMICOLON);
        return buffer.toString();
    }


    public String getSelectByIdMethod(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(Character.getSpace(4) + "@Override"+Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "@Select(\"select * from ");
        buffer.append(classMetaData.getTableName()+" where archive = 0 and id = #{id}\")");
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + classMetaData.getClassName() + Character.SPACE +"selectById(Long id);");
        return buffer.toString();
    }

    @Override
    public String getFileName() {
        return outputPath + File.separator + classMetaData.getClassName()+"Mapper.java";
    }
}