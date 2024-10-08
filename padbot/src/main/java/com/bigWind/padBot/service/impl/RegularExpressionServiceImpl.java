package com.bigWind.padBot.service.impl;

import com.bigWind.padBot.bean.response.TempEvent;
import com.bigWind.padBot.service.RegularExpressionService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
        String regexMeeting = "预定会议|订会议";
        String regexMoveForward = "前进|动一下|运动";
        String regexMoveBackward = "后退|退后";
        String regexNavigation = "导航|导航到|转到|去|去哪儿|去哪里|到哪儿|到哪里|去哪|到";

        // 编译正则表达式
        Pattern patternMeeting = Pattern.compile(regexMeeting);
        Pattern patternMoveForward = Pattern.compile(regexMoveForward);
        Pattern patternMoveBackward = Pattern.compile(regexMoveBackward);
        Pattern patternNavigation = Pattern.compile(regexNavigation);

        // 创建Matcher对象并匹配
        Matcher matcherMeeting = patternMeeting.matcher(input);
        Matcher matcherMoveForward = patternMoveForward.matcher(input);
        Matcher matcherMoveBackward = patternMoveBackward.matcher(input);
        Matcher matcherNavigation = patternNavigation.matcher(input);

        // 根据匹配结果创建TempEvent对象
        if (matcherMeeting.find()) {
            events.add(new TempEvent("webpage","http://222.200.184.32:8088/conference_room/ordinary/reserveRoomRobot",isEnd));
        }
        if (matcherMoveForward.find()) {
            events.add(new TempEvent("motion","1.0",isEnd));
        }
        if (matcherMoveBackward.find()) {
            events.add(new TempEvent("motion","-1.0",isEnd));
        }
        if (matcherNavigation.find()) {
            String regexDestinationMeetingRoom = "会议室|会客室";
            String regexDestinationOffice = "办公室|办公区";

            Pattern patternDestinationMeetingRoom = Pattern.compile(regexDestinationMeetingRoom);
            Pattern patternDestinationOffice = Pattern.compile(regexDestinationOffice);

            Matcher matcherDestinationMeetingRoom = patternDestinationMeetingRoom.matcher(input);
            Matcher matcherDestinationOffice = patternDestinationOffice.matcher(input);

            // 检查目的地是会议室还是办公室
            if (matcherDestinationMeetingRoom.find()) {
                events.add(new TempEvent("navigation", "NavigationId1","PointId", isEnd));
            } else if (matcherDestinationOffice.find()) {
                events.add(new TempEvent("navigation", "NavigationId2", "PointId",isEnd));
            }
        }
        // 如果列表不为空，则将最后一个元素的isEnd属性设置为true
        if (!events.isEmpty()) {
            events.get(events.size() - 1).setIsEnd(true);
        }
        return events;

    }

    @Override
    public String systemCommandPrompt(String input) {
        // 定义正则表达式
        String regexMeeting = "预定会议|订会议";
        String regexMoveForward = "前进|动一下|运动";
        String regexMoveBackward = "后退|退后";
        String regexNavigation = "导航|导航到|转到|去|去哪儿|去哪里|到哪儿|到哪里|去哪|到";

        // 编译正则表达式
        Pattern patternMeeting = Pattern.compile(regexMeeting);
        Pattern patternMoveForward = Pattern.compile(regexMoveForward);
        Pattern patternMoveBackward = Pattern.compile(regexMoveBackward);
        Pattern patternNavigation = Pattern.compile(regexNavigation);

        // 创建Matcher对象并匹配
        Matcher matcherMeeting = patternMeeting.matcher(input);
        Matcher matcherMoveForward = patternMoveForward.matcher(input);
        Matcher matcherMoveBackward = patternMoveBackward.matcher(input);
        Matcher matcherNavigation = patternNavigation.matcher(input);

        // 根据匹配结果创建TempEvent对象
        if (matcherMeeting.find()) {
            // 使用File类读取资源文件
            File file = new File("src/main/resources/static/sysPrompt/conference.txt");

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    input += line + System.lineSeparator();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return input;
    }
}
