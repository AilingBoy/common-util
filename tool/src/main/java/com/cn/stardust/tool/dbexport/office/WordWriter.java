package com.cn.stardust.tool.dbexport.office;

import com.cn.stardust.tool.dbexport.metadata.DatabaseMetadata;
import com.cn.stardust.tool.dbexport.office.style.RunStyle;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;

public class WordWriter {

    private InputStream inputStream;

    private XWPFDocument document;

    private void init(){
        inputStream = this.getClass().getResourceAsStream("/tempete/table.docx");
        try {
            document = new XWPFDocument(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeStream();
        }
    }

    /**
     * 开始导出
     * @param metadata 待导出数据
     * @param exportPath 导出目录
     * @return
     */
    public Boolean export(DatabaseMetadata metadata, String exportPath){
        if(null == document){
            init();
        }
        XWPFParagraph paragraph = document.getParagraphs().get(0);
        XWPFRun title1 = document.getParagraphs().get(0).getRuns().get(0);
        title1.setText("欢迎Mysql数据库导出工具",0);
        XWPFRun title2 = document.getParagraphs().get(1).getRuns().get(0);
        title2.setText("数据库用户名:"+metadata.getDbUsername()+"   数据库信息:"+metadata.getIp()+":"+metadata.getDbPort()+"/"+metadata.getDbName(),0);

        XWPFParagraph titlePara = document.getParagraphs().get(2);
        XWPFRun run = titlePara.createRun();
        RunStyle.setAllRightStyle(run);
        run.setText("Export By @KnowNoUnknown   Time:"+ LocalDateTime.now().toString(),0);
        TableWriter.write(document,metadata);
        try {
            document.write(new FileOutputStream(exportPath+"/"+metadata.getDbName()+".docx"));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeStream();
        }
        return Boolean.TRUE;
    }

    private void closeStream(){
        if(null != inputStream){
            try {
                inputStream.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public XWPFDocument getDocument() {
        return document;
    }
}
