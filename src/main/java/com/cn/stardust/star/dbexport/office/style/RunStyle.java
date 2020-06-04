package com.cn.stardust.star.dbexport.office.style;

import org.apache.poi.xwpf.usermodel.XWPFRun;

public class RunStyle {

    /**
     * 文档标题样式
     * @param run
     */
    public static void setTitle1Style(XWPFRun run){
        run.setFontSize(18);
        run.setFontFamily("华文中宋");
        run.setVerticalAlignment("baseline");
    }

    /**
     * 文档副标题样式
     * @param run
     */
    public static void setTitle2Style(XWPFRun run){
        run.setFontSize(12);
        run.setFontFamily("楷体");
        run.setVerticalAlignment("baseline");
    }

    /**
     * 表头段落样式
     * @param run
     */
    public static void settableTitleStyle(XWPFRun run){
        run.setFontSize(12);
        run.setFontFamily("楷体");
        run.setVerticalAlignment("baseline");
    }

    /**
     * 表头段落样式
     * @param run
     */
    public static void settableBodyStyle(XWPFRun run){
        run.setFontSize(10);
        run.setFontFamily("宋体");
        run.setVerticalAlignment("baseline");
    }

    /**
     * 所有权样式
     * @param run
     */
    public static void setAllRightStyle(XWPFRun run){
        run.setFontSize(12);
        run.setFontFamily("仿宋");
        run.setVerticalAlignment("baseline");
    }
}
