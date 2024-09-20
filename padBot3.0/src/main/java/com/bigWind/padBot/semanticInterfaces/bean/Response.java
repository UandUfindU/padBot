package com.bigWind.padBot.semanticInterfaces.bean;

import com.bigWind.padBot.semanticInterfaces.bean.response.Data;

public class Response {
    public int status;
    public String sessionId;

    public Data response;

    public String type;


    //Getter and Setter


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Data getResponse() {
        return response;
    }

    public void setResponse(Data response) {
        this.response = response;
    }
}
