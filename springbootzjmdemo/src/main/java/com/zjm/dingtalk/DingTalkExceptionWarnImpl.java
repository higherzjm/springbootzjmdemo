package com.zjm.dingtalk;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Component
@Slf4j
public class DingTalkExceptionWarnImpl implements ExceptionWarn {


    @Override
    public void execute(WarnContent content) {
        String secret="SECad6f95819fcfd1620cb267d9850d0336b9e8e046234f5c3e7ef48c4b4dae7543";
        String accessToken="8a9fcc774fe636f73c486c0d400b96a74d546d3d32f0f62abfef666b43609ec2";
        try {
            Long timestamp = System.currentTimeMillis();
            String stringToSign = timestamp + "\n" +secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(secret.getBytes("UTF-8"));
            String signature = new String(Base64.encodeBase64(signData));

            Map<String, String> headers = Maps.newHashMap();
            headers.put("Content-Type", "application/json");

            Map<String, String> querys = Maps.newHashMap();
            querys.put("access_token", accessToken);
            querys.put("timestamp", timestamp + "");
            querys.put("sign", signature);
            HttpResponse response = doPost(
                    "https://oapi.dingtalk.com", "/robot/send", headers, querys, dingtalkFormat(content));

            WarnResult res = getResult(response, WarnResult.class);

            if (res.getErrcode() != 0) {
                log.error(res.getErrcode() + "", res.getErrmsg());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 转换钉钉格式
     *
     * @param content
     * @return
     */
    public String dingtalkFormat(WarnContent content) {
        String title = content.getTitle();
        String profile = "dev"+ ":" + "1.0";
        content.setTitle("[" + profile + "][zjmProject]" + title);

        Map<String, String> text = Maps.newHashMap();
        text.put("content", content.getTitle() + "\n" + content.getText());

        Map<String, Object> data = Maps.newLinkedHashMap();
        data.put("msgtype", "text");
        data.put("text", text);
        Map<String, Object> at = new HashMap<>();
        at.put("isAtAll", true);
        data.put("at", at);
        return JSON.toJSONString(data);
    }
    public static HttpResponse doPost(String host, String path,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      String body)
            throws Exception {
        HttpClient httpClient = wrapClient(host,path);
        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        if(headers != null) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }
        }
        if (StringUtils.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }
        return httpClient.execute(request);
    }
    /**
     * 获取结果
     * @param httpResponse
     * @param cls
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T getResult(HttpResponse httpResponse, Class<T> cls) throws IOException {
        return JSON.parseObject(getString(httpResponse), cls);
    }

    /**
     * 将结果转换成string
     * @param httpResponse
     * @return
     * @throws IOException
     */
    public static String getString(HttpResponse httpResponse) throws IOException {
        HttpEntity entity = httpResponse.getEntity();
        String resp = EntityUtils.toString(entity, "UTF-8");
        EntityUtils.consume(entity);
        return resp;
    }
    /**
     * 获取 HttpClient
     * @param host
     * @param path
     * @return
     */
    private static HttpClient wrapClient(String host,String path) {
        HttpClient httpClient = HttpClientBuilder.create().build();
       /* if (host != null && host.startsWith("https://")) {
            return sslClient();
        }else if (StringUtils.isBlank(host) && path != null && path.startsWith("https://")) {
            return sslClient();
        }*/
        return httpClient;
    }
    private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        if (!StringUtils.isBlank(host)) {
            sbUrl.append(host);
        }
        if (!StringUtils.isBlank(path)) {
            sbUrl.append(path);
        }
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (!StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isBlank(query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }
        return sbUrl.toString();
    }

}