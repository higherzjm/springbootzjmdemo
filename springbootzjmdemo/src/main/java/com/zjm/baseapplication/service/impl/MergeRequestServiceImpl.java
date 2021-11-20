package com.zjm.baseapplication.service.impl;

import com.zjm.baseapplication.service.IMergeRequestService;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author zhujianming
 */
@Slf4j
@Service
public class MergeRequestServiceImpl implements IMergeRequestService {
    //积攒请求的阻塞队列
    public static LinkedBlockingDeque deviceCreateQueue = new LinkedBlockingDeque<>();

    @Override
    public Long mergeRequest() {
        log.info("积攒请求-----------------------");
        String productKey = String.valueOf(System.currentTimeMillis());
        String deviceName = String.valueOf(System.currentTimeMillis());
        // 缓存请求 ====== start
        CompletableFuture<Long> completedFuture = new CompletableFuture();
        DeviceCreateRequest deviceCreateRequest = new DeviceCreateRequest();
        deviceCreateRequest.setProductKey(productKey);
        deviceCreateRequest.setDeviceName(deviceName);
        deviceCreateRequest.setRequestSource(UUID.randomUUID().toString());
        deviceCreateRequest.setCompletedFuture(completedFuture);
        deviceCreateQueue.add(deviceCreateRequest);
        // 缓存请求 ====== end
        Long deviceId = null;
        try {
            deviceId = completedFuture.get();
        } catch (Exception e) {

        }
        return deviceId;
    }

    //批量请求
    @PostConstruct //被@PostConstruct注解的方法将在该被创建且该类中所有注入操作完成之后执行
    public void salaryRecheckBatchRequest() {
        //log.info("批量请求-----------------------");
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(100);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // 把出queue的请求存储一次
            List<DeviceCreateRequest> questBak = new ArrayList<>();
            // 批量创建设备的入参
            List<DeviceCreateQuery> deviceCreateQueryList = new ArrayList<>();

            int size = deviceCreateQueue.size();
            for (int i = 0; i < size; i++) {
                DeviceCreateRequest deviceCreateRequest = (DeviceCreateRequest) deviceCreateQueue.poll();
                if (Objects.nonNull(deviceCreateRequest)) {
                    questBak.add(deviceCreateRequest);
                    deviceCreateQueryList.add(buildDeviceCreateQuery(deviceCreateRequest));
                }
            }

            if (!deviceCreateQueryList.isEmpty()) {
                try {
                    List<DeviceCreateResp> response = batchCreateDevice(deviceCreateQueryList);
                    Map<String, Long> collect = response.stream().collect(Collectors.toMap(
                            DeviceCreateResp::getRequestSource, DeviceCreateResp::getDeviceId
                    ));
                    int i=1/0;
                    // 通知请求的线程
                    for (DeviceCreateRequest deviceCreateRequest : questBak) {
                        deviceCreateRequest.getCompletedFuture().complete(collect.get(deviceCreateRequest.getRequestSource()));
                    }
                } catch (Throwable throwable) {
                    // 通知请求的线程-异常
                    questBak.forEach(deviceCreateRequest -> deviceCreateRequest.getCompletedFuture().obtrudeException(throwable));

                }

            }

        }, 0, 30, TimeUnit.MILLISECONDS);
    }

    public List<DeviceCreateResp> batchCreateDevice(List<DeviceCreateQuery> deviceCreateQueryList) {
        log.info("批量创建设备-----------------------");
        List<DeviceCreateResp> deviceCreateRespList = new ArrayList<>();
        for (DeviceCreateQuery deviceCreateQuery : deviceCreateQueryList) {
            deviceCreateRespList.add(DeviceCreateResp.builder().requestSource(deviceCreateQuery.getRequestSource()).deviceId(System.currentTimeMillis()).build());
        }
        return deviceCreateRespList;
    }

    public DeviceCreateQuery buildDeviceCreateQuery(DeviceCreateRequest deviceCreateRequest) {
        log.info("查询设备参数-----------------------");
        return DeviceCreateQuery.builder().name(deviceCreateRequest.getDeviceName())
                .productKey(deviceCreateRequest.getProductKey()).requestSource(deviceCreateRequest.getRequestSource()).build();

    }
}

//请求参数
@Data
@Builder
class DeviceCreateQuery implements Serializable {

    //产品标识
    private String productKey;

    //设备名称
    private String name;

    //请求源，一次批量请求保证唯一
    private String requestSource;

}

//返回值
@Data
@Builder
class DeviceCreateResp implements Serializable {
    //设备ID
    private Long deviceId;
    // 请求源，一次批量请求保证唯一
    private String requestSource;
}

//积攒请求的自定义结构
@Data
class DeviceCreateRequest {

    //产品key
    private String productKey;

    //设备名
    private String deviceName;

    //请求源，需保证唯一
    private String requestSource;

    //CompletableFuture接口
    private CompletableFuture completedFuture;

}
