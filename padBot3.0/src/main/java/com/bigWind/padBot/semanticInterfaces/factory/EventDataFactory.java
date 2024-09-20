/*2024-8-1
* jl
* 工厂模式，根据输入的eventType和输入的字符串形成对应的参数*/
package com.bigWind.padBot.semanticInterfaces.factory;

import com.bigWind.padBot.semanticInterfaces.bean.response.*;
import com.bigWind.padBot.semanticInterfaces.bean.response.*;

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
            case "navigation":
                return new NavigationData(input);
            case "launcher":
                return new LauncherData(input);
            default:
                throw new IllegalArgumentException("Unsupported eventType: " + eventType);
        }
    }

    public EventData createEventData(String eventType, String input1, String input2) {
        switch (eventType) {
            case "speech":
                return new SpeechData(input1) ;
            case "webpage":
                return new WebPageData(input1) ;
            case "motion":
                double distance = Float.parseFloat(input1);
                return new MotionData(distance);
            case "navigation":
                return new NavigationData(input1,input2);
            case "forEnd":
                return new NavigationData(input1,input2);
            default:
                throw new IllegalArgumentException("Unsupported eventType: " + eventType);
        }
    }
}
