package com.bigWind.padBot.bean.response;

public class TempEvent {
    String eventType;
    String input;
    boolean isEnd;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
    public TempEvent(String eventType, String input, boolean isEnd) {
        this.eventType = eventType;
        this.input = input;
        this.isEnd = isEnd;
    }

    @Override
    public String toString() {
        return "\n system: (\n{" +
                "eventType='" + eventType + '\'' +
                ", input='" + input + '\'' +
                ", 已经被成功执行" +
                '}';
    }
}
