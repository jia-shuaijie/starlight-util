package org.starlight.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
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
}