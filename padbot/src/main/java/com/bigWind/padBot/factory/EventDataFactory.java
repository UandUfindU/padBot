/*2024-8-1
* jl
* 工厂模式，根据输入的eventType和输入的字符串形成对应的参数*/
package com.bigWind.padBot.factory;

import com.bigWind.padBot.bean.response.EventData;
import com.bigWind.padBot.bean.response.MotionData;
import com.bigWind.padBot.bean.response.SpeechData;
import com.bigWind.padBot.bean.response.WebPageData;

import static java.lang.String.valueOf;

public class EventDataFactory {
    public EventData createEventData(String eventType, String input) {
        switch (eventType) {
            case "speech":
                return new SpeechData(input) ;
            case "webpage":
                return new WebPageData(input) ;
            case "motion":
                double distance = Float.parseFloat(input);
                return new MotionData(distance);
            default:
                throw new IllegalArgumentException("Unsupported eventType: " + eventType);
        }
    }
}
