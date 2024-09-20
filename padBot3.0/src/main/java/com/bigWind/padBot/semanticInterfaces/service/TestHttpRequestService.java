/*2024-8-1
* jl
* 一个最简单的向模型发送请求的测试用例
* 基于“curl -X POST "http://192.168.1.122:8081/rag_query" -H "Content-Type: application/json" -d '{"question": "请问你是谁？"}'”
* 将传入的String替换到请求体中并发送请求，获得模型返回结果*/
package com.bigWind.padBot.semanticInterfaces.service;

import org.springframework.stereotype.Service;

@Service
public interface TestHttpRequestService {
    public String sendRequest(String question);

    public String sendNormalRequest(String question);
    public String determinApiUrl(String question);
    public String determinApiBody(String question);
    public String extractDestination(String question);
    public String extractMovement(String question);

    String recongnize(String question);
}
