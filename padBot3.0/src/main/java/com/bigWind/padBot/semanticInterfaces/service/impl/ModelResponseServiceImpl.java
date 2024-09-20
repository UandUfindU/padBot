package com.bigWind.padBot.semanticInterfaces.service.impl;

/*2024-8-1
*jl
*构建response并返回完整的JSON格式字符串*/

import com.bigWind.padBot.semanticInterfaces.bean.Response;
import com.bigWind.padBot.semanticInterfaces.bean.response.Data;
import com.bigWind.padBot.semanticInterfaces.bean.response.Event;
import com.bigWind.padBot.semanticInterfaces.bean.response.EventData;
import com.bigWind.padBot.semanticInterfaces.factory.EventDataFactory;
import com.bigWind.padBot.semanticInterfaces.service.ModelResponseService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelResponseServiceImpl implements ModelResponseService {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Response ResponseBuild(String sessionId, String eventType, String input) {
        Response response = new Response();
        response.status=0;
        response.setSessionId(sessionId);

        Data data = new Data();
        List<Event> events = new ArrayList<>();

        EventDataFactory factory=new EventDataFactory();
        EventData eventData = factory.createEventData(eventType,input);
        Event event = new Event(1,eventType,eventData,true);
        events.add(event);

        data.events = events;
        response.response = data;

        return response;
    }

    //流式返回结构
    @Override
    public String streamResponseBuild(Response response){
        StringBuilder result = new StringBuilder();

        for (Event event : response.getResponse().getEvents()) {
            // Create a new JSON object for the event
            ObjectNode eventNode = objectMapper.createObjectNode();
            eventNode.put("status", response.getStatus());
            eventNode.put("sessionId", response.getSessionId());

            // Add the event data
            JsonNode eventJsonNode = objectMapper.convertValue(event, JsonNode.class);
            eventNode.set("event", eventJsonNode);

            // Check preEventId and set it to null if it is 0
            if (event.getPreEventId() == 0) {
                ((ObjectNode) eventJsonNode).putNull("preEventId");
            }

            // Convert the event node to a JSON string
            String eventJson = eventNode.toString() + "\n";

            // Add "data:" prefix and append to the result
            result.append("data:").append(eventJson);
        }

        return result.toString();
    }



    @Override
    public String convertResponseToJson(Response response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            throw new RuntimeException("Error converting response to JSON", e);
        }
    }


    //下面这个方法包含所有event的字段
    @Override
    public Response ResponseBuild(String sessionId,int eventId,String eventType, String input,boolean isEnd) {
        Response response = new Response();
        response.status=0;
        response.setSessionId(sessionId);

        Data data = new Data();
        List<Event> events = new ArrayList<>();

        EventDataFactory factory=new EventDataFactory();
        EventData eventData = factory.createEventData(eventType,input);
        Event event;
        if (eventId==1){
            event = new Event(eventId,eventType,eventData,isEnd);
        }
        else{
            event = new Event(eventId,eventId-1,eventType,eventData,isEnd);
        }
        //重构逻辑，仅为1时事件直接添入，没有前置事件；其他情况置入前置事件序号
        events.add(event);
        data.events = events;
        response.response = data;

        return response;
    }

    //为了方便遍历，需要一个对已有response进行事件添加的方法，同时将String eventType, String input,boolean isEnd封装为一个bean（TempEvent）
    @Override
    public Response ResponseBuild(Response response,String eventType, String input,boolean isEnd) {
        Data data = new Data();
        List<Event> events = response.response.events;
        EventDataFactory factory=new EventDataFactory();
        EventData eventData = factory.createEventData(eventType,input);
        int eventId=response.response.events.size()+1;
        Event event;
        if (eventId==1){
            event = new Event(eventId,eventType,eventData,isEnd);
        }
        else{
            event = new Event(eventId,eventId-1,eventType,eventData,isEnd);
        }
        //重构逻辑，仅为1时事件直接添入，没有前置事件；其他情况置入前置事件序号
        events.add(event);
        data.events = events;
        response.response = data;
        return response;
    }

    @Override
    public Response ResponseBuild(Response response,String eventType, String input,String input2,boolean isEnd) {
        Data data = new Data();
        List<Event> events = response.response.events;
        EventDataFactory factory=new EventDataFactory();
        EventData eventData = factory.createEventData(eventType,input,input2);
        int eventId=response.response.events.size()+1;
        Event event;
        if (eventId==1){
            event = new Event(eventId,eventType,eventData,isEnd);
        }
        else{
            event = new Event(eventId,eventId-1,eventType,eventData,isEnd);
        }
        //重构逻辑，仅为1时事件直接添入，没有前置事件；其他情况置入前置事件序号
        events.add(event);
        data.events = events;
        response.response = data;
        return response;
    }
}
