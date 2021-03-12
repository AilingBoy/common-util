package com.cn.stardust.tool.codegen.generate;

import com.cn.stardust.tool.codegen.CamelCaseConvert;

import java.io.File;
import java.sql.BatchUpdateException;

/**
 * https://github.com/KnowNoUnknown
 * <p>
 * Controller 层的生成器
 *
 * @author stardust
 * @version 1.0.0
 */
final public class ControllerGenerator extends AbstractGenerator{

    static ControllerGenerator controllerGenerator = new ControllerGenerator();

    private ControllerGenerator() {}

    @Override
    public String getImportInfo() {
          return  "import io.swagger.annotations.Api;" + Character.LINE_FEED +
                  "import io.swagger.annotations.ApiImplicitParam;"+ Character.LINE_FEED +
                  "import io.swagger.annotations.ApiImplicitParams;"+ Character.LINE_FEED +
                  "import io.swagger.annotations.ApiOperation;" + Character.LINE_FEED +
                  "import org.springframework.beans.factory.annotation.Autowired;" + Character.LINE_FEED +
                  "import org.springframework.web.bind.annotation.*;" + Character.LINE_FEED +
                  "import lombok.extern.slf4j.Slf4j;"+Character.LINE_FEED +
                  "import java.util.List;"+ Character.getLineFeed(2);
    }

    @Override
    public String getClassInfo() {
        StringBuffer buffer = new StringBuffer(Character.LINE_FEED);
        buffer.append("@RequestMapping(value = \"/")
                .append(CamelCaseConvert.toURLCase(classMetaData.getTableName())).append("\")")
                .append(Character.LINE_FEED);
        buffer.append("@Api(value = \""+classMetaData.getClassName()+"\", tags = {\""+classMetaData.getClassName()+"\"})")
                .append(Character.LINE_FEED);
        buffer.append("@Slf4j").append(Character.LINE_FEED);
        buffer.append("@RestController").append(Character.LINE_FEED);
        buffer.append("public class "+ classMetaData.getClassName() +"Controller extends BaseController<"
                + classMetaData.getClassName()+", " + classMetaData.getClassName()+"Service> {"
                + Character.getLineFeed(2));
        return buffer.toString();
    }

    @Override
    public String getFieldsInfo() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(Character.getSpace(4));
        buffer.append("@Autowired").append(Character.LINE_FEED);
        buffer.append(Character.getSpace(4)).append("private ")
                .append(classMetaData.getClassName()).append("Service").append(Character.SPACE)
                .append(CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName()))
                .append("Service;").append(Character.getLineFeed(2));
        return buffer.toString();
    }

    @Override
    public String getMethodsInfo() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(Character.getSpace(4) + "@ApiOperation(value = \"新增"
                +CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName())+"\")"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "@PostMapping(\"/add\")"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "public ReturnData add(");
        buffer.append("@RequestBody ").append(classMetaData.getClassName()).append(Character.SPACE)
                .append(CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName()))
                .append(") throws Exception {").append(Character.LINE_FEED);
        buffer.append(Character.getSpace(8)).append("return super.add(");
        buffer.append(CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName())).append("Service, ");
        buffer.append(CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName()) +");");
        buffer.append(Character.LINE_FEED).append(Character.getSpace(4)).append("}");
        buffer.append(Character.getLineFeed(2));

        /**
         * 修改接口
         */
        buffer.append(Character.getSpace(4) + "@ApiOperation(value = \"修改"
                +CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName())+"\")"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "@PostMapping(\"/modify\")"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "public ReturnData modify(");
        buffer.append("@RequestBody ").append(classMetaData.getClassName()).append(Character.SPACE)
                .append(CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName()))
                .append(") throws Exception {").append(Character.LINE_FEED);
        buffer.append(Character.getSpace(8)).append("return super.modify(");
        buffer.append(CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName())).append("Service, ");
        buffer.append(CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName()) +");");
        buffer.append(Character.LINE_FEED).append(Character.getSpace(4)).append("}");
        buffer.append(Character.getLineFeed(2));

        /**
         * 删除接口
         */
        buffer.append(Character.getSpace(4) + "@ApiOperation(value = \"删除"
                +CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName())+"\")"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "@GetMapping(\"/remove\")"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "public ReturnData remove(");
        buffer.append("@RequestParam(\""+CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName()) +"Id\")");
        buffer.append("String ").append(CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName()) +"Id)");
        buffer.append(" throws Exception {").append(Character.LINE_FEED);
        buffer.append(Character.getSpace(8)).append("return super.remove(");
        buffer.append(CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName())).append("Service, ");
        buffer.append(CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName()) +"Id);");
        buffer.append(Character.LINE_FEED).append(Character.getSpace(4)).append("}");
        buffer.append(Character.getLineFeed(2));

        /**
         * 搜索接口
         */
        buffer.append(Character.getSpace(4) + "@ApiOperation(value = \"搜索"
                        +CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName())+"\")"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "@GetMapping(\"/search\")"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "public ReturnData search(@RequestParam(value = \"pageSize\", defaultValue = \"10\") Integer pageSize,"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(29)).append("@RequestParam(value = \"pageNum\", defaultValue = \"1\") Integer pageNum");
        buffer.append(") throws Exception {").append(Character.LINE_FEED);
        buffer.append(Character.getSpace(8)).append("return super.search(")
              .append(CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName())).append("Service, new ")
                .append(classMetaData.getClassName()).append("());");
        buffer.append(Character.LINE_FEED).append(Character.getSpace(4)).append("}");
        buffer.append(Character.getLineFeed(2));

        /**
         * get 接口
         */
        buffer.append(Character.getSpace(4) + "@ApiOperation(value = \"获取"+classMetaData.getClassName()+"\")"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4) + "@GetMapping(\"/get\")"+ Character.LINE_FEED);
        buffer.append(Character.getSpace(4)+"public ReturnData get(");
        buffer.append("@RequestParam(\""+CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName()) +"Id\")");
        buffer.append("String ").append(CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName()) +"Id)");
        buffer.append(" throws Exception{").append(Character.LINE_FEED);
        buffer.append(Character.getSpace(8));
        buffer.append("return super.get("+CamelCaseConvert.toLowerCamelCase(classMetaData.getClassName())+"Service,");
        buffer.append(CamelCaseConvert.toLowerCamelCase(classMetaData.getTableName()) +"Id);");
        buffer.append(Character.LINE_FEED).append(Character.getSpace(4)).append("}");
        buffer.append(Character.LINE_FEED);
        return buffer.toString();
    }

    @Override
    public String getFileName() {
        return outputPath + File.separator + classMetaData.getClassName()+"Controller.java";
    }
}