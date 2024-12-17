package org.starlight.util;

public class NumberTest {


    public static void main(String[] args) {
        Double d = 0.00d;
        int i = 0;
        System.out.println(nullOrZero(i));
    }

    public static boolean nullOrZero(Number target) {
        return target == null || target.intValue() == 0;
    }
}
