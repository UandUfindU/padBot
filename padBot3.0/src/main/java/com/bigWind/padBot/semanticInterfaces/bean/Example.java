/*
package com.bigWind.padBot.bean;

import java.util.Arrays;

public class Example {
    public static void main(String[] args) {
        System.out.println("Hello, world!");

        Request request = new Request();
        request.setSessionId("fa4f3b996724480eaa05ff8e0930b07b");
        request.setNewTopic(false);
        request.setDeviceId("PX0001");
        request.setDeviceModel("X1");
        request.setCity("广州市");
        request.setDistrict("黄埔区");
        request.setLang("zh_cn");

        Message message = new Message();
        message.setPrompt("你叫什么名字");
        request.setMessage(message);

        Response response = new Response();
        response.setStatus(0);
        response.setSessionId("fa4f3b996724480eaa05ff8e0930b07b");

        Data responseData = new Data();
        Event event1 = new Event();
        event1.setEventId("1");
        event1.setEventType("speech");
        event1.setPreEventId("");
        event1.setDelay(0);

        SpeechData speechData = new SpeechData();
        speechData.setContent("好的，下⾯为你播放⼀段视频");
        speechData.setVoice("卡通");
        speechData.setSpeed(6);
        event1.setData(speechData);

        Event event2 = new Event();
        event2.setEventId("2");
        event2.setEventType("image");
        event2.setPreEventId("1");
        event2.setDelay(0);

        ImageData imageData = new ImageData();
        imageData.setSource(2);
        imageData.setPath("http://xxx/xx.jpg");
        imageData.setDuration(3);
        imageData.setOutput(1);
        event2.setData(imageData);

        responseData.setEvents(Arrays.asList(event1, event2));
        response.setData(responseData);

    }
}
*/
