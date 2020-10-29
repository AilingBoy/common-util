package com.cn.stardust.tool.bpmn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

/**
 * @author stardust
 * @date 2020/10/29 16:48
 *
 * Camunda BPM文件解析器 解析器
 *
 */
public class CamundaBpmnXmlParser {

    private static Log log = LogFactory.getLog(CamundaBpmnXmlParser.class);

    public static void main(String[] args)throws Exception {
        String filePath = "C:\\Users\\14256\\Desktop\\CamundaBpmn.bpmn";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Files.copy(new File(filePath).toPath(),outputStream);
        String content =  outputStream.toString();
        Document xmlDoc = Jsoup.parse(content, new String(), Parser.xmlParser());
        convertDefinitions(xmlDoc.getElementsByTag("bpmn:definitions").get(0));
        new FileOutputStream("C:\\Users\\14256\\Desktop\\CamundaBpmn(M).bpmn").write(xmlDoc.html().getBytes());
    }

    /**
     * 转化抬头
     * @param element
     * @return
     */
    public static Element convertDefinitions(Element element){
        /**
         * 首先删除所有除id外的所有属性
         */
        String id = element.attr("id");
        element.clearAttributes();
        element.attr("xmlns","http://www.omg.org/spec/BPMN/20100524/MODEL");
        element.attr("xmlns:omgdi","http://www.omg.org/spec/DD/20100524/DI");
        element.attr("xmlns:activiti","http://activiti.org/bpmn");
        element.attr("xmlns:omgdc","http://www.omg.org/spec/DD/20100524/DC");
        element.attr("xmlns:tns","http://www.activiti.org/test");
        element.attr("xmlns:xsd","http://www.w3.org/2001/XMLSchema");
        element.attr("xmlns:bpmndi","http://www.omg.org/spec/BPMN/20100524/DI");
        element.attr("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
        element.attr("expressionLanguage","http://www.w3.org/1999/XPath");
        element.attr("id",id+"");
        element.attr("name","");
        element.attr("targetNamespace","http://www.activiti.org/test");
        element.attr("typeLanguage","http://www.w3.org/2001/XMLSchema");
        element.tagName("definitions");
        return element;
    }

    /**
     * 转化流程实例
     * @param element
     * @return
     */
    public static Element convertProcess(Element element){
        // todo 转换
        return element;
    }
}
