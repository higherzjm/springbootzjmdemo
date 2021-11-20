package com.zjm.baseapplication.VO;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.CompletableFuture;

@Data
@Builder
public class SalaryRecheckMergeRequestVO{
    private String requestPath;
    private String requestName;
    private String paramJsonStr;
    //CompletableFuture接口
    private CompletableFuture completedFuture;
}