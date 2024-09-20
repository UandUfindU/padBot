package com.bigWind.padBot.semanticInterfaces.service.impl;

/*2024-8-1
 * jL
 * 接口类，用于将模型请求当中的prompt字符串提取出来*/

import com.bigWind.padBot.semanticInterfaces.bean.Request;
import com.bigWind.padBot.semanticInterfaces.bean.Message;
import com.bigWind.padBot.semanticInterfaces.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public String extractPrompt(Request request){
        if(request !=null && request.getMessage() !=null){
            Message message = request.getMessage();
            String prompt =message.getPrompt();
            return prompt;
        }else{
            return null;
        }
    }
}
