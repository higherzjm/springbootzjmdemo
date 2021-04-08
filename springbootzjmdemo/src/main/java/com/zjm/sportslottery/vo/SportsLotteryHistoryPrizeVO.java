package com.zjm.sportslottery.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhujianming
 */
@Data
@Builder
@ApiModel("体彩历史开奖号码")
@NoArgsConstructor
@AllArgsConstructor
public class SportsLotteryHistoryPrizeVO {
    @ApiModelProperty(value = "类型")
    private String type;
    @ApiModelProperty(value = "开奖时间")
    private String prizeDate;
    @ApiModelProperty(value = "开奖号码")
    private String prizeNum;
}
