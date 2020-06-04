package com.cn.stardust.star.dbexport.office;

import com.cn.stardust.star.dbexport.metadata.ColumnMetadata;
import com.cn.stardust.star.dbexport.metadata.DatabaseMetadata;
import com.cn.stardust.star.dbexport.metadata.TableMetadata;
import com.cn.stardust.star.dbexport.office.style.CellStyle;
import com.cn.stardust.star.dbexport.office.style.RunStyle;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import java.math.BigInteger;
import java.util.List;

public class TableWriter{


    public static void write(XWPFDocument document,DatabaseMetadata metadata){
        List<TableMetadata> tableMetadatas = metadata.getTableMetadatas();
        for(int i = 0 ; i < tableMetadatas.size() ; i++){
            XWPFParagraph tableTileParagraph = document.createParagraph();
            tableTileParagraph.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run = tableTileParagraph.createRun();
            RunStyle.settableTitleStyle(run);
            String comment = isEmpty(tableMetadatas.get(i).getComment())? "无备注":tableMetadatas.get(i).getComment() ;
            /** 写入表名与备注 */
            run.setText(i+1+"、"+tableMetadatas.get(i).getTableName()+"("+comment+")",0);

            tableTileParagraph = document.createParagraph();
            tableTileParagraph.setAlignment(ParagraphAlignment.LEFT);
            run = tableTileParagraph.createRun();
            RunStyle.settableTitleStyle(run);
            /** 写入存储引擎与建立表日期 */
            run.setText(tableMetadatas.get(i).getEngine()+"/"+tableMetadatas.get(i).getTableCollection()+"/"+tableMetadatas.get(i).getCreateTime());
            /** 创建table */
            createTable(tableMetadatas.get(i),document);
            /** 添加空白段落 */
            document.createParagraph();
        }
    }

    /**
     * 创建table
     * @param tableMetadata
     * @param document
     * @return
     */
    private static XWPFTable createTable(TableMetadata tableMetadata,XWPFDocument document){
        XWPFTable table = document.createTable(tableMetadata.getColumns().size()+1,6);
        initTableCellSize(table);
        XWPFTableRow row = table.getRow(0);
        fillTableTitle(row);
        fillTableBody(table,tableMetadata.getColumns());
        return table;
    }

    private static void initTableCellSize(XWPFTable table){
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        tblPr.getTblW().setType(STTblWidth.DXA);
        tblPr.getTblW().setW(new BigInteger("8500"));
    }

    /**
     * 填充表头
     */
    private static void fillTableTitle(XWPFTableRow row){
        setTitleText(row.getCell(0),"字段名",Boolean.TRUE);
        setTitleText(row.getCell(1),"类型",Boolean.TRUE);
        setTitleText(row.getCell(2),"可为空",Boolean.TRUE);
        setTitleText(row.getCell(3),"主键",Boolean.TRUE);
        setTitleText(row.getCell(4),"备注",Boolean.TRUE);
        setTitleText(row.getCell(5),"默认值",Boolean.TRUE);
    }

    /**
     * 填充表内容
     */
    private static void fillTableBody(XWPFTable table, List<ColumnMetadata> columnMetadata){
        ColumnMetadata metadata;
        for(int i = 0 ; i < columnMetadata.size() ; i++){
            XWPFTableRow row = table.getRow(i+1);
            metadata = columnMetadata.get(i);
            if(isEmpty(metadata.getPrimaryKey())){
                setText(row.getCell(0),metadata.getName().toLowerCase(),i);
                setText(row.getCell(1),metadata.getType(),i);
                setText(row.getCell(2),metadata.getNullable().toLowerCase(),i);
                setText(row.getCell(3),metadata.getPrimaryKey(),i);
                setText(row.getCell(4),metadata.getComment(),i);
                setText(row.getCell(5),metadata.getDefaultValue(),i);
            }else{
                setPrimaryKeyText(row.getCell(0),metadata.getName().toLowerCase());
                setPrimaryKeyText(row.getCell(1),metadata.getType());
                setPrimaryKeyText(row.getCell(2),metadata.getNullable().toLowerCase());
                setPrimaryKeyText(row.getCell(3),metadata.getPrimaryKey());
                setPrimaryKeyText(row.getCell(4),metadata.getComment());
                setPrimaryKeyText(row.getCell(5),metadata.getDefaultValue());
            }
        }
    }


    private static Boolean isEmpty(String content){
        if(null == content || "".equals(content)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private static XWPFRun getRun(XWPFTableCell cell){
        CellStyle.setCenter(cell);
        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        XWPFRun run = paragraph.createRun();
        RunStyle.settableBodyStyle(run);
        return run;
    }

    private static void setTitleText(XWPFTableCell cell,String content,Boolean isBold){
        XWPFRun run = getRun(cell);
        run.setBold(isBold);
        cell.getCTTc().addNewTcPr().addNewShd().setFill("6699FF");
        run.setText(content,0);
    }

    private static void setPrimaryKeyText(XWPFTableCell cell,String content){
        XWPFRun run = getRun(cell);
        cell.getCTTc().addNewTcPr().addNewShd().setFill("FFC125");
        run.setText(content,0);
    }


    private static void setText(XWPFTableCell cell,String content,Integer index){
        XWPFRun run = getRun(cell);
        if(index % 2 == 0){
            cell.getCTTc().addNewTcPr().addNewShd().setFill("B7E8F4");
        }
        run.setText(content,0);
    }
}
