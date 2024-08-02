package com.bigWind.padBot.bean;
/**
 * Created by yy on 2024/7/30.
 * 目的是模拟派宝发送的请求，相对简单。
 * 因为内部类的调用方法较为奇怪，因此这里将内部类转换为外部类
 */
public class Request {
    private String sessionId;
    private boolean newTopic;
    private String deviceId;
    private String deviceModel;
    private String city;
    private String district;
    private String lang;// 语言
    private Message message;

    // Getters and Setters

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isNewTopic() {
        return newTopic;
    }

    public void setNewTopic(boolean newTopic) {
        this.newTopic = newTopic;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}

