package org.starlight.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author huangyong
 * @data 2025/4/9
 */
public class PdfToImageUtil {


    /**
     * 将PDF文件转换为图片
     * <p>
     * 图片输出为pdf一页一张图片起始为1 输出文件名page-1.png
     *
     * @param pdfFilePath     pdf文件地址
     * @param outputDirectory 图片输出目录
     * @throws IOException io异常报错
     */
    private static void convertPdfToImages(String pdfFilePath, String outputDirectory) throws IOException {
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                // 300 DPI
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300);
                ImageIO.write(bim, "PNG", new File(outputDirectory, "page-" + (page + 1) + ".png"));
            }
        }
    }

}
