package com.gisquest.ga.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gisquest.ga.VO.ResultVO;
import com.gisquest.ga.config.AppConfig;
import com.gisquest.ga.converter.AreaFormToAreaConverter;
import com.gisquest.ga.domain.DLTB;
import com.gisquest.ga.dto.Area;
import com.gisquest.ga.enums.ResultEnum;
import com.gisquest.ga.exception.AppException;
import com.gisquest.ga.form.AreaForm;
import com.gisquest.ga.service.DLTBService;
import com.gisquest.ga.utils.ExceptionUtil;
import com.gisquest.ga.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by swd on 2018/8/8
 *
 * @Description:
 */
@RestController
@RequestMapping("/dltb")
@Slf4j
public class DLTBController
{
    @Autowired
    private DLTBService dltbService;
    @Autowired
    private AppConfig appConfig;

    //创建订单
    @PostMapping("/intersect")
    public ResultVO<Map<String, String>> intersect(@Valid AreaForm form, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            log.error("intersect参数不正确, form={}", form);
            throw new AppException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        try
        {
            List<Area> areaList = AreaFormToAreaConverter.convert(form);
            Integer srid = appConfig.getSrid();
            Map<String, List<DLTB>> intersect = dltbService.intersect(areaList, srid);
            return ResultVOUtil.success(intersect);
        }
        catch (Exception ex)
        {
            log.error("intersect出错, message={}, trace={}", ex.getMessage(), ExceptionUtil.getStackTrace(ex));
            throw ex;
        }
    }
}
