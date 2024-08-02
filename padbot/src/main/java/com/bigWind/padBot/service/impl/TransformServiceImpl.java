package com.bigWind.padBot.service.impl;

import com.bigWind.padBot.bean.Request;
import com.bigWind.padBot.bean.Response;
import com.bigWind.padBot.service.MessageService;
import com.bigWind.padBot.service.ModelResponseService;
import com.bigWind.padBot.service.TestHttpRequestService;
import com.bigWind.padBot.service.TransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransformServiceImpl implements TransformService {
    @Autowired
    private MessageService messageService;

    @Autowired
    private ModelResponseService modelResponseService;

    @Autowired
    private TestHttpRequestService testHttpRequestService;

    @Override
    public String processRequest(Request request){
        //1-提取prompt
        String prompt = messageService.extractPrompt(request);
        if (prompt == null) {
            return "{\"error\": \"Invalid request\"}";
        }

        //2-利用prompt拼装请求体，发送请求并接收模型返回
        String externalResponse = testHttpRequestService.sendRequest(prompt);

        //3-处理模型返回
        Response response = modelResponseService.ResponseBuild(request.getSessionId(), "speech", externalResponse);

        //4-格式化response
        String jsonResponse = modelResponseService.convertResponseToJson(response);

        return jsonResponse;
    }
}
