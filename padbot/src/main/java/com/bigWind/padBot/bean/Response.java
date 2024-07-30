package com.bigWind.padBot.bean;

import java.util.List;

public class Response {
    private int status;
    private String sessionId;
    private Data data;

    // Getters and Setters

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

class Data {
    private List<Event> events;

    // Getters and Setters

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}

class Event {
    private String eventId;
    private String eventType;
    private String preEventId;
    private int delay;
    private EventData data;

    // Getters and Setters

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getPreEventId() {
        return preEventId;
    }

    public void setPreEventId(String preEventId) {
        this.preEventId = preEventId;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public EventData getData() {
        return data;
    }

    public void setData(EventData data) {
        this.data = data;
    }
}

abstract class EventData {

}

class SpeechData extends EventData {
    private String content;
    private String voice;
    private int speed;

    // Getters and Setters

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

class ImageData extends EventData {
    private int source;
    private String path;
    private int duration;
    private int output;

    // Getters and Setters

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }
}

class VideoData extends EventData {
    private String path;
    private int output;

    // Getters and Setters

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }
}

class WebpageData extends EventData {
    private String path;
    private int duration;
    private int output;

    // Getters and Setters

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }
}

class NavigationData extends EventData {
    private String pathId;
    private int count;
    private String pointId;

    // Getters and Setters

    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }
}

class NetworkData extends EventData {
    private String type;
    private String url;
    private String host;
    private String port;
    private String data;
    private String dataType;

    // Getters and Setters

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}

class MotionData extends EventData {
    private String type;
    private MotionArgs args;

    // Getters and Setters

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MotionArgs getArgs() {
        return args;
    }

    public void setArgs(MotionArgs args) {
        this.args = args;
    }
}

class MotionArgs {
    private double linearVel;
    private double distance;

    // Getters and Setters

    public double getLinearVel() {
        return linearVel;
    }

    public void setLinearVel(double linearVel) {
        this.linearVel = linearVel;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}

class LauncherData extends EventData {
    private String packageName;

    // Getters and Setters

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}

class CollectionData extends EventData {
    private String collectionName;

    // Getters and Setters

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
}

