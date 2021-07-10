package com.zjm.base.service.impl;

import com.zjm.base.service.IMergeRequestService;
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
    //�����������������
    public static LinkedBlockingDeque deviceCreateQueue = new LinkedBlockingDeque<>();

    @Override
    public Long mergeRequest() {
        log.info("��������-----------------------");
        String productKey = String.valueOf(System.currentTimeMillis());
        String deviceName = String.valueOf(System.currentTimeMillis());
        // �������� ====== start
        CompletableFuture<Long> completedFuture = new CompletableFuture();
        DeviceCreateRequest deviceCreateRequest = new DeviceCreateRequest();
        deviceCreateRequest.setProductKey(productKey);
        deviceCreateRequest.setDeviceName(deviceName);
        deviceCreateRequest.setRequestSource(UUID.randomUUID().toString());
        deviceCreateRequest.setCompletedFuture(completedFuture);
        deviceCreateQueue.add(deviceCreateRequest);
        // �������� ====== end
        Long deviceId = null;
        try {
            deviceId = completedFuture.get();
        } catch (Exception e) {

        }
        return deviceId;
    }

    //��������
    @PostConstruct //��@PostConstructע��ķ������ڸñ������Ҹ���������ע��������֮��ִ��
    public void salaryRecheckBatchRequest() {
        log.info("��������-----------------------");
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(100);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // �ѳ�queue������洢һ��
            List<DeviceCreateRequest> questBak = new ArrayList<>();
            // ���������豸�����
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
                    // ֪ͨ������߳�
                    for (DeviceCreateRequest deviceCreateRequest : questBak) {
                        deviceCreateRequest.getCompletedFuture().complete(collect.get(deviceCreateRequest.getRequestSource()));
                    }
                } catch (Throwable throwable) {
                    // ֪ͨ������߳�-�쳣
                    questBak.forEach(deviceCreateRequest -> deviceCreateRequest.getCompletedFuture().obtrudeException(throwable));

                }

            }

        }, 0, 30, TimeUnit.MILLISECONDS);
    }

    public List<DeviceCreateResp> batchCreateDevice(List<DeviceCreateQuery> deviceCreateQueryList) {
        log.info("���������豸-----------------------");
        List<DeviceCreateResp> deviceCreateRespList = new ArrayList<>();
        for (DeviceCreateQuery deviceCreateQuery : deviceCreateQueryList) {
            deviceCreateRespList.add(DeviceCreateResp.builder().requestSource(deviceCreateQuery.getRequestSource()).deviceId(System.currentTimeMillis()).build());
        }
        return deviceCreateRespList;
    }

    public DeviceCreateQuery buildDeviceCreateQuery(DeviceCreateRequest deviceCreateRequest) {
        log.info("��ѯ�豸����-----------------------");
        return DeviceCreateQuery.builder().name(deviceCreateRequest.getDeviceName())
                .productKey(deviceCreateRequest.getProductKey()).requestSource(deviceCreateRequest.getRequestSource()).build();

    }
}

//�������
@Data
@Builder
class DeviceCreateQuery implements Serializable {

    //��Ʒ��ʶ
    private String productKey;

    //�豸����
    private String name;

    //����Դ��һ����������֤Ψһ
    private String requestSource;

}

//����ֵ
@Data
@Builder
class DeviceCreateResp implements Serializable {
    //�豸ID
    private Long deviceId;
    // ����Դ��һ����������֤Ψһ
    private String requestSource;
}

//����������Զ���ṹ
@Data
class DeviceCreateRequest {

    //��Ʒkey
    private String productKey;

    //�豸��
    private String deviceName;

    //����Դ���豣֤Ψһ
    private String requestSource;

    //CompletableFuture�ӿ�
    private CompletableFuture completedFuture;

}
