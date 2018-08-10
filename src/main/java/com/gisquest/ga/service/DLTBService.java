package com.gisquest.ga.service;

import com.gisquest.ga.domain.DLTB;
import com.gisquest.ga.dto.Area;

import java.util.List;
import java.util.Map;

/**
 * Created by swd on 2018/8/8
 *
 * @Description:
 */
public interface DLTBService
{
    List<DLTB> intersect(String wkt, Integer srid);

    Map<String, List<DLTB>> intersect(List<Area> areaList);
}
