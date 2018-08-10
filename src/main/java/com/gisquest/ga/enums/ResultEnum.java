package com.gisquest.ga.enums;

import lombok.Getter;

/**
 * Created by swd on 2018/8/8
 *
 * @Description:
 */
@Getter
public enum ResultEnum
{
    PARAM_ERROR(1,"参数不正确"),
    GEOMETRY_EMPTY(10, "空几何");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message)
    {
        this.code = code;
        this.message = message;
    }

//    public String getMessage(){
//        return this.message;
//    }
//
//    public Integer getCode()
//    {
//        return this.code;
//    }
}
