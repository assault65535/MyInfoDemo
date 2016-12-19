package com.tnecesoc.MyInfoDemo.Widget.Util;

import android.content.Context;
import android.graphics.Color;

import java.util.Comparator;

/**
 * Created by Tnecesoc on 2016/11/18.
 */
public class DisplayUtil {

    public static class MyMath {

        public static <T> T clamp(T value, T min, T max, Comparator<T> comparator) {
            if (comparator.compare(value, min) < 0) {
                return min;
            } else if (comparator.compare(value, max) > 0) {
                return max;
            } else {
                return value;
            }
        }

        public static int clamp(int value, int min, int max) {
            return clamp(value, min, max, new Comparator<Integer>() {
                @Override
                public int compare(Integer lhs, Integer rhs) {
                    return Integer.compare(lhs, rhs);
                }
            });
        }

        public static double clamp(double value, double min, double max) {
            return clamp(value, min, max, new Comparator<Double>() {
                @Override
                public int compare(Double lhs, Double rhs) {
                    return Double.compare(lhs, rhs);
                }
            });
        }

    }


    public static final int colorPrimary = Color.parseColor("#5E9FF2");

    public static int dp2px(Context context, double dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    // get a color between black and colorPrimary.
    public static int getHalftonePrimary(double ratio) {

        ratio = MyMath.clamp(ratio, 0.0, 1.0);

        int r = (int) (Color.red(colorPrimary) * ratio);
        int g = (int) (Color.green(colorPrimary) * ratio);
        int b = (int) (Color.blue(colorPrimary) * ratio);

        return Color.rgb(r, g, b);
    }

}
