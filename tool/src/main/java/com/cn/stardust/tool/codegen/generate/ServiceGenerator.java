package com.cn.stardust.tool.codegen.generate;

import com.cn.stardust.tool.codegen.CamelCaseConvert;

import javax.print.DocFlavor;
import java.io.File;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 * Service 层的生成器
 *
 * @author stardust
 * @version 1.0.0
 */
final public class ServiceGenerator extends AbstractGenerator{

    static ServiceGenerator serviceGenerator = new ServiceGenerator();

    private ServiceGenerator() {}

    @Override
    public String getImportInfo() {
//        return "import com.cn.stardust.star.codegen.base.BaseService;" + Character.LINE_FEED+
//        return "import com.cn.hz.info.manager.model." + classMetaData.getClassName() +";"+ Character.LINE_FEED +
//             "import java.util.List;"+ Character.getLineFeed(2);
          return "import java.util.List;"+ Character.getLineFeed(2);
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

        buffer.append(Character.getSpace(4) + "@Override"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "String add("+classMetaData.getClassName()+ Character.SPACE
                + CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName()) + ") throws Exception;");
        buffer.append(Character.getLineFeed(2));

        buffer.append(Character.getSpace(4) + "@Override"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "Boolean modify("+classMetaData.getClassName()+ Character.SPACE
                + CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName()) + ") throws Exception;");
        buffer.append(Character.getLineFeed(2));

        buffer.append(Character.getSpace(4) + "@Override"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "Boolean remove(String id) throws Exception;");
        buffer.append(Character.getLineFeed(2));


        buffer.append(Character.getSpace(4) + "@Override"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "List"+ Character.OPEN_ANGULAR_BRACKETS
                + classMetaData.getClassName() + Character.CLOSE_ANGULAR_BRACKETS
                + " search("+classMetaData.getClassName()+ Character.SPACE
                + CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName()) + ") throws Exception;");
        buffer.append(Character.getLineFeed(2));


        buffer.append(Character.getSpace(4) + "@Override"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + classMetaData.getClassName()+ Character.SPACE
                + "get(String id) throws Exception;");
        buffer.append(Character.getLineFeed(2));
        return buffer.toString();
    }

    @Override
    public String getFileName() {
        return outputPath + File.separator + classMetaData.getClassName()+"Service.java";
    }
}