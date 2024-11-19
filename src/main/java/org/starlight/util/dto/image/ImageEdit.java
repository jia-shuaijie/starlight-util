package org.starlight.util.dto.image;

import java.io.InputStream;
import java.util.List;

/**
 * 图片编辑参数
 *
 * @author 黑色的小火苗
 */
public class ImageEdit {
    /**
     * 图片压缩前临时路径
     */
    private String compressTempPath;

    /**
     * 图片模板读取的输入流
     */
    private InputStream ins;

    /**
     * 要添加的图片数据集合
     */
    private List<AbstractGraphics> graphicsList;

    /**
     * 图片尺寸缩放比例 0~1.0,默认1.0
     */
    private double scale;
    /**
     * 图片输出质量 0~1.0,默认1.0
     */
    private double outputQuality;

    public ImageEdit() {
    }

    public ImageEdit(String compressTempPath, InputStream ins, List<AbstractGraphics> graphicsList) {
        this.compressTempPath = compressTempPath;
        this.ins = ins;
        this.graphicsList = graphicsList;
        this.scale = 1.0;
        this.outputQuality = 1.0;
    }

    public ImageEdit(String compressTempPath, InputStream ins, List<AbstractGraphics> graphicsList, double scale, double outputQuality) {
        this.compressTempPath = compressTempPath;
        this.ins = ins;
        this.graphicsList = graphicsList;
        this.scale = scale;
        this.outputQuality = outputQuality;
    }

    public String getCompressTempPath() {
        return compressTempPath;
    }

    public void setCompressTempPath(String compressTempPath) {
        this.compressTempPath = compressTempPath;
    }

    public InputStream getIns() {
        return ins;
    }

    public void setIns(InputStream ins) {
        this.ins = ins;
    }

    public List<AbstractGraphics> getGraphicsList() {
        return graphicsList;
    }

    public void setGraphicsList(List<AbstractGraphics> graphicsList) {
        this.graphicsList = graphicsList;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getOutputQuality() {
        return outputQuality;
    }

    public void setOutputQuality(double outputQuality) {
        this.outputQuality = outputQuality;
    }

    @Override
    public String toString() {
        return "ImageEdit{" +
                "compressTempPath='" + compressTempPath + '\'' +
                ", ins=" + ins +
                ", graphicsList=" + graphicsList +
                ", scale=" + scale +
                ", outputQuality=" + outputQuality +
                '}';
    }
}