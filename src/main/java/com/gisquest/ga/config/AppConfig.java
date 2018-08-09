package com.gisquest.ga.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by swd on 2018/8/8
 *
 * @Description:
 */

@Configuration
@ConfigurationProperties(prefix = "ga")
@Data
public class AppConfig
{
    //椭球
    private Integer ellipse;
    //中央经线
    private Integer centralmeridian;
    //代号
    private Integer dai;
    private Integer srid;
}
