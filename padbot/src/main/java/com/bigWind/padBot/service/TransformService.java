package com.bigWind.padBot.service;

import com.bigWind.padBot.bean.Request;
import org.springframework.stereotype.Service;

@Service
public interface TransformService {
    public String processRequest(Request request);
}
