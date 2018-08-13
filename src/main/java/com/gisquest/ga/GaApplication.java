package com.gisquest.ga;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.gisquest.ga.mapper")
@EnableCaching
public class GaApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(GaApplication.class, args);
    }
}
