package org.starlight.util.image;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

/**
 * 图片处理工具类
 *
 * @author 黑色的小火苗
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

    private static void setG2d(AbstractGraphics graphics, Graphics2D g2d) {
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

    /**
     * 图片Url转 byte[] 转 Base64
     *
     * @param imageUrl 图片URl
     * @return Base64
     */
    public static String imageUrlToBase64Data(String imageUrl) {
        byte[] bytes = imageUrlToByteArray(imageUrl);
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 图片Url转Byte[]
     *
     * @param imageUrl 网络图片地址
     * @return byte[]
     */
    public static byte[] imageUrlToByteArray(String imageUrl) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            URL url = new URL(imageUrl);
            try (InputStream is = url.openStream()) {
                int nRead;
                byte[] data = new byte[16384];
                while ((nRead = is.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
            } catch (IOException e) {
                throw new RuntimeException("图片Url转换Byte[]失败!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return buffer.toByteArray();

    }

    /**
     * 图片路径转换为byte[]
     *
     * @param imagePath 图片路径
     * @return byte[]
     */
    public static byte[] imageToBinaryData(String imagePath) {
        // 输入校验
        if (imagePath == null || imagePath.isEmpty()) {
            throw new IllegalArgumentException("图片路径不能为空!");
        }

        File file = new File(imagePath);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("指定路径的文件不存在或不是一个有效的文件: " + imagePath);
        }

        try {
            // 读取图片文件
            BufferedImage bufferedImage = ImageIO.read(file);
            if (bufferedImage == null) {
                throw new IOException("无法读取图片文件: " + imagePath);
            }

            // 动态获取图片格式
            String formatName = getImageFormat(file);
            if (formatName == null || formatName.isEmpty()) {
                throw new IOException("无法识别图片格式: " + imagePath);
            }

            // 使用 try-with-resources 确保 ByteArrayOutputStream 正确关闭
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                // 将图片写入流
                ImageIO.write(bufferedImage, formatName, byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            }
        } catch (IOException e) {
            // 增强异常处理，保留原始异常信息
            throw new RuntimeException("图片文件转换Byte[]失败! 路径: " + imagePath, e);
        }
    }

    /**
     * 动态获取图片格式
     */
    private static String getImageFormat(File file) {
        String name = file.getName();
        int dotIndex = name.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < name.length() - 1) {
            return name.substring(dotIndex + 1).toLowerCase();
        }
        return null;
    }


}