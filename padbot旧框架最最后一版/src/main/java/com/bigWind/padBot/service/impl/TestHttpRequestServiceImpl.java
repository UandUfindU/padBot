package com.bigWind.padBot.service.impl;

/*2024-8-1
:jl
一个最简单的向模型发送请求的测试用例
基于"curl -X POST "http://192.168.1.122:8081/rag_query"-H "content-Type: application/json" -d '{"question": "请问你是谁?"}'”将传入的string替换到请求体中并发送请求，获得模型返回结果*/

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

    @Override
    public String sendMeetingRequest(String question) {
        String apiUrl = "http://192.168.1.122:8081/meetting";

        // 构建请求体
        String requestBody = "{\"question\": \"" + question + "\"}";

        // 发送请求并返回响应
        return httpRequestService.sendPostRequest(apiUrl, requestBody);
    }
}
