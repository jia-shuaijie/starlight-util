package org.starlight.util.image;

import org.starlight.util.enums.GraphicsType;

import java.io.InputStream;

/**
 * @author 黑色的小火苗
 */
public class GraphicsImageData extends AbstractGraphics {
    /**
     * 形状宽度
     */
    private int width;
    /**
     * 形状高度
     */
    private int height;

    /**
     * 图片输入流
     */
    private InputStream imageIns;

    /**
     * 构造GraphicsImageData对象，用于表示图形图像数据
     * 此构造函数继承自父类，并添加了宽度、高度和图像输入流属性
     *
     * @param x        图像数据的x坐标，用于定位图像在平面中的水平位置
     * @param y        图像数据的y坐标，用于定位图像在平面中的垂直位置
     * @param type     图像的类型，使用GraphicsType枚举来表示，以便于类型管理和识别
     * @param width    图像的宽度，以像素为单位，用于定义图像的水平尺寸
     * @param height   图像的高度，以像素为单位，用于定义图像的垂直尺寸
     * @param imageIns 图像的输入流，用于读取图像数据，使图像能够在程序中显示或处理
     */
    public GraphicsImageData(int x, int y, GraphicsType type, int width, int height, InputStream imageIns) {
        // 调用父类构造方法，初始化位置和类型，颜色参数为null
        super(x, y, null, type);
        // 初始化图形图像的宽度
        this.width = width;
        // 初始化图形图像的高度
        this.height = height;
        // 初始化图形图像的输入流
        this.imageIns = imageIns;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public InputStream getImageIns() {
        return imageIns;
    }

    public void setImageIns(InputStream imageIns) {
        this.imageIns = imageIns;
    }

    @Override
    public String toString() {
        return "GraphicsShapeData{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", color=" + getColor() +
                ", type=" + getType() +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
