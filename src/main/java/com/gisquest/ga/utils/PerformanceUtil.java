package com.gisquest.ga.utils;

/**
 * Created by swd on 2018/8/9
 *
 * @Description:
 */
public class PerformanceUtil
{
    private static long startTime;
    private static long endTime;

    public static void getStartTime()
    {
        startTime = System.currentTimeMillis();
    }

    public static void getEndTime()
    {
        endTime = System.currentTimeMillis();
    }

    public static long getUsedTime()
    {
        return endTime - startTime;
    }
}
