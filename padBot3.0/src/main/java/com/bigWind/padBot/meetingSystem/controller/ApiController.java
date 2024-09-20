package com.bigWind.padBot.meetingSystem.controller;

import com.bigWind.padBot.meetingSystem.Handler.NotificationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Autowired
    private NotificationHandler notificationHandler;

    @GetMapping("/api/web1")
    public String openBaidu() throws Exception {
        notificationHandler.sendMessageToAll("openWebPage:https://www.baidu.com");
        return "Message sent to open Baidu";
    }

    @GetMapping("/api/web2")
    public String openJD() throws Exception {
        notificationHandler.sendMessageToAll("openWebPage:https://www.jd.com");
        return "Message sent to open JD";
    }
}

