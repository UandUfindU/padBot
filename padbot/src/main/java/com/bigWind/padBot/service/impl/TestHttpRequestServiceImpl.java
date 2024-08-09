package com.bigWind.padBot.service.impl;

import com.bigWind.padBot.service.HttpRequestService;
import com.bigWind.padBot.service.TestHttpRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

@Service
public class TestHttpRequestServiceImpl implements TestHttpRequestService {
    @Autowired
    private HttpRequestService httpRequestService;

    @Override
    public String sendRequest(String question) {
        String apiUrl = "http://192.168.1.122:8081/rag_query";

        // 构建请求体
        String requestBody = "{\"question\": \"" + question + "\"}";

        // 发送请求并返回响应
        return httpRequestService.sendPostRequest(apiUrl, requestBody);
    }

    @Override
    public String sendMeetingRequest(String question) {
        String apiUrl = "http://192.168.1.122:8081/meeting";
        // 构建请求体
        String requestBody = "{\"question\": \"" + question + "\"}";
        // 发送请求并返回响应
        return httpRequestService.sendPostRequest(apiUrl, requestBody);
    }

    public String getNavigationRequest() throws NoSuchAlgorithmException {
        String url = "http://s.padbot.cn:9080/cloud/openapimap/loadAllIndoorMapAndCruisePath.action";

        long currentUnixTime = Instant.now().getEpochSecond();
        String strToEncode = "serialNumber:PX9286,time:" + currentUnixTime + ",appkey:dc9aef3929cc45f28a5415bf56a16b9e,apptoken:iyHvoRPIaCUjr7Xe";
        System.out.println(strToEncode);

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(strToEncode.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md5.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        String md5EncodedStr = sb.toString();
        System.out.println(md5EncodedStr);

        // 请求参数
        String data = "{ \"system\": { \"time\": " + currentUnixTime + ", \"appkey\": \"dc9aef3929cc45f28a5415bf56a16b9e\", \"language\": \"zh-CN\", \"sign\": \"" + md5EncodedStr + "\" }, \"serialNumber\": \"PX9286\" }";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(data, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // 打印响应内容
        System.out.println(response.getStatusCodeValue());
        System.out.println(response.getBody());

        return response.getBody();
    }
}
