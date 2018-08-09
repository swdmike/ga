package com.gisquest.ga.enums;

import lombok.Getter;

/**
 * Created by swd on 2018/8/8
 *
 * @Description:
 */
public enum ResultEnum
{
    PARAM_ERROR(1,"参数不正确"),
    INTERSET_ERROR(10,"参数不正确");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    public Integer getCode()
    {
        return this.code;
    }
}
