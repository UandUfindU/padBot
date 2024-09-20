package com.bigWind.padBot.semanticInterfaces.service.impl;

/*2024-8-1
:jl
一个最简单的向模型发送请求的测试用例
基于"curl -X POST "http://192.168.1.122:8081/rag_query"-H "content-Type: application/json" -d '{"question": "请问你是谁?"}'”将传入的string替换到请求体中并发送请求，获得模型返回结果*/

import com.bigWind.padBot.semanticInterfaces.service.HttpRequestService;
import com.bigWind.padBot.semanticInterfaces.service.TestHttpRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class TestHttpRequestServiceImpl implements TestHttpRequestService {
    @Autowired
    private HttpRequestService httpRequestService;
    @Override
    public String sendRequest(String question) {
        //String apiUrl = determinApiUrl(question);
        String apiUrl = "http://192.168.1.122:8081/rag_query";

        // 构建请求体
        String requestBody = determinApiBody(question);

        // 发送请求并返回响应
        String response = httpRequestService.sendPostRequest(apiUrl, requestBody);
	return response.replace("\n","").replace("\\","");
    }
    @Override
    public String sendNormalRequest(String question) {
        //String apiUrl = determinApiUrl(question);
        String apiUrl = "http://192.168.1.122:8081/normal_query";

        // 构建请求体
        String requestBody = determinApiBody(question);

        // 发送请求并返回响应
        String response = httpRequestService.sendPostRequest(apiUrl, requestBody);
        return response.replace("\n","").replace("\\","");
    }
    @Override
    public String determinApiUrl(String question){
        //定义正则表达式
        Pattern meetingPattern = Pattern.compile("定会议|预定会议|预约会议|安排会议");
        Pattern navigationPattern = Pattern.compile("导航|路线|带我去");
        Pattern movementPattern = Pattern.compile("前进|后退");
	Pattern testPattern = Pattern.compile("会议系统");

        //匹配输入字符串
        Matcher meetingMatcher = meetingPattern.matcher(question);
        Matcher navigationMatcher = navigationPattern.matcher(question);
        Matcher movementMatcher = movementPattern.matcher(question);
	Matcher testMatcher = testPattern.matcher(question);

        if (meetingMatcher.find()){
            return  "http://192.168.1.122:8081/meeting";
        } else if (navigationMatcher.find()) {
            return  "http://192.168.1.122:8081/navigation";
        } else if (meetingMatcher.find()) {
            return  "http://192.168.1.122:8081/movement";
        }else if(testMatcher.find()){
	    return "http://222.200.184.74:8082/api/test";
	}else {
            return  "http://192.168.1.122:8081/rag_query";
        }
    }

    @Override
    public String determinApiBody(String question){
        //定义正则表达式
        Pattern meetingPattern = Pattern.compile("定会议|预定会议|预约会议|安排会议|开会");
        Pattern navigationPattern = Pattern.compile("导航|路线|带我去");
        Pattern movementPattern = Pattern.compile("前进|后退");

        //匹配输入字符串
        Matcher meetingMatcher = meetingPattern.matcher(question);
        Matcher navigationMatcher = navigationPattern.matcher(question);
        Matcher movementMatcher = movementPattern.matcher(question);

        if (meetingMatcher.find()){
            return  "{\"question\": \"" + question + "\",\"movement\":\"\",\"destination\":\"\"}";
        } else if (navigationMatcher.find()) {
            String destination = extractDestination(question);
            return  "{\"question\": \"为我导航去\",\"movement\":\"\",\"destination\":\"" + destination + "\"}";
        } else if (movementMatcher.find()) {
            String movement =extractMovement(question);
            return "{\"question\": \"请你按照我的指令做出动作\",\"movement\":\"" + movement + "\",\"destination\":\"\"}";
        }else {
            return  "{\"question\": \"" + question + "\",\"movement\":\"\",\"destination\":\"\"}";
        }

    }
    @Override
    public String extractDestination(String question){
        // 定义匹配地点的正则表达式（例如匹配“B202”或“会议室一”）
        Pattern locationPattern = Pattern.compile("B\\d{3}|会议室|A\\d{3}"); // 根据实际地点进行调整
        Matcher locationMatcher = locationPattern.matcher(question);

        if (locationMatcher.find()) {
            return locationMatcher.group(); // 获取匹配到的地点
        } else {
            return "";
        }
    }

    @Override
    public String extractMovement(String question){
        // 定义匹配动作的正则表达式（例如匹配“前进”或“后退”或者“向前”）
        Pattern locationPattern = Pattern.compile("前进|后退|向前|向后"); // 根据实际动作进行调整
        Matcher locationMatcher = locationPattern.matcher(question);

        if (locationMatcher.find()) {
            return locationMatcher.group(); // 获取匹配到的动作
        } else {
            return "";
        }
    }

    @Override
    public String recongnize(String question){
        String URL = "http://192.168.1.122:8083/recognition";
        String requestBody = "{\"question\": \"" + question + "\"}";
        String response = httpRequestService.sendPostRequest(URL, requestBody);
        return response.replace("\n","").replace("\\","");
    }
}
