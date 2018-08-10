package com.gisquest.ga.utils;

import com.gisquest.ga.dto.Area;
import com.gisquest.ga.enums.ResultEnum;
import com.gisquest.ga.exception.AppException;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by swd on 2018/8/10
 *
 * 自定义验证器
 */
@Slf4j
public class ValidateUtil
{
    public static void ValidateEmptyArea(Area area)
    {
        String id = area.getId();
        if (Strings.isNullOrEmpty(id))
        {
            String msg = "id为空";
            log.error(msg);
            throw new AppException(ResultEnum.PARAM_ERROR.getCode(),
                    msg);
        }
        String wkt = area.getWkt();
        if (Strings.isNullOrEmpty(wkt))
        {
            String msg = "wkt为空";
            log.error(msg);
            throw new AppException(ResultEnum.PARAM_ERROR.getCode(),
                    msg);
        }
        Boolean jwd = area.getJwd();
        if (jwd == null)
        {
            String msg = "jwd为空";
            log.error(msg);
            throw new AppException(ResultEnum.PARAM_ERROR.getCode(),
                    msg);
        }
    }
}