package com.gisquest.ga.utils;

import com.esri.core.geometry.Geometry;
import com.google.common.math.DoubleMath;

import java.math.RoundingMode;

/**
 * Created by swd on 2018/8/13
 *
 * @Description:
 */
public class MathUtil
{
    /**
     * 四舍五入保留几位小数
     */
    public static double round(double x, int decimal)
    {
        double pow = Math.pow(10, decimal);
        int n = (int)Double.valueOf(pow).longValue();
        x = x * n;
        x = DoubleMath.roundToInt(x, RoundingMode.HALF_UP);
        x = x / n;
        return x;
    }
}
