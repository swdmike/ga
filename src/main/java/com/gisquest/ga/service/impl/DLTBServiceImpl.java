package com.gisquest.ga.service.impl;

import com.gisquest.ga.config.AppConfig;
import com.gisquest.ga.domain.DLTB;
import com.gisquest.ga.dto.Area;
import com.gisquest.ga.mapper.DLTBMapper;
import com.gisquest.ga.service.DLTBService;
import com.gisquest.ga.utils.GeometryUtil;
import com.gisquest.ga.utils.ProjectUtil;
import com.gisquest.ga.utils.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by swd on 2018/8/8
 *
 * @Description:
 */
@Service
@Slf4j
public class DLTBServiceImpl implements DLTBService
{
    @Autowired
    private DLTBMapper dltbMapper;

    @Autowired
    private AppConfig appConfig;

    @Override
    public List<DLTB> intersect(String wkt, Integer srid)
    {
        List<DLTB> dltbList = dltbMapper.intersectOne(wkt, srid);
        return dltbList;
    }

    /**
     * 功能描述:
     *
     * @param: areaList
     * @param: srid
     */
    @Override
    public Map<String, List<DLTB>> intersect(List<Area> areaList)
    {
        //先检查参数是否为空
        areaList.forEach(i-> ValidateUtil.ValidateEmptyArea(i));
        Map<String, List<DLTB>> result = new HashMap<>();
        Integer ellipse = appConfig.getEllipse();
        Integer cm = appConfig.getCentralmeridian();
        Integer dai = appConfig.getDai();
        Integer srid = appConfig.getSrid();
        for (Area area : areaList)
        {
            String wkt = area.getWkt();
            //JWD转
            if (area.getJwd())
            {
                String wktNew = ProjectUtil.CovertWKTPolygonJWDToXY(wkt, ellipse, cm, dai);
                wkt = wktNew;
            }
            else {
                wkt = GeometryUtil.roundWKTVertices(wkt);
            }

            List<DLTB> dltbList = intersect(wkt, srid);
            String id = area.getId();
            if (result.containsKey(id))
            {
                //同id
                result.get(id).addAll(dltbList);
            } else
            {
                result.put(area.getId(), dltbList);
            }
        }
        return result;
    }
}
