package org.starlight.util;

import java.io.File;
import java.io.IOException;

/**
 * @author huangyong
 * @data 2025/4/9
 */
public class WordToPdfUtil {
    private static void convertWordToPdf(String libreOfficePath, String wordFilePath, String pdfFilePath) throws InterruptedException, IOException {
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
}
