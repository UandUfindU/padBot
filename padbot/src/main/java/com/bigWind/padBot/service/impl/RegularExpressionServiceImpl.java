package com.bigWind.padBot.service.impl;

import com.bigWind.padBot.bean.response.TempEvent;
import com.bigWind.padBot.service.RegularExpressionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegularExpressionServiceImpl implements RegularExpressionService {
    @Override
    public List<TempEvent> matchPrompt(String input) {
        List<TempEvent> events = new ArrayList<>();
        boolean isEnd = false;

        // 定义正则表达式
        String regexMeeting = "预定会议|会议";
        String regexMoveForward = "前进|动一下|运动";
        String regexMoveBackward = "后退|退后";

        // 编译正则表达式
        Pattern patternMeeting = Pattern.compile(regexMeeting);
        Pattern patternMoveForward = Pattern.compile(regexMoveForward);
        Pattern patternMoveBackward = Pattern.compile(regexMoveBackward);

        // 创建Matcher对象并匹配
        Matcher matcherMeeting = patternMeeting.matcher(input);
        Matcher matcherMoveForward = patternMoveForward.matcher(input);
        Matcher matcherMoveBackward = patternMoveBackward.matcher(input);

        // 根据匹配结果创建TempEvent对象
        if (matcherMeeting.find()) {
            events.add(new TempEvent("webpage","http://222.200.184.32:8088/conference_room/ordinary/reserveRoomRobot",isEnd));
        }
        if (matcherMoveForward.find()) {
            events.add(new TempEvent("motion","1.0",isEnd));
        }
        if (matcherMoveBackward.find()) {
            events.add(new TempEvent("webpage","-1.0",isEnd));
        }
        // 如果列表不为空，则将最后一个元素的isEnd属性设置为true
        if (!events.isEmpty()) {
            events.get(events.size() - 1).setEnd(true);
        }
        return events;

    }
}
