package com.bigWind.padBot.bean.response;

public class SpeechData extends EventData{
    public String content;
    public String voice;
    public int speed;

    //构造函数，处理模型返回结果并将其进行标准化，暂时的处理是将模型返回结果直接放进content里面进行处理
    public SpeechData(String input){
        this.content=input;
        this.voice="卡通";
        this.speed=6;
    }

    @Override
    public String toString(){
        return "{\"content\": \"" + content + "\", \"voice\": \"" + voice + "\", \"speed\": " + speed + "}";
    }
    //Getter and Setter

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
