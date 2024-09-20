package com.bigWind.padBot.semanticInterfaces.bean.response;

import java.util.List;

public class Data {
    public List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
