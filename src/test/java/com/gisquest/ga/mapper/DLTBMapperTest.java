package com.gisquest.ga.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gisquest.ga.config.AppConfig;
import com.gisquest.ga.domain.DLTB;
import com.gisquest.ga.dto.Area;
import com.gisquest.ga.utils.PerformanceUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swd on 2018/8/7
 *
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DLTBMapperTest
{
    @Autowired
    private DLTBMapper dltbMapper;

    @Autowired
    private AppConfig appConfig;

    @Test
    public void intersect()
    {
        PerformanceUtil.getStartTime();
        String wkt = "POLYGON  (( 40381330.520  3215764.164, 40381330.520  3215850.056, 40381408.062  3215846.477, 40381435.500  3215767.743, 40381330.520  3215764.164))";
        Integer srid = appConfig.getSrid();
        List<DLTB> intersects = dltbMapper.intersect(wkt, srid);
        PerformanceUtil.getEndTime();
        System.out.println("运行时间:" + PerformanceUtil.getUsedTime() + "ms");
        Assert.assertTrue("叠加返回DLTB", intersects.size() > 0);
        intersects.forEach(System.out::println);
        List<Area> areas = new ArrayList<>();
        Area area = new Area();
        area.setJwd(false);
        area.setWkt("w");
        area.setId("1");
        areas.add(area);
        area = new Area();
        area.setJwd(false);
        area.setWkt("m");
        area.setId("2");
        areas.add(area);
        ObjectMapper mapper = new ObjectMapper();
        String s = null;
        try
        {
            s = mapper.writeValueAsString(areas);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        System.out.println(s);

    }

    @Test
    public void intersectBig()
    {
        //叠加区达到1000公顷
        PerformanceUtil.getStartTime();
        String wkt = "POLYGON ((40391395.1348 3220689.4938999992, 40391401.8822 3217301.6693999991, 40394372.2507 3217490.6994000003, 40394749.8248 3220605.9006999992, 40391395.1348 3220689.4938999992))";
        Integer srid = 4528;//gaConfig.getSrid();
        List<DLTB> intersects = dltbMapper.intersect(wkt, srid);
        PerformanceUtil.getEndTime();
        intersects.forEach(System.out::println);
        System.out.println(intersects.size());
        System.out.println("运行时间:" + PerformanceUtil.getUsedTime() + "ms");
        Assert.assertTrue("叠加返回DLTB", intersects.size() > 0);
    }
}