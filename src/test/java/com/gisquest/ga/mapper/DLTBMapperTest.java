package com.gisquest.ga.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gisquest.ga.config.AppConfig;
import com.gisquest.ga.domain.DLTB;
import com.gisquest.ga.dto.Area;
import com.gisquest.ga.utils.PerformanceUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;
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
    public void intersectOne()
    {
        PerformanceUtil.getStartTime();
        String wkt = "POLYGON  (( 40381330.520  3215764.164, 40381330.520  3215850.056, 40381408.062  3215846.477, 40381435.500  3215767.743, 40381330.520  3215764.164))";
        Integer srid = appConfig.getSrid();
        List<DLTB> intersects = dltbMapper.intersectOne(wkt, srid);
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
        List<DLTB> intersects = dltbMapper.intersectOne(wkt, srid);
        PerformanceUtil.getEndTime();
        intersects.forEach(System.out::println);
        System.out.println(intersects.size());
        System.out.println("运行时间:" + PerformanceUtil.getUsedTime() + "ms");
        Assert.assertTrue("叠加返回DLTB", intersects.size() > 0);
    }

    @Test
    @Parameterized.Parameters(method = "ageValues")
    public void insert()
    {
        dltbMapper.delete();
        List<String> wktList = new ArrayList<>();
        wktList.add("POLYGON ((40391395.1348 3220689.4938999992, 40391401.8822 3217301.6693999991, 40394372.2507 3217490.6994000003, 40394749.8248 3220605.9006999992, 40391395.1348 3220689.4938999992))");
        wktList.add("POLYGON ((40391008 3220912, 40391001 3220701, 40391237 3220872, 40391008 3220912), (40391043 3220783, 40391043 3220866, 40391150 3220854, 40391043 3220783))");
        wktList.add("MULTIPOLYGON (((40391029.8874 3220639.2482999992, 40391033.8561 3220441.6042, 40391206.1002 3220443.1917000003, 40391029.8874 3220639.2482999992)), ((40391047.3499 3220659.0921, 40391207.6877 3220476.5292000007, 40391203.719 3220656.7107999995, 40391047.3499 3220659.0921)))");
        wktList.add("POLYGON ((40390637.3405 3220759.8030999992, 40390376.0639 3220677.1206, 40390415.7515 3220329.8543, 40390789.4762 3220369.5418999996, 40390809.32 3220587.8235, 40390637.3405 3220759.8030999992))");
        wktList.add("POLYGON ((40390541.4288 3220240.5571999997, 40390438.9026 3219635.3215999994, 40390753.0959 3219542.7172999997, 40390934.9973 3219790.7646999992, 40390541.4288 3220240.5571999997))");
        wktList.add("POLYGON ((40391804.4199 3221304.1843999997, 40391556.3725 3221142.1267000008, 40391721.7374 3220877.5429, 40392111.9986 3221191.7361999992, 40391804.4199 3221304.1843999997))");
        wktList.add("POLYGON ((40390716.3188 3221505.9296000004, 40390739.4699 3221145.4340000004, 40391486.9193 3221343.8718999997, 40391480.3047 3221552.2316999994, 40390716.3188 3221505.9296000004))");
        List<Area> areas = new ArrayList<>();
        for(String wkt:wktList){
            Area area = new Area();
            area.setJwd(false);
            area.setWkt(wkt);
            area.setId("1");
            areas.add(area);
        }
        Integer i = dltbMapper.insert(areas, appConfig.getSrid());
        System.out.println(i);
        Assert.assertTrue("插入", i > 0);
    }

    @Test
    public void intersect()
    {
        List<DLTB> dltbList = dltbMapper.intersect();
        System.out.println(dltbList.size());
        Assert.assertTrue("图层叠加", dltbList.size() > 0);
    }

    @Test
    public void delete()
    {
        dltbMapper.delete();
    }
}