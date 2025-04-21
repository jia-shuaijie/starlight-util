package org.starlight.util.image;

import org.starlight.util.enums.GraphicsType;

import java.awt.*;

/**
 * @author br.vst
 */
public class GraphicsTextData extends AbstractGraphics {

    /**
     * 文本样式
     */
    private Font font;

    /**
     * 文本
     */
    private String text;

    /**
     * GraphicsTextData类的构造器，用于创建文本图形对象
     * 该构造器初始化了文本的位置、颜色、类型、字体和内容
     *
     * @param x 文本的起始X坐标
     * @param y 文本的起始Y坐标
     * @param color 文本的颜色
     * @param type 文本的类型，用于描述文本的样式或功能
     * @param font 文本使用的字体
     * @param text 要显示的具体文本内容
     */
    public GraphicsTextData(int x, int y, Color color, GraphicsType type, Font font, String text) {
        // 调用父类构造器，初始化位置、颜色和类型
        super(x, y, color, type);
        // 初始化文本使用的字体
        this.font = font;
        // 初始化文本内容
        this.text = text;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "GraphicsTextData{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", color=" + getColor() +
                ", type=" + getType() +
                "font=" + font +
                ", text='" + text + '\'' +
                '}';
    }
}
