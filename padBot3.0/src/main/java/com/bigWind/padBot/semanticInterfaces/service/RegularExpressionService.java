package com.bigWind.padBot.semanticInterfaces.service;

import com.bigWind.padBot.semanticInterfaces.bean.response.TempEvent;

import java.util.List;

public interface RegularExpressionService {
    List<TempEvent> matchPrompt(String input);

    // 新建的matchWeb方法，用于从Excel文件中匹配关键词并返回网页事件
    List<TempEvent> matchWeb(String input);

    // 新建的matchWeb方法，用于从Excel文件中匹配关键词并返回网页事件
    List<TempEvent> matchNavigation(String input);

    boolean isMeeting(String input);
}
