/*2024-8-1
* jl
* 向模型端发送请求，为保证可扩展性，参数为url与请求体字符串
* 发送请求后接收模型回复，并输出字符串*/
package com.bigWind.padBot.service;

import org.springframework.stereotype.Service;

@Service
public interface HttpRequestService {
    public String sendPostRequest(String url,String requestBody);
}

