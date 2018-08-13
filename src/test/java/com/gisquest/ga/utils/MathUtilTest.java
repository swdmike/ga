package com.gisquest.ga.utils;

import com.gisquest.ga.GaApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by swd on 2018/8/13
 *
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MathUtilTest
{

    @Test
    public void round()
    {
        double d = 100.554;
        d = MathUtil.round(d,1);
        Assert.assertTrue("保留一位小数", d == 100.6);
        d = 100.554;
        d = MathUtil.round(d,2);
        Assert.assertTrue("保留两位小数", d == 100.55);
    }
}