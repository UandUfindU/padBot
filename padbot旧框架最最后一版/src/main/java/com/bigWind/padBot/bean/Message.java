package com.bigWind.padBot.bean;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Message {
    private String prompt;

    // 无参构造函数
    public Message() {}

    // 使用 JSON 字符串构造函数
    public Message(String jsonString) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        this.prompt = jsonNode.get("prompt").asText();
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

}
