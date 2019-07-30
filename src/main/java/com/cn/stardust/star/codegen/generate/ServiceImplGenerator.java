package com.cn.stardust.star.codegen.generate;

import com.cn.stardust.star.codegen.CamelCaseConvert;

import java.io.File;

/**
 * https://github.com/oraclexing
 * <p>
 *  ServiceImpl 层的生成器
 *
 * @author stardust
 * @version 1.0.0
 */
final public class ServiceImplGenerator extends AbstractGenerator {

    static ServiceImplGenerator serviceImplGenerator = new ServiceImplGenerator();

    private ServiceImplGenerator() {}

    @Override
    public String getImportInfo() {
        return "import org.springframework.beans.factory.annotation.Autowired;" + Character.LINE_FEED +
               "import org.springframework.stereotype.Service;" + Character.LINE_FEED +
               "import com.cn.hz.info.manager.model." + classMetaData.getClassName() +";"+ Character.LINE_FEED +
               "import java.util.List;"+Character.getLineFeed(2);
    }

    @Override
    public String getClassInfo() {
        return  "@Service"+Character.LINE_FEED +
                "public class "+ classMetaData.getClassName() +"ServiceImpl implements "+classMetaData.getClassName()+"Service"
                + Character.OPEN_BRACE
                + Character.getLineFeed(2);
    }

    @Override
    public String getFieldsInfo() {
        return Character.getSpace(4) + "@Autowired" + Character.LINE_FEED
                + Character.getSpace(4) + "private "+ classMetaData.getClassName() + "Mapper "
                + CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName()) + "Mapper " + Character.SEMICOLON
                + Character.getLineFeed(2) ;
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
        return Character.getSpace(4)+"@Override"+Character.LINE_FEED
                + Character.getSpace(4)+"public String add(" + classMetaData.getClassName() + Character.SPACE
                + CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName()) + Character.COLSE_PAREN
                + "throws Exception " + Character.OPEN_BRACE + Character.LINE_FEED
                + Character.getSpace(8) + CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName())
                + "Mapper.insert" + Character.OPEN_PAREN + CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName())
                + Character.COLSE_PAREN + Character.SEMICOLON + Character.LINE_FEED
                + Character.getSpace(8) + "return "
                + CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName())+".getId()" + Character.SEMICOLON + Character.LINE_FEED
                + Character.getSpace(4) + Character.CLOSE_BRACE;
    }

    private String getRemoveMethod(){
        return Character.getSpace(4) + "@Override"+Character.LINE_FEED
                + Character.getSpace(4) + "public Boolean remove(String id)throws Exception " + Character.OPEN_BRACE + Character.LINE_FEED
                + Character.getSpace(8) +CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName())
                + "Mapper.delete(id)"
                + Character.SEMICOLON + Character.LINE_FEED
                + Character.getSpace(8) + "return Boolean.TRUE" + Character.SEMICOLON + Character.LINE_FEED
                + Character.getSpace(4) + Character.CLOSE_BRACE;
    }

    private String getModifyMethod(){
        return Character.getSpace(4) + "@Override"+Character.LINE_FEED
                + Character.getSpace(4) + "public Boolean modify" + Character.OPEN_PAREN + classMetaData.getClassName() + Character.SPACE
                + CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName()) + Character.COLSE_PAREN
                + "throws Exception " + Character.OPEN_BRACE + Character.LINE_FEED
                + Character.getSpace(8) +CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName())
                + "Mapper.update" + Character.OPEN_PAREN + CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName())
                + Character.COLSE_PAREN + Character.SEMICOLON + Character.LINE_FEED
                + Character.getSpace(8) + "return Boolean.TRUE" + Character.SEMICOLON + Character.LINE_FEED
                + Character.getSpace(4) + Character.CLOSE_BRACE;
    }

    private String getSearchMethod(){
        return Character.getSpace(4) + "@Override"+Character.LINE_FEED
                + Character.getSpace(4) + "public List"+Character.OPEN_ANGULAR_BRACKETS
                + classMetaData.getClassName() + Character.CLOSE_ANGULAR_BRACKETS + Character.SPACE
                + "search"+Character.OPEN_PAREN + classMetaData.getClassName() + Character.SPACE
                + CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName()) + Character.COLSE_PAREN
                + "throws Exception " + Character.OPEN_BRACE + Character.LINE_FEED
                + Character.getSpace(8) + "return" + Character.SPACE + CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName())
                + "Mapper.select"+Character.OPEN_PAREN
                + CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName()) + Character.COLSE_PAREN
                + Character.SEMICOLON + Character.LINE_FEED
                + Character.getSpace(4) + Character.CLOSE_BRACE;
    }

    private String getSelectByIdMethod(){
        return Character.getSpace(4) + "@Override"+Character.LINE_FEED
                + Character.getSpace(4) + "public" + Character.SPACE + classMetaData.getClassName()+Character.SPACE
                +"get(String id)throws Exception" + Character.SPACE + Character.OPEN_BRACE + Character.LINE_FEED
                + Character.getSpace(8) + "return" + Character.SPACE
                + CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName())
                + "Mapper.selectById(id)"
                + Character.SEMICOLON + Character.LINE_FEED
                + Character.getSpace(4) + Character.CLOSE_BRACE;
    }

    @Override
    public String getFileName() {
        return outputPath + File.separator + classMetaData.getClassName()+"ServiceImpl.java";
    }
}
