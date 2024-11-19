package org.starlight.util.dto.image;

import org.starlight.util.enums.GraphicsType;

import java.awt.*;

/**
 * @author 黑色的小火苗
 */
public abstract class AbstractGraphics {
    /**
     * 坐标
     */
    private int x;
    /**
     * 坐标
     */
    private int y;
    /**
     * 文本颜色
     */
    private Color color;

    private GraphicsType type;

    public GraphicsType getType() {
        return type;
    }

    public void setType(GraphicsType type) {
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public AbstractGraphics(int x, int y, Color color, GraphicsType type) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.type = type;
    }

    @Override
    public String toString() {
        return "AbstractGraphics{" +
                "x=" + x +
                ", y=" + y +
                ", color=" + color +
                ", type=" + type +
                '}';
    }
}
