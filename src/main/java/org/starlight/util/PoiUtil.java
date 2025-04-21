package org.starlight.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;


/**
 * @author br.vst
 */
public class PoiUtil {
    public static STBorder.Enum BORDER_NONE = STBorder.NONE;

    /**
     * 整体表格设置文本居中
     *
     * @param table table
     */
    public static void tableSetTextCenter(XWPFTable table) {
        // 遍历表格中的每个单元格并设置文本居中
        for (XWPFTableRow row : table.getRows()) {
            for (XWPFTableCell cell : row.getTableCells()) {
                // 创建段落
                List<XWPFParagraph> paragraphs = cell.getParagraphs();
                XWPFParagraph paragraph = paragraphs.isEmpty() || paragraphs.get(0) == null
                        ? cell.addParagraph()
                        : paragraphs.get(0);
                // 设置段落对齐方式为水平居中
                paragraph.setAlignment(ParagraphAlignment.CENTER);
                // 设置单元格对齐方式为垂直居中
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            }
        }
    }

    /**
     * 设置纸张大小
     *
     * @param document doc对象
     * @param width    宽
     * @param height   长
     */
    public static void setPageSize(XWPFDocument document, double width, double height) {
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        CTPageSz pageSz = sectPr.isSetPgSz() ? sectPr.getPgSz() : sectPr.addNewPgSz();
        pageSz.setW(cmToTwips(width));
        pageSz.setH(cmToTwips(height));
    }

    /**
     * 将表格边框设置为无边框
     *
     * @param table table
     */
    public static void removeBorders(XWPFTable table) {
        CTTblBorders borders = tableGetBorder(table);
        removeTopBorders(borders);
        removeBottomBorders(borders);
        removeLeftBorders(borders);
        removeRightBorders(borders);
        removeBordersH(borders);
        removeBordersV(borders);

    }

    public static void removeBordersH(CTTblBorders borders) {
        borders.addNewInsideH().setVal(BORDER_NONE);
    }

    public static void removeBordersV(CTTblBorders borders) {
        borders.addNewInsideV().setVal(BORDER_NONE);
    }

    /**
     * 移除表格左边框线
     *
     * @param borders 边框设置
     */
    public static void removeLeftBorders(CTTblBorders borders) {
        CTBorder tBorder = borders.addNewLeft();
        tBorder.setVal(BORDER_NONE);
    }

    /**
     * 移除表格右边框线
     *
     * @param borders 边框设置
     */
    public static void removeRightBorders(CTTblBorders borders) {
        CTBorder tBorder = borders.addNewRight();
        tBorder.setVal(BORDER_NONE);
    }


    /**
     * 移除表格下边框线
     *
     * @param borders 边框设置
     */
    public static void removeBottomBorders(CTTblBorders borders) {
        CTBorder tBorder = borders.addNewBottom();
        tBorder.setVal(BORDER_NONE);
    }

    /**
     * 移除表格上边框线
     *
     * @param borders 边框设置
     */
    public static void removeTopBorders(CTTblBorders borders) {
        CTBorder tBorder = borders.addNewTop();
        tBorder.setVal(BORDER_NONE);
    }

    /**
     * 表格获取边框设置
     *
     * @param table table
     * @return CTBorder
     */

    public static CTTblBorders tableGetBorder(XWPFTable table) {
        return table.getCTTbl().getTblPr().addNewTblBorders();
    }

    /**
     * 表格某行某列获取边框设置
     *
     * @param table     table
     * @param rowIndex  行索引
     * @param cellIndex 列索引
     * @return CTTcBorders
     */
    public static CTTcBorders tableCellGetBorder(XWPFTable table, int rowIndex, int cellIndex) {
        XWPFTableCell cell = table.getRow(rowIndex).getCell(cellIndex);
        CTTc ctTc = cell.getCTTc();
        return ctTc.addNewTcPr().addNewTcBorders();
    }


