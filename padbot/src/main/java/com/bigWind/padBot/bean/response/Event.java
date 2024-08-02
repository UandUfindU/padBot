package com.bigWind.padBot.bean.response;

public class Event {
    public int eventId;
    public int preEventId;//新加字段，用于记录上一个事件的id
    public String eventType;
    public EventData data;
    public boolean isEnd;
    public Event(int eventId,String eventType,EventData data,boolean isEnd){
        this.eventId=eventId;
        this.eventType=eventType;
        this.data=data;
        this.isEnd=isEnd;
    }
    public Event(int eventId,int preEventId,String eventType,EventData data,boolean isEnd){
        this.eventId=eventId;
        this.preEventId=preEventId;
        this.eventType=eventType;
        this.data=data;
        this.isEnd=isEnd;
    }
    public String toJSON() {
        // 将 Event 对象转换为 JSON 字符串的逻辑
        // 这里只是示例，实际情况需要根据具体需求进行实现
        return "{\"eventType\": \"" + eventType + "\", \"data\": " + data.toString() + "}";
    }

    //Getter and Setter
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public EventData getData() {
        return data;
    }

    public void setData(EventData data) {
        this.data = data;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean  end) {
        isEnd = end;
    }

    public int getPreEventId() {
        return preEventId;
    }

    public void setPreEventId(int preEventId) {
        this.preEventId = preEventId;
    }
}
