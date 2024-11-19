package org.starlight.util.dto.image;

import org.starlight.util.enums.GraphicsType;

import java.awt.*;

/**
 * @author 黑色的小火苗
 */
public class GraphicsShapeData extends AbstractGraphics {
    /**
     * 形状宽度
     */
    private int width;
    /**
     * 形状高度
     */
    private int height;

    public GraphicsShapeData(int x, int y, Color color, GraphicsType type) {
        super(x, y, color, type);
    }

    /**
     * 构造函数，用于初始化GraphicsShapeData对象
     * 继承自父类的构造函数，同时添加宽度和高度属性
     *
     * @param x 形状的x坐标
     * @param y 形状的y坐标
     * @param color 形状的颜色
     * @param type 形状的类型
     * @param width 形状的宽度
     * @param height 形状的高度
     */
    public GraphicsShapeData(int x, int y, Color color, GraphicsType type, int width, int height) {
        // 调用父类构造函数，初始化位置、颜色和类型
        super(x, y, color, type);
        // 初始化形状的宽度
        this.width = width;
        // 初始化形状的高度
        this.height = height;
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
