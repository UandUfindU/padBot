//package com.bigWind.padBot.controller;
//
//import com.bigWind.padBot.bean.Message;
//import com.bigWind.padBot.bean.Request;
//import com.bigWind.padBot.service.TransformService;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//class TransformController {
//
//    @Autowired
//    private TransformService transformService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @PostMapping("/processRequest")
//    public String processRequest(@RequestBody String requestJson) {
//        try {
//            // 将 JSON 字符串解析为 JsonNode
//            JsonNode rootNode = objectMapper.readTree(requestJson);
//
//            // 读取除了 message 字段之外的其他字段
//            boolean newTopic = rootNode.get("newTopic").asBoolean();
//            String deviceId = rootNode.get("deviceId").asText();
//            String deviceModel = rootNode.get("deviceModel").asText();
//            String city = rootNode.get("city").asText();
//            String district = rootNode.get("district").asText();
//            String lang = rootNode.get("lang").asText();
//            String steam = rootNode.get("steam").asText();
//
//            // 读取 message 字段并解析为 Message 对象
//            String messageJsonString = rootNode.get("message").asText();
//            JsonNode messageJsonNode = objectMapper.readTree(messageJsonString);
//            Message message = objectMapper.treeToValue(messageJsonNode, Message.class);
//
//            // 创建 Request 对象
//            Request request = new Request();
//            request.setNewTopic(newTopic);
//            request.setDeviceId(deviceId);
//            request.setDeviceModel(deviceModel);
//            request.setCity(city);
//            request.setDistrict(district);
//            request.setLang(lang);
//            request.setSteam(steam);
//            request.setMessage(message);
//
//            // 打印请求到控制台
//            System.out.println(request.toString());
//
//            // 处理请求
//            return transformService.processRequest(request);
//        } catch (Exception e) {
//            // 打印异常到控制台
//            e.printStackTrace();
//            // 返回错误信息
//            return "Error: " + e.getMessage();
//        }
//    }
//}
package com.bigWind.padBot.controller;

import com.bigWind.padBot.bean.Request;
import com.bigWind.padBot.service.TransformService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

//import static com.bigWind.padBot.service.impl.ModelResponseServiceImpl.objectMapper;

@RestController
@RequestMapping("/api")
class TransformController {
    @Autowired
    private TransformService transformService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/processRequest_origin")
    public String processRequest(@RequestBody Request request) {
        String result = transformService.processRequest(request);
        System.out.println(result);
        return result;
    }

    @PostMapping("/processRequest_stream_origin")
    public String processRequestStream(@RequestBody Request request) {
        String result = transformService.processRequestStream(request);
        System.out.println(result);
        return result;
    }

    @PostMapping("/processRequest")
    @ResponseBody
    public void processRequestStream(@RequestBody Request request, HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            transformService.processRequestStream(request, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        //return "{ \"status\" : 0}";
    }
}