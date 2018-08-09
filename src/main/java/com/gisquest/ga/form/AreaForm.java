package com.gisquest.ga.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Created by swd on 2018/8/8
 *
 * @Description:
 */
@Data
public class AreaForm
{
    @NotEmpty(message = "features不能为空")
    private String features;
}