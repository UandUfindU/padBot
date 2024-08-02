package com.bigWind.padBot.service;

import com.bigWind.padBot.bean.response.TempEvent;

import java.util.List;

public interface RegularExpressionService {
    List<TempEvent> matchPrompt(String input);
}
