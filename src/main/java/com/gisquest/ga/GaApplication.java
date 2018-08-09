package com.gisquest.ga;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan("com.gisquest.ga.mapper")
public class GaApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(GaApplication.class, args);
    }
}