    /**
     * 表格设置字体样式
     * <p>
     * 默认颜色设置为黑色,非斜体
     *
     * @param row        XWPFTableRow
     * @param colIndex   int 列索引
     * @param contents   String 文本内容
     * @param fontFamily String 字体
     * @param fontSize   int 字体大小
     * @param bold       boolean 是否加粗
     */
    public static void tableSetFont(XWPFTableRow row, int colIndex, List<String> contents,
                                    String fontFamily, int fontSize, boolean bold) {
        XWPFTableCell cell = row.getCell(colIndex);
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        setFont(paragraph, contents, fontFamily, fontSize,
                "000000", bold, false, UnderlinePatterns.NONE);
    }

    /**
     * 表格设置字体样式
     * <p>
     * 默认颜色设置为黑色,非斜体
     *
     * @param row        XWPFTableRow
     * @param colIndex   int 列索引
     * @param content    String 文本内容
     * @param fontFamily String 字体
     * @param fontSize   int 字体大小
     * @param bold       boolean 是否加粗
     */
    public static void tableSetFont(XWPFTableRow row, int colIndex, String content,
                                    String fontFamily, int fontSize, boolean bold) {
        XWPFTableCell cell = row.getCell(colIndex);
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        setParagraphSpacing(paragraph, 5, 5, 5, 0);
        setFont(paragraph, content, fontFamily, fontSize,
                "000000", bold, false, UnderlinePatterns.NONE);
    }

    /**
     * 设置页边距
     * <p>
     * 1 厘米 ≈ 28.3464567 点
     * 1 点 = 20 twips
     * <p>
     * 设置页边距（单位：twips，1 twip = 1/20 点）
     *
     * @param document      文档对象
     * @param topMargins    上边距
     * @param bottomMargins 下边距
     * @param leftMargins   左边距
     * @param rightMargins  右边距
     */
    public static void setMargins(XWPFDocument document, int topMargins, int bottomMargins,
                                  int leftMargins, int rightMargins) {
        // 获取或创建节属性
        CTSectPr sectPr = document.getDocument().getBody().isSetSectPr() ?
                document.getDocument().getBody().getSectPr() :
                document.getDocument().getBody().addNewSectPr();

        // 获取或创建页面边距设置
        CTPageMar pageMar = sectPr.isSetPgMar() ? sectPr.getPgMar() : sectPr.addNewPgMar();

        // 上边距 1 英寸
        pageMar.setTop(cmToTwips(topMargins));
        // 下边距 1 英寸
        pageMar.setBottom(cmToTwips(bottomMargins));
        // 左边距 1 英寸
        pageMar.setLeft(cmToTwips(leftMargins));
        // 右边距 1 英寸
        pageMar.setRight(cmToTwips(rightMargins));
    }

    /**
     * 设置列宽度
     *
     * @param table    表格
     * @param colIndex 列索引
     * @param width    宽度
     */
    public static void setCellWidth(XWPFTable table, int colIndex, int width) {
        for (XWPFTableRow row : table.getRows()) {
            XWPFTableCell cell = row.getCell(colIndex);
            cell.setWidthType(TableWidthType.DXA);
            cell.setWidth(toTwips(width).toString());
        }
    }

    /**
     * 表格使用
     * <p>
     * 跨列合并，横着合并 , 都是下标值
     *
     * @param rowIndex 要合并哪一行的列
     * @param fromCell 从哪列开始合并（下标）
     * @param endCell  合并到哪列结束（下标）
     */
    public static void mergeCellsHorizontally(XWPFTable table, int rowIndex, int fromCell, int endCell) {
        for (int cellIndex = fromCell; cellIndex <= endCell; cellIndex++) {
            CTTc ctTc = table.getRow(rowIndex).getCell(cellIndex).getCTTc();
            CTTcPr ctTcPr = ctTc.isSetTcPr() ? ctTc.getTcPr() : ctTc.addNewTcPr();
            CTHMerge hMerge = ctTcPr.isSetHMerge() ? ctTcPr.getHMerge() : ctTcPr.addNewHMerge();
            hMerge.setVal(cellIndex == fromCell ? STMerge.RESTART : STMerge.CONTINUE);
        }
    }

