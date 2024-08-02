/*2024-8-1
* jl
* 工厂模式，根据输入的eventType和输入的字符串形成对应的参数*/
package com.bigWind.padBot.factory;

import com.bigWind.padBot.bean.response.EventData;
import com.bigWind.padBot.bean.response.SpeechData;

public class EventDataFactory {
    public EventData createEventData(String eventType, String input) {
        switch (eventType) {
            case "speech":
                return new SpeechData(input) ;
            default:
                throw new IllegalArgumentException("Unsupported eventType: " + eventType);
        }
    }
}
