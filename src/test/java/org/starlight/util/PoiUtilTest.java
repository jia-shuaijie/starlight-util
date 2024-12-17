package org.starlight.util;

import org.apache.poi.xwpf.usermodel.*;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoiUtilTest {
    private final static Logger log = LoggerFactory.getLogger(PoiUtilTest.class);

    @Test
    public void test1() {
        XWPFDocument document = new XWPFDocument();
        PoiUtil.setPageSize(document, 29.7, 21);
        PoiUtil.setMargins(document, 1, 1, 1, 1);
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("人教版 八年级上册单词包");
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        PoiUtil.setFontStyle(run, "黑体", 20, "000000", true, false, UnderlinePatterns.NONE);
        document.createParagraph().createRun().addBreak();
        XWPFTable table = document.createTable(1, 3);
        XWPFTableRow row1 = table.getRow(0);
        boolean bold = false;
        int fontSize = 12;
        PoiUtil.tableSetFont(row1, 0, "学员姓名：XXX", "黑体", fontSize, bold);
        PoiUtil.tableSetFont(row1, 1, "打印日期：2024-09-06", "黑体", fontSize, bold);
        PoiUtil.tableSetFont(row1, 2, "打印内容：第1~3关", "黑体", fontSize, bold);
        PoiUtil.setCellWidth(table, 0, 300);
        PoiUtil.setCellWidth(table, 1, 300);
        PoiUtil.setCellWidth(table, 2, 300);
        PoiUtil.setAlignment(row1.getCell(0).getParagraphArray(0), ParagraphAlignment.CENTER);
        PoiUtil.removeBorders(table);
        document.createParagraph().createRun().addBreak();
        createThreeTablesInOneRow(document);

        FileUtil.writeWord(document, "/temp/testWord.docx");
    }


    public void createThreeTablesInOneRow(XWPFDocument document) {
        // 创建第一个表格
        int rowNum = 15;
        int colNum = 2;
        XWPFTable table = document.createTable(rowNum, colNum);
        boolean bold = false;
        int fontSize = 12;

        for (int i = 0; i < rowNum; i++) {
            XWPFTableRow row = table.getRow(i);
            PoiUtil.tableSetFont(row, 0, "course", "黑体", fontSize, bold);
            PoiUtil.tableSetFont(row, 1, "当然", "黑体", fontSize, bold);
        }
        for (int i = 0; i < 3; i++) {
            table.addNewCol();
        }
        removeBorders(table, 2);

        for (int i = 0; i < rowNum; i++) {
            XWPFTableRow row = table.getRow(i);
            PoiUtil.tableSetFont(row, 3, "course", "黑体", fontSize, bold);
            PoiUtil.tableSetFont(row, 4, "当然", "黑体", fontSize, bold);
        }


        for (int i = 0; i < 3; i++) {
            table.addNewCol();
        }
        removeBorders(table, 5);
        for (int i = 0; i < rowNum; i++) {
            XWPFTableRow row = table.getRow(i);
            PoiUtil.tableSetFont(row, 6, "course", "黑体", fontSize, bold);
            PoiUtil.tableSetFont(row, 7, "当然", "黑体", fontSize, bold);
        }

        int size = table.getRow(0).getTableCells().size();
        for (int i = 0; i < size; i++) {
            PoiUtil.setCellWidth(table, i, 100);
        }
        PoiUtil.setCellWidth(table, 2, 1);
        PoiUtil.setCellWidth(table, 5, 1);
        PoiUtil.setCellWidth(table, 0, 150);

        for (int i = 0; i < table.getRow(0).getTableCells().size(); i++) {
            if (i == 2 || i == 5) {
                PoiUtil.setCellWidth(table, i, 10);
                continue;
            }
            PoiUtil.setCellWidth(table, i, 140);
        }
    }

    public void removeBorders(XWPFTable table, int cellIndex) {
        int rowNum = table.getRows().size();
        for (int i = 0; i < rowNum; i++) {
            XWPFTableCell cell = table.getRow(i).getCell(cellIndex);
            CTTc ctTc = cell.getCTTc();
            CTTcBorders borders = ctTc.addNewTcPr().addNewTcBorders();
            STBorder.Enum bolderType = STBorder.NONE;
            // 设置上下左右外边框
            CTBorder tBorder = borders.addNewTop();
            tBorder.setVal(bolderType);
            CTBorder bBorder = borders.addNewBottom();
            bBorder.setVal(bolderType);
        }
    }
}
