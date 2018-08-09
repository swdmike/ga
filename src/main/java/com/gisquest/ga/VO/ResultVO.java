package com.gisquest.ga.VO;

import lombok.Data;

/**
 * Created by swd on 2018/8/8
 *
 * @Description:
 */
@Data
public class ResultVO<T>
{
    private Integer code;
    private String message;
    private T data;
}
