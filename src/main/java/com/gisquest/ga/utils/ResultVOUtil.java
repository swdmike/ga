package com.gisquest.ga.utils;

import com.gisquest.ga.VO.ResultVO;

/**
 * Created by swd on 2018/8/8
 *
 * @Description:
 */
public class ResultVOUtil
{
    public static ResultVO success(Object object)
    {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMessage("success");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success()
    {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg)
    {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(msg);
        return resultVO;
    }
}