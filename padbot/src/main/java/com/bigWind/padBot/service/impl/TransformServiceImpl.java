package com.bigWind.padBot.service.impl;

/*2024-8-1
 * j
 *封装所有服务类，形成工作流，从request到response Json格式字符串*/

import com.bigWind.padBot.bean.Request;
import com.bigWind.padBot.bean.Response;
import com.bigWind.padBot.bean.response.TempEvent;
import com.bigWind.padBot.service.MessageService;
import com.bigWind.padBot.service.ModelResponseService;
import com.bigWind.padBot.service.TestHttpRequestService;
import com.bigWind.padBot.service.TransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TransformServiceImpl implements TransformService {
    @Autowired
    private MessageService messageService;

    @Autowired
    private ModelResponseService modelResponseService;

    @Autowired
    private TestHttpRequestService testHttpRequestService;

    @Resource
    private RegularExpressionServiceImpl regularExpressionService;

    @Override
    public String processRequest(Request request){
        //0-eventId初始化,原本方法不变的前提下，语音默认为第一个event，所以计数从第二个开始,默认情况下只有一次event
        boolean isEnd = true;

        //1-提取prompt
        String prompt = messageService.extractPrompt(request);
        if (prompt == null) {
            return "{\"error\": \"Invalid request\"}";
        }

        //1.5-首先对prompt进行正则表达式匹配，匹配几次关键词就拼接几个结构化命令给模型
        prompt = regularExpressionService.systemCommandPrompt(prompt);

        //2-利用prompt拼装请求体，发送请求并接收模型返回
        String externalResponse = testHttpRequestService.sendRequest(prompt);

        //2.5-对模型返回结果进行正则匹配，提取结构化数据
        List<TempEvent> events = regularExpressionService.matchPrompt(externalResponse);

        Response response;
        //3-处理模型返回-得到语音
        if(events.isEmpty()){
            response = modelResponseService.ResponseBuild(request.getSessionId(), 1,"speech", externalResponse, isEnd);
        }else{
            response = modelResponseService.ResponseBuild(request.getSessionId(), 1,"speech", externalResponse, false);
            for (TempEvent event : events) {
                if (event.getEventType() == "navigation") {
                    response = modelResponseService.ResponseBuild(response, event.getEventType(),event.getInput(),event.getInput2(),event.GetIsEnd());
                    continue;
                }
                response = modelResponseService.ResponseBuild(response, event.getEventType(),event.getInput(),event.GetIsEnd());
            }
        }

        //4-格式化response
        String jsonResponse = modelResponseService.convertResponseToJson(response);

        return jsonResponse;
    }
}
