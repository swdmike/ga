package com.gisquest.ga.exception;

import com.gisquest.ga.enums.ResultEnum;

/**
 * Created by swd on 2018/8/8
 *
 * @Description:
 */
public class AppException extends RuntimeException
{
    private Integer code;

    public AppException(ResultEnum resultEnum)
    {
        super(resultEnum.getMessage());
        this.code=resultEnum.getCode();
    }

    public AppException(Integer code, String message)
    {
        super(message);
        this.code=code;
    }
}
