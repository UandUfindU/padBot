package com.bigWind.padBot.service.impl;

import com.bigWind.padBot.bean.Response;
import com.bigWind.padBot.bean.response.Data;
import com.bigWind.padBot.bean.response.Event;
import com.bigWind.padBot.bean.response.EventData;
import com.bigWind.padBot.factory.EventDataFactory;
import com.bigWind.padBot.service.ModelResponseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelResponseServiceImpl implements ModelResponseService {
    @Override
    public Response ResponseBuild(String sessionId,String eventType, String input) {
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
        response.data = data;

        return response;
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
}
