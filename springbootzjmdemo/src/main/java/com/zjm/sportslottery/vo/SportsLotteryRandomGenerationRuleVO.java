package com.zjm.sportslottery.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhujianming
 */
@Data
@Builder
@ApiModel("体彩随机生成规则")
@NoArgsConstructor
@AllArgsConstructor
public class SportsLotteryRandomGenerationRuleVO implements Serializable {
    @ApiModelProperty(value = "类型 1：福建体彩31选7, 2:大乐透31选5+12选2")
    private String sportsLotteryType;
    @ApiModelProperty(value = "前区1-10数量")
    private Integer between_1_10_size;
    @ApiModelProperty(value = "前区11-20数量")
    private Integer between_11_20_size;
    @ApiModelProperty(value = "前区21-30数量")
    private Integer between_21_30_size;
    @ApiModelProperty(value = "前区31-35数量")
    private Integer between_31_35_size;
    @ApiModelProperty(value = "后区1-3数量")
    private Integer after_1_3_size;
    @ApiModelProperty(value = "后区4-6数量")
    private Integer after_4_6_size;
    @ApiModelProperty(value = "后区7-9数量")
    private Integer after_7_9_size;
    @ApiModelProperty(value = "后区10-12数量")
    private Integer after_10_12_size;
}
