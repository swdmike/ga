package com.gisquest.ga.service.impl;

import com.gisquest.ga.config.AppConfig;
import com.gisquest.ga.domain.DLTB;
import com.gisquest.ga.dto.Area;
import com.gisquest.ga.service.DLTBService;
import com.gisquest.ga.utils.PerformanceUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by swd on 2018/8/9
 *
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DLTBServiceImplTest
{
    @Autowired
    private DLTBService dltbService;

    @Autowired
    private AppConfig appConfig;

    @Test
    public void intersectBig()
    {
        //叠加区达到1000公顷
        PerformanceUtil.getStartTime();
        String wkt = "POLYGON ((40391395.1348 3220689.4938999992, 40391401.8822 3217301.6693999991, 40394372.2507 3217490.6994000003, 40394749.8248 3220605.9006999992, 40391395.1348 3220689.4938999992))";

        Area area = new Area();
        area.setId("1");
        area.setWkt(wkt);
        area.setJwd(false);
        List<Area> areas = new ArrayList<>();
        areas.add(area);

        Map<String, List<DLTB>> intersects = dltbService.intersect(areas, appConfig.getSrid());
        PerformanceUtil.getEndTime();
        for (Map.Entry<String, List<DLTB>> entry : intersects.entrySet())
        {
            for (DLTB dltb : entry.getValue())
            {
                System.out.println(dltb);
            }
            System.out.println(entry.getValue().size());
        }
        System.out.println("运行时间:" + PerformanceUtil.getUsedTime() + "ms");
        Assert.assertTrue("叠加返回DLTB", intersects.size() > 0);
    }
}