package com.inke.childstudy.utils;

public class NumUtils {
    public static int[] getNumAttr(int num, int[][] attr) {
        int right = num % 10;
        int left = num / 10;
        if(left > 0) {
            return combineAttr(getLeftNumAttr(left, attr), getRightNumAttr(right, attr));
        } else {
            return getRightNumAttr(num, attr);
        }
    }

    public static int[] getRightNumAttr(int num, int[][] attr) {
        switch (num) {
            case 0:
                return new int[]{attr[0][2], attr[0][3], attr[1][2], attr[1][3], attr[3][2], attr[3][3], attr[4][2], attr[4][3]};
            case 1:
                return new int[]{attr[1][3], attr[3][3]};
            case 2:
                return new int[]{attr[0][2], attr[0][3], attr[1][3], attr[2][2], attr[2][3], attr[3][2], attr[4][2], attr[4][3]};
            case 3:
                return new int[]{attr[0][2], attr[0][3], attr[1][3], attr[2][2], attr[2][3], attr[3][3], attr[4][2], attr[4][3]};
            case 4:
                return new int[]{attr[1][2], attr[1][3], attr[2][2], attr[2][3], attr[3][3]};
            case 5:
                return new int[]{attr[0][2], attr[0][3], attr[1][2], attr[2][2], attr[2][3], attr[3][3], attr[4][2], attr[4][3]};
            case 6:
                return new int[]{attr[0][2], attr[0][3], attr[1][2], attr[2][2], attr[2][3], attr[3][2], attr[3][3], attr[4][2], attr[4][3]};
            case 7:
                return new int[]{attr[0][2], attr[0][3], attr[1][3], attr[3][3]};
            case 8:
                return new int[]{attr[0][2], attr[0][3], attr[1][2], attr[1][3], attr[2][2], attr[2][3],attr[3][2], attr[3][3], attr[4][2], attr[4][3]};
            case 9:
                return new int[]{attr[0][2], attr[0][3], attr[1][2], attr[1][3], attr[2][2], attr[2][3], attr[3][3], attr[4][2], attr[4][3]};
        }
        return null;
    }

    public static int[] getLeftNumAttr(int num, int[][] attr) {
        switch (num) {
            case 0:
                return new int[]{attr[0][0], attr[0][2], attr[1][0], attr[1][1], attr[3][0], attr[3][1], attr[4][0], attr[4][1]};
            case 1:
                return new int[]{attr[1][1], attr[3][1]};
            case 2:
                return new int[]{attr[0][0], attr[0][1], attr[1][1], attr[2][0], attr[2][1], attr[3][0], attr[4][0], attr[4][1]};
            case 3:
                return new int[]{attr[0][0], attr[0][1], attr[1][1], attr[2][0], attr[2][1], attr[3][1], attr[4][0], attr[4][1]};
            case 4:
                return new int[]{attr[1][0], attr[1][1], attr[2][0], attr[2][1], attr[3][1]};
            case 5:
                return new int[]{attr[0][0], attr[0][1], attr[1][0], attr[2][0], attr[2][1], attr[3][1], attr[4][0], attr[4][1]};
            case 6:
                return new int[]{attr[0][0], attr[0][1], attr[1][0], attr[2][0], attr[2][1], attr[3][0], attr[3][1], attr[4][0], attr[4][1]};
            case 7:
                return new int[]{attr[0][0], attr[0][1], attr[1][1], attr[3][1]};
            case 8:
                return new int[]{attr[0][0], attr[0][1], attr[1][0], attr[1][1], attr[2][0], attr[2][1],attr[3][0], attr[3][1], attr[4][0], attr[4][1]};
            case 9:
                return new int[]{attr[0][0], attr[0][1], attr[1][0], attr[1][1], attr[2][0], attr[2][1], attr[3][1], attr[4][0], attr[4][1]};
        }
        return null;
    }

    public static int[] combineAttr(int[] attr1, int[] attr2) {
        int[] attr = new int[attr1.length + attr2.length];
        for (int i=0;i<attr.length;i++) {
            if(i < attr1.length) {
                attr[i] = attr1[i];
            } else {
                attr[i] = attr2[i-attr1.length];
            }
        }
        return attr;
    }
}
