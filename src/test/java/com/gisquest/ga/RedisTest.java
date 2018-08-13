package com.gisquest.ga;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by swd on 2018/8/13
 *
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest
{
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void set()
    {
        redisTemplate.opsForValue().set("栋栋", "美女最爱");
        String swd = redisTemplate.opsForValue().get("栋栋").toString();
        System.out.println(swd);
    }
}

