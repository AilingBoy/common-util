package com.cn.stardust.tool.bpmn;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author stardust
 * @date 2020/10/30 12:42
 *
 * bpmn 文件转换
 * 从camunda 流程引擎绘图工具 Camunda Modeler所绘制的流程图.bpmn文件。
 * 无法直接用于activiti流程引擎，解析报错。
 * 因此做一步转换，以便后期直接使用Camunda Modeler 绘制流程图。
 * 交由activiti 引擎执行。
 *
 */
public class CamundaBpmnFileConvert {

    /**
     * 示例
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        start("**************\\Desktop\\CamundaBpmn.bpmn");
    }

    /**
     * 转换入口
     * @param filePath 待转换的Bpmn文件本地路径
     * @throws Exception
     */
    public static void start(String filePath)throws Exception{
        File file = new File(filePath);
        if(!file.exists() || file.isDirectory()){
            throw new Exception("待转换文件不合法");
        }
        String[] paths = file.getAbsolutePath().split("\\.");
        String convertPath = paths[0]+"(Converted)."+paths[1];
        convertBpmn(file,convertPath);
        System.out.println("********************<转换完毕>********************");
    }

    private static void convertBpmn(File file,String outPath)throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Files.copy(file.toPath(),outputStream);
        String content =  outputStream.toString();
        Document xmlDoc = Jsoup.parse(content, new String(), Parser.xmlParser());
        convertDefinitions(xmlDoc.getElementsByTag("bpmn:definitions").get(0));
        convertProcess(xmlDoc.getElementsByTag("bpmn:process").get(0));
        convertBaseNode(xmlDoc);
        convertUserTask(xmlDoc.getElementsByTag("bpmn:userTask"));
        convertGateWay(xmlDoc.getElementsByTag("bpmn:exclusiveGateway"));
        List<String> nIds = convertSequenceFlow(xmlDoc.getElementsByTag("bpmn:sequenceFlow"));
        convertServiceTask(xmlDoc.getElementsByTag("bpmn:serviceTask"));
        convertDiagram(xmlDoc.getElementsByTag("bpmndi:BPMNDiagram").get(0));
        String convertText = xmlDoc.html();
        convertText = removeLineCharacter(convertText,nIds);
        new FileOutputStream(outPath).write(convertText.getBytes());
    }

    /**
     * 转化抬头
     * @param element
     * @return
     */
    private static Element convertDefinitions(Element element){
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
    private static Element convertProcess(Element element){
        //转换 proecss 标签
        element.tagName("process");
        element.attr("processType","None");
        element.attr("isClosed","false");
        return element;
    }

    /**
     * 转换基本节点（start/end event）
     * @param xmlDoc
     * @return
     */
    private static void convertBaseNode(Document xmlDoc){
        Element startEvent = xmlDoc.getElementsByTag("bpmn:startEvent").get(0);
        Element endEvent = xmlDoc.getElementsByTag("bpmn:endEvent").get(0);
        startEvent.tagName("startEvent");
        startEvent.attr("name",startEvent.tagName());
        List<Element> elementList = startEvent.children();
        elementList.forEach(e ->e.remove());

        endEvent.tagName("endEvent");
        endEvent.attr("name",startEvent.tagName());
        elementList = endEvent.children();
        elementList.forEach(e ->e.remove());
    }

    /**
     * 转化用户任务
     * @param elements
     * 替换 标签名称，以及内部属性
     */
    private static void convertUserTask(Elements elements){
        Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()){
            Element element = iterator.next();
            // 重命名标签名
            element.tagName("userTask");
            // 替换任务处理人
            String assign = element.attr("camunda:assignee");
            element.removeAttr("camunda:assignee");
            element.attr("activiti:assignee",assign);
            // 默认排他执行
            element.attr("activiti:exclusive","true");
            for(Element e : element.children()){
                if(e.tagName().equals("bpmn:incoming") || e.tagName().equals("bpmn:outgoing")){
                    e.remove();
                }
            }
        }
    }

    /**
     * 转化自动服务任务
     * @param elements
     * 替换 标签名称，以及内部属性
     */
    private static void convertServiceTask(Elements elements){
        Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()){
            Element element = iterator.next();
            // 重命名标签名
            element.tagName("serviceTask");
            // 替换执行器
            String exeHandler = element.attr("camunda:expression");
            element.removeAttr("camunda:expression");
            element.attr("activiti:expression",exeHandler);
            // 默认排他执行
            element.attr("activiti:exclusive","true");
            for(Element e : element.children()){
                if(e.tagName().equals("bpmn:incoming") || e.tagName().equals("bpmn:outgoing")){
                    e.remove();
                }
            }
        }
    }

    /**
     * 转换网关
     * @param elements
     */
    private static void convertGateWay(Elements elements){
        Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()){
            Element element = iterator.next();
            // 重命名标签名
            element.tagName("exclusiveGateway");
            element.attr("name",element.tagName());
            element.attr("gatewayDirection","Unspecified");
            for(Element e : element.children()){
                if(e.tagName().equals("bpmn:incoming") || e.tagName().equals("bpmn:outgoing")){
                    e.remove();
                }
            }
        }
    }

    /**
     * 转换连接线
     */
    private static List<String> convertSequenceFlow(Elements elements){
        List<String> nIds = new ArrayList<>();
        Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()){
            Element element = iterator.next();
            // 重命名标签名
            element.tagName("sequenceFlow");
            for(Element e : element.children()){
                if(e.tagName().equals("bpmn:conditionExpression")){
                    // 重命名条件标签
                    e.tagName("conditionExpression");
                    // 重新设置表达式属性
                    e.removeAttr("xsi:type");
                    e.attr("xsi:type","tFormalExpression");
                    String content = e.html();
                    String nId = UUID.randomUUID().toString().replaceAll("-","");
                    nIds.add(nId);
                    e.html(nId+content+nId);
                }
            }
        }
        return nIds;
    }

    /**
     * 转换绘图语法
     * @param element
     */
    private static void convertDiagram(Element element){
        for(Element e : element.getElementsByTag("dc:Bounds")){
            e.tagName("omgdc:Bounds");
        }
        for(Element e : element.getElementsByTag("di:waypoint")){
            e.tagName("omgdi:waypoint");
        }
    }

    private static String removeLineCharacter(String content,List<String> nIds){
        if(null == nIds || nIds.size() == 0){
            return content;
        }
        for(String nId : nIds){
            content = content.replaceAll("\n\\s\\s\\s\\s"+nId,"");
            content = content.replaceAll(nId+"\n\\s\\s\\s","");
        }
        return content;
    }

}
