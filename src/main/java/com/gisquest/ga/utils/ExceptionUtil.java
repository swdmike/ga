package com.gisquest.ga.utils;

import com.gisquest.ga.VO.ResultVO;

/**
 * Created by swd on 2018/8/8
 *
 * @Description:
 */
public class ExceptionUtil
{
    public static String getStackTrace(Exception e) {
        StringBuffer sb = new StringBuffer();
        if (e != null) {
            for (StackTraceElement element : e.getStackTrace()) {
                sb.append("\r\n\t").append(element);
            }
        }
        return sb.length() == 0 ? "" : sb.toString();
    }
}
