package com.cn.stardust.tool.codegen.generate;

import com.cn.stardust.tool.codegen.CamelCaseConvert;
import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 * mapper 层生成器
 *
 * @author stardust
 * @version 1.0.0
 */
final class MapperGenerator extends AbstractGenerator {

    static MapperGenerator  mapperGenerator = new MapperGenerator();

    static List<String> IGNORE_FIELDS = Lists.newArrayList("id","createAt","updateAt","archive");

    private MapperGenerator() {}

    @Override
    public String getImportInfo() {
        return "import org.apache.ibatis.annotations.*;"+ Character.LINE_FEED +
               "import org.apache.ibatis.mapping.StatementType;" + Character.LINE_FEED +
//               "import com.cn.hz.info.manager.model." + classMetaData.getClassName() +";"+ Character.LINE_FEED +
               "import java.util.List;"+ Character.getLineFeed(2);
    }

    @Override
    public String getClassInfo() {
        return "@Mapper"+ Character.LINE_FEED+
                "public interface "+ classMetaData.getClassName() +"Mapper extends BaseMapper"
                + Character.OPEN_ANGULAR_BRACKETS + classMetaData.getClassName()
                + Character.CLOSE_ANGULAR_BRACKETS + Character.OPEN_BRACE
                + Character.getLineFeed(2);
    }

    @Override
    public String getFieldsInfo() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(Character.getSpace(4)+"String baseColumn = \"");
        classMetaData.getFieldMetaDatas().forEach(e -> buffer.append(e.getName()+','));
        buffer.setCharAt(buffer.length()-1,'\"');
        buffer.append(";");
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
        buffer.append(getBatchAddMethod() + Character.getLineFeed(2));
        return buffer.toString();
    }


    private String getAddMethod(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(Character.getSpace(4) + "@Override"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "@Insert(\"insert into ");
        buffer.append(classMetaData.getTableName()+"(");
        buffer.append(classMetaData.getFieldMetaDatas().stream().map(e -> e.getName()).reduce((a,b)->a+","+b).get());
        buffer.append(") ");
        buffer.append("values(");
        buffer.append(classMetaData.getFieldMetaDatas().stream().map(e ->"#{"+e.getFieldName()+"}").reduce((a,b)->a+","+b).get());
        buffer.append(")");
        buffer.append("\")");
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "void insert(");
        buffer.append(classMetaData.getClassName());
        buffer.append(Character.SPACE + CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName()));
        buffer.append(");");
        return buffer.toString().replace("#{createAt}","sysdate").
                replace("#{updateAt}","sysdate").replace("#{archive}","0");
    }


    private String getRemoveMethod(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(Character.getSpace(4) + "@Override"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "@Update(\"update ");
        buffer.append(classMetaData.getTableName());
        buffer.append(" set update_at = sysdate,archive = 1 where id = #{id} and archive = 0 \")");
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "void delete(String id);");
        return buffer.toString();
    }


    public String getModifyMethod(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(Character.getSpace(4) + "@Override"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "@Update({\"<script> update ");
        buffer.append(classMetaData.getTableName()+" set \" +");
        buffer.append(Character.LINE_FEED);

        for(int i = 0 ; i < classMetaData.getFieldMetaDatas().size();i++){
            if(IGNORE_FIELDS.contains(classMetaData.getFieldMetaDatas().get(i).getFieldName()))continue;
            buffer.append(Character.getSpace(12)).append("\"<if test = '")
                    .append(classMetaData.getFieldMetaDatas().get(i).getFieldName())
                    .append(" != null'> ")
                    .append(classMetaData.getFieldMetaDatas().get(i).getName())
                    .append(" =#{").append(classMetaData.getFieldMetaDatas().get(i).getFieldName())
                    .append("},</if>\" +")
                    .append(Character.LINE_FEED);
        }
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(12) + "\"update_at = sysdate where archive = 0 and id = #{id} </script>\"})");
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "void update" + Character.OPEN_PAREN + classMetaData.getClassName() + Character.SPACE
                + CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName())
                + Character.COLSE_PAREN + Character.SEMICOLON);
        return buffer.toString();
    }

    public String getSearchMethod(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(Character.getSpace(4) + "@Override"+ Character.LINE_FEED);
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
                + CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName())
                + Character.COLSE_PAREN + Character.SEMICOLON);
        return buffer.toString();
    }


    public String getSelectByIdMethod(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(Character.getSpace(4) + "@Override"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "@Select(\"select * from ");
        buffer.append(classMetaData.getTableName()+" where archive = 0 and id = #{id}\")");
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + classMetaData.getClassName() + Character.SPACE +"selectById(String id);");
        return buffer.toString();
    }

    @Override
    public String getFileName() {
        return outputPath + File.separator + classMetaData.getClassName()+"Mapper.java";
    }

    /**
     * 批量添加接口
     * @return
     */
    private String getBatchAddMethod(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(Character.getSpace(4) + "@Insert({\"<script> insert into ");
        buffer.append(classMetaData.getTableName()+"(");
        buffer.append(classMetaData.getFieldMetaDatas().stream().map(e -> e.getName()).reduce((a,b)->a+","+b).get());
        buffer.append(") ");
        buffer.append("values\" +");
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(12));
        buffer.append("\"<foreach collection='")
                .append(CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName()))
                .append("s' index='index' item='")
                .append(CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName()))
                .append("' separator=',' >\" +")
                .append(Character.LINE_FEED)
                .append(Character.getSpace(14)).append("\"(");
        buffer.append("#{").append(CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName()))
                .append(".").append("id},");
        buffer.append("sysdate,sysdate,0,");
        for(int i = 0 ; i < classMetaData.getFieldMetaDatas().size();i++){
            if(IGNORE_FIELDS.contains(classMetaData.getFieldMetaDatas().get(i).getFieldName()))continue;
            buffer.append("#{").append(CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName()))
                    .append(".").append(classMetaData.getFieldMetaDatas().get(i).getFieldName())
                    .append("},");
        }
        buffer.setCharAt(buffer.length()-1,')');
        buffer.append("\"").append("+");
        buffer.append(Character.LINE_FEED).append(Character.getSpace(12))
                .append("\"</foreach>\"+").append(Character.LINE_FEED)
                .append(Character.getSpace(8))
                .append("\"</script>\"})").append(Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "void insert(")
                .append("@Param(\"").append(CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName())).append("s")
                .append("\")List<");
        buffer.append(classMetaData.getClassName()).append("> ");
        buffer.append(Character.SPACE + CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName())).append("s");
        buffer.append(");");
        return buffer.toString().replace("#{createAt}","sysdate").
                replace("#{updateAt}","sysdate").replace("#{archive}","0");
    }
}