package com.bigWind.padBot.bean;

import com.bigWind.padBot.bean.response.Data;

public class Response {
    public int status;
    public String sessionId;
    public Data data;

    //Getter and Setter
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
