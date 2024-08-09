/*2024-8-1
* jl
* 接口类，用于将模型请求当中的prompt字符串提取出来*/

package com.bigWind.padBot.service;

import com.bigWind.padBot.bean.Request;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {
    String extractPrompt(Request request);
}
