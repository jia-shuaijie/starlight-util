package org.starlight.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * word工具类
 * <p>
 * springBoot下获取src/main/resources的文件时可以使用org.springframework.util.ResourceUtils.getFile来获取文件
 *
 * @author br.vst
 */
public class WordUtil {
    private final static Logger log = LoggerFactory.getLogger(WordUtil.class);


    /**
     * FreeMarker模板生成word
     *
     * @param ftlTemplatePath 模板名称 src/main/resources/template下模板名称
     *                        示例 src/main/resources/template/test.ftl 传入 test.ftl即可
     * @param data            模板渲染数据
     * @param outputWordPath  输出word路径
     */
    public static void ftlToWord(String ftlTemplatePath, Map<String, Object> data, String outputWordPath) {
        // 初始化 FreeMarker 配置
        // 使用最新版本的 Configuration
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        // 假设模板文件在 classpath 下的 /template 目录
        configuration.setClassForTemplateLoading(WordUtil.class, "/template");
        try {
            // 假设模板文件名为 证书.ftl
            Template template = configuration.getTemplate(ftlTemplatePath);
            // 使用 try-with-resources 确保资源正确关闭
            try (Writer out = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(outputWordPath)), StandardCharsets.UTF_8))) {
                // 渲染模板
                template.process(data, out);
            }
            // 记录成功日志
            log.info("FreeMarker 生成word成功，文件路径: {}", outputWordPath);
        } catch (TemplateException e) {
            // 捕获模板处理异常
            log.error("FreeMarker 模板处理失败，原因: {}", e.getMessage(), e);
        } catch (IOException e) {
            // 捕获 IO 异常
            log.error("文件操作失败，原因: {}", e.getMessage(), e);
        }
    }

    /**
     * 将Word文件转换为PDF文件
     * <p>
     * 使用LibreOffice的命令,将文件转换为pdf
     *
     * @param libreOfficePath libreOffice中的soffice位置
     *                        示例: /libreoffice24.8/program/soffice
     * @param wordFilePath    word文件路径 word全路径
     *                        示例: /word/test.docx
     * @param pdfFilePath     pdf存储路径 pdf全路径
     *                        示例: /pdf/test.pdf
     * @throws InterruptedException 线程中断报错
     * @throws IOException          io输出异常报错
     * @throws RuntimeException     命令执行失败报错
     */
    private static void convertWordToPdf(String libreOfficePath, String wordFilePath, String pdfFilePath)
            throws InterruptedException, IOException {
        // 构建 LibreOffice 命令
        String[] command = {
                libreOfficePath,
                "--headless",
                "--convert-to",
                "pdf:writer_pdf_Export",
                "--outdir",
                new File(pdfFilePath).getParent(),
                wordFilePath
        };
        // 执行命令
        Process process = Runtime.getRuntime().exec(command);
        if (process.waitFor() != 0) {
            throw new RuntimeException("Failed to convert Word to PDF");
        }
    }

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