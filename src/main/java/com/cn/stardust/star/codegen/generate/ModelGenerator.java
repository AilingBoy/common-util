package com.cn.stardust.star.codegen.generate;

import com.beust.jcommander.internal.Lists;
import com.cn.stardust.star.codegen.CamelCaseConvert;
import com.cn.stardust.star.codegen.metadata.FieldMetaData;

import java.io.File;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 * model 层的生成器
 * 使用了 lombok
 *
 * @author stardust
 * @version 1.0.0
 */
final public class ModelGenerator extends AbstractGenerator {

    static ModelGenerator modelGenerator = new ModelGenerator();

    private List<String> fieldList = Lists.newArrayList("id","createAt","updateAt","archive");

    private ModelGenerator() {}

    @Override
    public String getImportInfo() {
        return "import lombok.Data" + Character.SEMICOLON + Character.LINE_FEED +
               "import com.cn.stardust.star.codegen.base.BaseModel" + Character.SEMICOLON +
                Character.getLineFeed(2);
    }

    @Override
    public String getClassInfo() {
        return  "@Data"+Character.LINE_FEED +
                "public class "+ classMetaData.getClassName() +" extends BaseModel "
                + Character.OPEN_BRACE
                + Character.getLineFeed(2);
    }

    @Override
    public String getFieldsInfo() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(Character.getSpace(4)+"private static final long serialVersionUID = "+ ThreadLocalRandom.current().nextLong()+"L"+Character.SEMICOLON);
        buffer.append(Character.getLineFeed(2));
        /**
         *
         * 过滤掉父类中已经有的属性
         */
        classMetaData.getFieldMetaDatas().stream().filter(e -> !fieldList.contains(e.getFieldName()))
                .forEach(e ->
            buffer.append(getFieldInfo(e))
        );
        return buffer.toString() ;
    }


    public String getFieldInfo(FieldMetaData fieldMetaData){
        StringBuffer buffer = new StringBuffer();
        // 添加字段注释
        String desc = fieldMetaData.getDesc();
        if(null == desc || "".equals(desc)){
            desc = CamelCaseConvert.toLowerCamelCase(fieldMetaData.getFieldName());
        }
        buffer.append(Character.getSpace(4) + "/** "+ desc +" */");
        buffer.append(Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "private " + fieldMetaData.getClazz().getName()
                + Character.SPACE + fieldMetaData.getFieldName() + Character.SEMICOLON);
        buffer.append(Character.getLineFeed(2));
        return buffer.toString();
    }

    @Override
    public String getMethodsInfo() {
        return "";
    }

    @Override
    public String getFileName() {
        return outputPath + File.separator + classMetaData.getClassName()+".java";
    }
}
