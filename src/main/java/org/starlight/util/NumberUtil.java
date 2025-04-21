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
            return Math.abs(value) < 1e-10; // 小于一个很小的阈值
        }
        return target.intValue() == 0;
    }

}
