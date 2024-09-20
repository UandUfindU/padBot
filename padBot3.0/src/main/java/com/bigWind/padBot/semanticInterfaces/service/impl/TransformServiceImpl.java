package com.bigWind.padBot.semanticInterfaces.service.impl;

/*2024-8-1
 * j
 *封装所有服务类，形成工作流，从request到response Json格式字符串*/

import com.bigWind.padBot.semanticInterfaces.bean.Request;
import com.bigWind.padBot.semanticInterfaces.bean.Response;
import com.bigWind.padBot.semanticInterfaces.bean.response.TempEvent;
import com.bigWind.padBot.semanticInterfaces.service.MessageService;
import com.bigWind.padBot.semanticInterfaces.service.ModelResponseService;
import com.bigWind.padBot.semanticInterfaces.service.TestHttpRequestService;
import com.bigWind.padBot.semanticInterfaces.service.TransformService;
import com.bigWind.padBot.semanticInterfaces.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

    private boolean meetingContinues = false;

    @Override
    public String processRequest(Request request){
        //0-eventId初始化,原本方法不变的前提下，语音默认为第一个event，所以计数从第二个开始,默认情况下只有一次event
        boolean isEnd = true;

        //1-提取prompt
        String prompt = messageService.extractPrompt(request);
        if (prompt == null) {
            return "{\"error\": \"Invalid request\"}";
        }

        //正式代码块，与后下test相互互斥，等待语轩模型端
        JSONObject intents = JSONObject.parseObject(testHttpRequestService.recongnize(prompt));
        System.out.println("intents:"+intents);
        String intent = intents.getString("type");
        String content = intents.getString("response");

        

        List<TempEvent> events = new ArrayList<>();
        Response response = new Response();



        String externalResponse = "";
        if (intent.equals("C")){
            events = regularExpressionService.matchWeb(content);
            externalResponse = testHttpRequestService.sendRequest(prompt);
        }
        else if (intent.equals("A")){
            System.out.println(content);
            events = regularExpressionService.matchNavigation(content);
            if (events.size() == 0) {
                externalResponse = "抱歉，没有找到地点信息，请重新输入";
            } else {
            externalResponse = "好的，正在为您导航";
            }
        }
        else if (intent.equals("B")){
            events = regularExpressionService.matchWeb(content);
            externalResponse = "正在为您打开预定会议系统";
        }
        else if (intent.equals("D")){
            intent = "C";
            externalResponse = testHttpRequestService.sendNormalRequest(prompt);
        }
        //告诉模型哪些任务已经被执行
//        for (TempEvent event : events) {
//            prompt = prompt + "### system: " + event.getEventType() +"已被识别并执行###";
//        }
        //String externalResponse = testHttpRequestService.sendRequest(prompt);


      


        //3-处理模型返回-得到语音
        if(events.isEmpty()){
            response = modelResponseService.ResponseBuild(request.getSessionId(), 1,"speech", externalResponse, isEnd);
        }else{
            response = modelResponseService.ResponseBuild(request.getSessionId() ,1,"speech", externalResponse, false);
            for (TempEvent event : events) {
                if (event.getEventType() == "navigation") {
                    response = modelResponseService.ResponseBuild(response, event.getEventType(),event.getInput(),event.getInput2(),event.GetIsEnd());
                    continue;
                }
                response = modelResponseService.ResponseBuild(response, event.getEventType(),event.getInput(),event.GetIsEnd());
            }
        }

        response.setType(intent);

        //4-格式化response
        String jsonResponse = modelResponseService.convertResponseToJson(response);

        return jsonResponse;
    }

    @Override
    public String processRequestStream(Request request){
        //0-eventId初始化,原本方法不变的前提下，语音默认为第一个event，所以计数从第二个开始,默认情况下只有一次event
        boolean isEnd = true;

        //1-提取prompt
        String prompt = messageService.extractPrompt(request);
        if (prompt == null) {
            return "{\"error\": \"Invalid request\"}";
        }

        //1.5-首先对prompt进行正则表达式匹配，匹配几次关键词就生成几个除了第一个event的event，在增强循环中得到一个eventList,并用“\n system：\n”的形式拼装在prompt后，告诉模型任务已经执行
        List<TempEvent> events = regularExpressionService.matchPrompt(prompt);

        //告诉模型哪些任务已经被执行
        for (TempEvent event : events) {
            prompt = prompt + "### system: " + event.getEventType() +"已被识别并执行###";
        }

        //2-利用prompt拼装请求体，发送请求并接收模型返回
        String externalResponse = testHttpRequestService.sendRequest(prompt);

        Response response;
        //3-处理模型返回-得到语音
        if(events.isEmpty()){
            response = modelResponseService.ResponseBuild(request.getSessionId(), 1,"speech", externalResponse, isEnd);
        }else{
            response = modelResponseService.ResponseBuild(request.getSessionId() ,1,"speech", externalResponse, false);
            for (TempEvent event : events) {
                if (event.getEventType() == "navigation") {
                    response = modelResponseService.ResponseBuild(response, event.getEventType(),event.getInput(),event.getInput2(),event.GetIsEnd());
                    continue;
                }
                response = modelResponseService.ResponseBuild(response, event.getEventType(),event.getInput(),event.GetIsEnd());
            }
        }

        //4-格式化response
        String jsonResponse = modelResponseService.streamResponseBuild(response);

        return jsonResponse;
    }

    @Override
    public void processRequestStream(Request request, ServletOutputStream outputStream) throws IOException {
        boolean isEnd = true;

        String prompt = messageService.extractPrompt(request);
        if (prompt == null) {
            outputStream.write("{\"error\": \"Invalid request\"}".getBytes());
            outputStream.flush();
            return;
        }

        JSONObject intents = JSONObject.parseObject(testHttpRequestService.recongnize(prompt));
        String intent = intents.getString("type");
        String content = intents.getString("response");

        List<TempEvent> events = new ArrayList<>();
        if (intent.equals("C")){
            events = regularExpressionService.matchWeb(content);
        }

        //List<TempEvent> events = regularExpressionService.matchPrompt(prompt);

        //for (TempEvent event : events) {
         //   prompt = prompt + "### system: " + event.getEventType() + "已被识别并执行###";
        //}

        String externalResponse = testHttpRequestService.sendRequest(prompt);

        Response response;
        if (events.isEmpty()) {
            response = modelResponseService.ResponseBuild(request.getSessionId(), 1, "speech", externalResponse, isEnd);
        } else {
            response = modelResponseService.ResponseBuild(request.getSessionId(), 1, "speech", externalResponse, false);
            for (TempEvent event : events) {
                if (event.getEventType().equals("navigation")) {
                    response = modelResponseService.ResponseBuild(response, event.getEventType(), event.getInput(), event.getInput2(), event.GetIsEnd());
                    continue;
                }
                response = modelResponseService.ResponseBuild(response, event.getEventType(), event.getInput(), event.GetIsEnd());
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = modelResponseService.streamResponseBuild(response).replace("\\\"", "");
        //String jsonResponse = objectMapper.writeValueAsString();
        //for test
        //jsonResponse ="data:{\"sessionId\":\"18eeb673-935f-446d-a479-15ba674eba2a\",\"event\":{\"eventId\":\"04fb5867-5c19-43c8-b775-983a460e1369\",\"delay\":0,\"data\":{\"content\":\"你好呀，很高兴见到你\"},\"eventType\":\"speech\",\"isEnd\":true},\"status\":0}data:{\"status\":0,\"sessionId\":null,\"event\":{\"eventId\":3,\"preEventId\":2,\"eventType\":\"navigation\",\"data\":{\"pathId\":\"forEnd\",\"count\":1,\"pointId\":\"forEnd\"},\"isEnd\":true}}" +"\n"
//        + "data:{\"status\":0,\"sessionId\":null,\"event\":{\"eventId\":2,\"preEventId\":1,\"eventType\":\"navigation\",\"data\":{\"pathId\":\"forEnd\",\"count\":1,\"pointId\":\"forEnd\"},\"isEnd\":true}}";

        //for test
        System.out.println(jsonResponse);

        outputStream.write(jsonResponse.getBytes());
        outputStream.flush();
    }



}






