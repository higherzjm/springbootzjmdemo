package com.zjm.baseapplication.sportslottery.vo;

import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhujianming
 */
@Data
@Builder
@ApiModel("体彩历史开奖号码")
@NoArgsConstructor
@AllArgsConstructor
public class SportsLotteryHistoryPrizeVO implements Serializable {
    @ApiModelProperty(value = "类型 1：双色球31选7, 2:大乐透31选5+12选2")
    private String sportsLotteryType;
    @NotNull(message = "开奖时间不能为空")
    @ApiModelProperty(value = "开奖时间", required = true)
    private String prizeDate;
    @ApiModelProperty(value = "开奖号码")
    private String prizeNum;
}
