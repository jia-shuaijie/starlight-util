package org.starlight.util;

import net.coobird.thumbnailator.Thumbnails;
import org.starlight.util.dto.image.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片处理工具类
 *
 * @author: 黑色的小火苗
 */
public class ImageUtil {

    public static void generateImage(ImageEdit edit) {
        // 读取模板图片
        BufferedImage templateImage;
        try {
            templateImage = ImageIO.read(edit.getIns());
        } catch (IOException e) {
            throw new RuntimeException("读取模板失败!");
        }
        // 获取模板图片的尺寸
        int width = templateImage.getWidth();
        int height = templateImage.getHeight();

        // 创建一个新的BufferedImage对象，用于保存修改后的图片
        BufferedImage modifiedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // 获取Graphics2D对象
        Graphics2D g2d = modifiedImage.createGraphics();
        try {
            // 将模板图片绘制到新的BufferedImage上
            g2d.drawImage(templateImage, 0, 0, null);
            // 遍历GraphicsData对象，依次绘制到新的BufferedImage上
            for (AbstractGraphics graphics : edit.getGraphicsList()) {
                setG2d(graphics, g2d);
            }
            // 保存修改后的图片到文件
            Thumbnails.of(modifiedImage).scale(edit.getScale()).outputQuality(edit.getOutputQuality()).toFile(new File(edit.getCompressTempPath()));
        } catch (IOException e) {
            throw new RuntimeException("保存图片失败!");
        } finally {
            // 释放资源
            g2d.dispose();
        }
    }

    public static void setG2d(AbstractGraphics graphics, Graphics2D g2d) {
        g2d.setColor(graphics.getColor());
        switch (graphics.getType()) {
            case TEXT:
                GraphicsTextData text = (GraphicsTextData) graphics;
                g2d.setFont(text.getFont());
                g2d.drawString(text.getText(), graphics.getX(), graphics.getY());
                break;
            case RECT:
                GraphicsShapeData rectShape = (GraphicsShapeData) graphics;
                g2d.fillRect(graphics.getX(), graphics.getY(), rectShape.getWidth(), rectShape.getHeight());
                break;
            case OVAL:
                GraphicsShapeData ovalShape = (GraphicsShapeData) graphics;
                g2d.fillOval(graphics.getX(), graphics.getY(), ovalShape.getWidth(), ovalShape.getHeight());
                break;
            case LINE:
                GraphicsLineData line = (GraphicsLineData) graphics;
                g2d.setStroke(new BasicStroke(line.getLineWidth()));
                g2d.drawLine(graphics.getX(), graphics.getY(), line.getX2(), line.getY2());
                break;
            case IMAGE:
                // 创建一个新的 BufferedImage，用于保存最终结果
                GraphicsImageData image = (GraphicsImageData) graphics;
                BufferedImage avatar;
                try {
                    InputStream imageIns = image.getImageIns();
                    avatar = ImageIO.read(imageIns);
                    // 将头像绘制到背景图片上
                    g2d.drawImage(avatar, image.getX(), image.getY(), image.getWidth(), image.getHeight(), null);
                    imageIns.close();
                } catch (IOException e) {
                    throw new RuntimeException("读取图片失败");
                }
                break;
            default:
                break;
        }
    }

}