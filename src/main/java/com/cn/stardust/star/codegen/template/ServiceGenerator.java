package com.cn.stardust.star.codegen.template;

import com.cn.stardust.star.codegen.CamelCaseConvert;

import java.io.File;

/**
 * https://github.com/oraclexing
 * <p>
 *
 * @author stardust
 * @version 1.0.0
 */
final public class ServiceGenerator extends AbstractGenerator{

    static ServiceGenerator serviceGenerator = new ServiceGenerator();

    private ServiceGenerator() {}

    @Override
    public String getImportInfo() {
        return "import com.cn.stardust.star.codegen.base.BaseService;" + Character.LINE_FEED+
               "import java.util.List;"+Character.getLineFeed(2);
    }

    @Override
    public String getClassInfo() {
        return "public interface "+ classMetaData.getClassName() +"Service extends BaseService"
                + Character.OPEN_ANGULAR_BRACKETS + classMetaData.getClassName()
                + Character.CLOSE_ANGULAR_BRACKETS + Character.OPEN_BRACE
                + Character.getLineFeed(2);
    }

    @Override
    public String getFieldsInfo() {
        return new String();
    }

    @Override
    public String getMethodsInfo() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(Character.getSpace(4) + "@Override"+Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "Long add("+classMetaData.getClassName()+Character.SPACE
                + CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName()) + ") throws Exception;");
        buffer.append(Character.getLineFeed(2));

        buffer.append(Character.getSpace(4) + "@Override"+Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "Boolean modify("+classMetaData.getClassName()+Character.SPACE
                + CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName()) + ") throws Exception;");
        buffer.append(Character.getLineFeed(2));

        buffer.append(Character.getSpace(4) + "@Override"+Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "Boolean remove(Long id) throws Exception;");
        buffer.append(Character.getLineFeed(2));


        buffer.append(Character.getSpace(4) + "@Override"+Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "List"+Character.OPEN_ANGULAR_BRACKETS
                + classMetaData.getClassName() + Character.CLOSE_ANGULAR_BRACKETS
                + " search("+classMetaData.getClassName()+Character.SPACE
                + CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName()) + ") throws Exception;");
        buffer.append(Character.getLineFeed(2));


        buffer.append(Character.getSpace(4) + "@Override"+Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + classMetaData.getClassName()+Character.SPACE
                + "get(Long id) throws Exception;");
        buffer.append(Character.getLineFeed(2));
        return buffer.toString();
    }

    @Override
    public String getFileName() {
        return outputPath + File.separator + classMetaData.getClassName()+"Service.java";
    }
}