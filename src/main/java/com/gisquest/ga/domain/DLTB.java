package com.gisquest.ga.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by swd on 2018/8/7
 *
 * @Description:
 */
@Repository
@Data
public class DLTB implements Serializable
{
    private String dldm;
    private String dlmc;
    private double mj;
    private String wkt;
}
