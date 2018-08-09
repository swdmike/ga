package com.gisquest.ga.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gisquest.ga.dto.Area;
import com.gisquest.ga.enums.ResultEnum;
import com.gisquest.ga.exception.AppException;
import com.gisquest.ga.form.AreaForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * Created by swd on 2018/8/8
 *
 * @Description:
 */
@Slf4j
public class AreaFormToAreaConverter
{
    @Autowired
    ObjectMapper mapper;

    public static List<Area> convert(AreaForm form)
    {
        ObjectMapper mapper = new ObjectMapper();

        List<Area> areas;
        try
        {
            areas = mapper.readValue(form.getFeatures(), new TypeReference<List<Area>>(){});
        }
        catch (Exception e)
        {
            log.error("Form对象转换错误, string={}", form.getFeatures());
            throw new AppException(ResultEnum.PARAM_ERROR);
        }

        return areas;
    }

    public static List<Area> convert(String features)
    {
        ObjectMapper mapper = new ObjectMapper();

        Area area;
        try
        {
            area = mapper.readValue(features, new TypeReference<List<Area>>(){});
        }
        catch (Exception e)
        {
            log.error("features转换Area错误, string={}", features);
            throw new AppException(ResultEnum.PARAM_ERROR);
        }

        List<Area> areas = Arrays.asList(area);
        return areas;
    }
}
