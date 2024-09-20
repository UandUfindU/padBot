package com.bigWind.padBot.semanticInterfaces.bean.response;

public class TempEvent {
    String eventType;
    String input;

    String input2;
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

    public boolean GetIsEnd() {
        return isEnd;
    }

    public void setIsEnd(boolean end) {
        this.isEnd = end;
    }

    public String getInput2() {
        return input2;
    }

    public void setInput2(String input2) {
        this.input2 = input2;
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

    public TempEvent(String eventType, String input, String input2,boolean isEnd) {
        this.eventType = eventType;
        this.input = input;
        this.isEnd = isEnd;
        this.input2 = input2;
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
