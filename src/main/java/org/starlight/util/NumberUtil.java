package org.starlight.util;

/**
 * @author br.vst
 */
public class NumberUtil {
    /**
     * 判断是否为空或者0
     *
     * @param target 目标对象
     * @return 为空/0返回true，否则返回false
     */
    public static boolean isNullOrZero(Number target) {
        if (target == null) {
            return true;
        }
        if (target instanceof Double || target instanceof Float) {
            double value = target.doubleValue();
            // 小于一个很小的阈值
            return Math.abs(value) < 1e-10;
        }
        return target.intValue() == 0;
    }

    /**
     * 数字转中文小写数字
     *
     * @param intParam 数字,不可以使用小数
     * @return 中文数字字符串 示例: 2023 => 二〇二三
     */
    public static String numberToChinese(Number intParam) {
        String[] chineseNumbers = {"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        char[] chars = String.valueOf(intParam).toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (c == '.') {
                sb.append("点");
                continue;
            }
            sb.append(chineseNumbers[Integer.parseInt(String.valueOf(c))]);
        }
        return sb.toString();
    }
}
