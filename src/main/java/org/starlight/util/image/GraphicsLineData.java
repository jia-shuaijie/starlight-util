package org.starlight.util.image;

import org.starlight.util.enums.GraphicsType;

import java.awt.*;

/**
 * @author 黑色的小火苗
 */
public class GraphicsLineData extends AbstractGraphics {

    /**
     * 目标坐标
     */
    private int x2;
    /**
     * 目标坐标
     */
    private int y2;

    /**
     * 线条宽度
     */
    private int lineWidth;


    /**
     * 构造函数，用于初始化GraphicsLineData对象
     * 该构造函数继承自父类，用于创建一条线的数据对象
     * 它不仅包含线的起点坐标和颜色，还包括线的终点坐标
     *
     * @param x     线起点的x坐标
     * @param y     线起点的y坐标
     * @param color 线的颜色
     * @param type  线的类型
     * @param x2    线终点的x坐标
     * @param y2    线终点的y坐标
     */
    public GraphicsLineData(int x, int y, Color color, GraphicsType type, int x2, int y2) {
        super(x, y, color, type); // 调用父类构造方法初始化起点坐标、颜色和类型
        this.x2 = x2; // 初始化线的终点x坐标
        this.y2 = y2; // 初始化线的终点y坐标
    }


    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    @Override
    public String toString() {
        return "GraphicsLineData{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", color=" + getColor() +
                ", type=" + getType() +
                "x2=" + x2 +
                ", y2=" + y2 +
                ", lineWidth=" + lineWidth +
                '}';
    }
}
