package com.bigWind.padBot.service.impl;

import com.bigWind.padBot.service.HttpRequestService;
import com.bigWind.padBot.service.TestHttpRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
