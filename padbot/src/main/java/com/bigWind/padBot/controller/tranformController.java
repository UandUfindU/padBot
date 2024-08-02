package com.bigWind.padBot.controller;

import com.bigWind.padBot.bean.Request;
import com.bigWind.padBot.service.TransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
class tranformController {
    @Autowired
    private TransformService transformService;

    @PostMapping("/processRequest")
    public String processRequest(@RequestBody Request request) {
        return transformService.processRequest(request);
    }
}