    /**
     * 跨行合并
     *
     * @param table     table对象
     * @param formRow   int 合并起始行
     * @param endRow    int 合并结束行
     * @param cellIndex int 合并的列索引
     */
    public static void mergeCellsVertically(XWPFTable table, int formRow, int endRow, int cellIndex) {
        for (int rowIndex = formRow; rowIndex <= endRow; rowIndex++) {
            CTTc ctTc = table.getRow(rowIndex).getCell(cellIndex).getCTTc();
            CTTcPr ctTcPr = ctTc.isSetTcPr() ? ctTc.getTcPr() : ctTc.addNewTcPr();
            CTVMerge vMerge = ctTcPr.isSetVMerge() ? ctTcPr.getVMerge() : ctTcPr.addNewVMerge();
            vMerge.setVal(rowIndex == formRow ? STMerge.RESTART : STMerge.CONTINUE);
        }
    }


    /**
     * 设置段落样式
     *
     * @param paragraph          XWPFParagraph
     * @param paragraphAlignment 设置段落居中或其他 ParagraphAlignment.CENTER
     */
    public static void setAlignment(XWPFParagraph paragraph, ParagraphAlignment paragraphAlignment) {
        paragraph.setAlignment(paragraphAlignment);
    }

    /**
     * 向 Word 文档中插入图片
     *
     * @param paragraph XWPFParagraph 段落对象
     * @param imagePath String 图片路径
     * @param width     int 图片宽度（单位：像素）
     * @param height    int 图片高度（单位：像素）
     * @throws IOException 如果读取图片文件时发生错误
     */
    public static void insertImage(XWPFParagraph paragraph, String imagePath, int width, int height)
            throws IOException, InvalidFormatException {
        // 读取图片文件
        byte[] pictureData = IOUtils.toByteArray(Files.newInputStream(Paths.get(imagePath)));
        // 设置段落居中对齐
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        // 创建运行对象
        XWPFRun run = paragraph.createRun();

        // 插入图片  // 假设图片是 PNG 格式
        int format = XWPFDocument.PICTURE_TYPE_PNG;
        if (imagePath.toLowerCase().endsWith(".jpg") || imagePath.toLowerCase().endsWith(".jpeg")) {
            format = XWPFDocument.PICTURE_TYPE_JPEG;
        } else if (imagePath.toLowerCase().endsWith(".gif")) {
            format = XWPFDocument.PICTURE_TYPE_GIF;
        } else if (imagePath.toLowerCase().endsWith(".bmp")) {
            format = XWPFDocument.PICTURE_TYPE_BMP;
        }
        run.addPicture(new ByteArrayInputStream(pictureData),
                format, imagePath, Units.toEMU(width), Units.toEMU(height));
    }

    /**
     * 设置段落行间距
     *
     * @param paragraph        XWPFParagraph 段落
     * @param lineSpacingValue double 行间距值
     */
    public static void setLineSpacing(XWPFParagraph paragraph, int lineSpacingValue) {
        CTPPr ppr = paragraph.getCTP().getPPr();
        if (ppr == null) {
            ppr = paragraph.getCTP().addNewPPr();
        }
        CTSpacing spacing = ppr.isSetSpacing() ? ppr.getSpacing() : ppr.addNewSpacing();
        // 单位转换
        spacing.setLine(BigInteger.valueOf(lineSpacingValue));
    }

    /**
     * 设置段落的上下间距
     *
     * @param paragraph   XWPFParagraph 段落
     * @param beforeSpace int 段前间距（单位：磅）
     * @param afterSpace  int 段后间距（单位：磅）
     */
    public static void setParagraphSpacing(XWPFParagraph paragraph, int beforeSpace, int afterSpace,
                                           int leftIndent, int rightIndent) {

        // 获取段落的属性
        CTPPr pPr = paragraph.getCTP().getPPr();
        if (pPr == null) {
            pPr = paragraph.getCTP().addNewPPr();
        }
        // 设置段前间距
        CTSpacing spacing = pPr.isSetSpacing() ? pPr.getSpacing() : pPr.addNewSpacing();
        // 单位转换为 Twips
        spacing.setBefore(toTwips(beforeSpace));
        // 单位转换为 Twips
        spacing.setAfter(toTwips(afterSpace));
        // 获取或创建缩进对象
        CTInd ind = pPr.isSetInd() ? pPr.getInd() : pPr.addNewInd();

        // 设置左缩进，单位转换为 Twips
        ind.setLeft(toTwips(leftIndent));
        // 设置右缩进，单位转换为 Twips
        ind.setRight(toTwips(rightIndent));
    }


    /**
     * 设置段落字体样式
     *
     * @param paragraph         XWPFParagraph 段落
     * @param content           String 文本内容
     * @param fontFamily        String 字体
     * @param fontSize          int 字体大小
     * @param fontColor         String 字体颜色
     * @param bold              boolean 是否加粗
     * @param italic            boolean 是否斜体
     * @param underlinePatterns UnderlinePatterns 设置下划线
     *                          UnderlinePatterns.NONE 表示不添加下划线
     *                          UnderlinePatterns.SINGLE 下划线
     */
    public static void setFont(XWPFParagraph paragraph, String content,
                               String fontFamily, int fontSize, String fontColor,
                               boolean bold, boolean italic,
                               UnderlinePatterns underlinePatterns) {
        XWPFRun run = paragraph.createRun();
        run.setText(content);
        setFontStyle(run, fontFamily, fontSize, fontColor, bold, italic, underlinePatterns);
    }

    /**
     * 设置段落字体样式
     *
     * @param paragraph         XWPFParagraph 段落
     * @param contents          String 文本内容
     * @param fontFamily        String 字体
     * @param fontSize          int 字体大小
     * @param fontColor         String 字体颜色
     * @param bold              boolean 是否加粗
     * @param italic            boolean 是否斜体
     * @param underlinePatterns UnderlinePatterns 设置下划线
     *                          UnderlinePatterns.NONE 表示不添加下划线
     *                          UnderlinePatterns.SINGLE 下划线
     */
    public static void setFont(XWPFParagraph paragraph, List<String> contents, String fontFamily,
                               int fontSize, String fontColor, boolean bold, boolean italic,
                               UnderlinePatterns underlinePatterns) {
        XWPFRun run = paragraph.createRun();
        // 获取迭代器
        Iterator<String> it = contents.iterator();
        while (it.hasNext()) {
            run.setText(it.next());
            if (it.hasNext()) {
                run.addBreak();
            }
        }
        setFontStyle(run, fontFamily, fontSize, fontColor, bold, italic, underlinePatterns);
    }

    /**
     * 设置字体样式
     *
     * @param run               XWPFRun
     * @param fontFamily        String 字体
     * @param fontSize          int 字体大小
     * @param fontColor         String 字体颜色
     * @param bold              boolean 是否加粗
     * @param italic            boolean 是否斜体
     * @param underlinePatterns UnderlinePatterns 设置下划线
     *                          UnderlinePatterns.NONE 表示不添加下划线
     *                          UnderlinePatterns.SINGLE 下划线
     */
    public static void setFontStyle(XWPFRun run, String fontFamily, int fontSize, String fontColor,
                                    boolean bold, boolean italic,
                                    UnderlinePatterns underlinePatterns) {
        // 设置字体
        run.setFontFamily(fontFamily);
        // 设置字体大小
        run.setFontSize(fontSize);
        // 设置字体颜色
        run.setColor(fontColor);
        // 设置加粗
        run.setBold(bold);
        // 设置斜体
        run.setItalic(italic);
        // 设置下划线
        run.setUnderline(underlinePatterns);
    }

    public static BigInteger toTwips(int value) {
        return BigInteger.valueOf(value * 20L);
    }

    /**
     * 单位里面转twips
     *
     * @param cm 厘米
     * @return BigInteger
     */
    public static BigInteger cmToTwips(double cm) {
        return BigInteger.valueOf((long) (cm * 28.35 * 20));
    }

}
