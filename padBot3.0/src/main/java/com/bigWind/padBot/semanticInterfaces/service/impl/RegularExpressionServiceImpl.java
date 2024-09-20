package com.bigWind.padBot.semanticInterfaces.service.impl;

import com.bigWind.padBot.semanticInterfaces.bean.response.TempEvent;
import com.bigWind.padBot.semanticInterfaces.service.RegularExpressionService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.io.FileInputStream;


@Service
public class RegularExpressionServiceImpl implements RegularExpressionService {
    @Override
    public List<TempEvent> matchPrompt(String input) {
        List<TempEvent> events = new ArrayList<>();
        boolean isEnd = false;

        // 定义正则表达式
        String regexMeeting = "预定会议|订会议|订.*会议";
        String regexMoveForward = "前进|动一下|运动";
        String regexMoveBackward = "后退|退后";
        String regexNavigation = "导航|导航到|转到|去|去哪儿|去哪里|到哪儿|到哪里|去哪|到";
        String regexAppLaunch = "打开|启动|打开.*应用|启动.*应用|打开.*软件|启动.*软件|打开.*游戏|启动.*游戏|打开.*软件|启动.*软件|打开.*浏览器|启动.*浏览器|打开.*浏览器|打开.*浏览器|启动.*浏览器|打开.*浏览器|启动.*浏览器";

        // 编译正则表达式
        Pattern patternMeeting = Pattern.compile(regexMeeting);
        Pattern patternMoveForward = Pattern.compile(regexMoveForward);
        Pattern patternMoveBackward = Pattern.compile(regexMoveBackward);
        Pattern patternNavigation = Pattern.compile(regexNavigation);
        Pattern patternAppLaunch = Pattern.compile(regexAppLaunch);

        // 创建Matcher对象并匹配
        Matcher matcherMeeting = patternMeeting.matcher(input);
        Matcher matcherMoveForward = patternMoveForward.matcher(input);
        Matcher matcherMoveBackward = patternMoveBackward.matcher(input);
        Matcher matcherNavigation = patternNavigation.matcher(input);
        Matcher matcherAppLaunch = patternAppLaunch.matcher(input);

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
        if (matcherNavigation.find()) {
        //将文档信息转换成映射hashMap
            JSONObject jsonObject = null;
		try {
	    	String jsonFilePath = "/home/isdb14/home/padbot/padBot-main/padBot2.3/data.txt";
	    	jsonObject = new JSONObject(readFile(jsonFilePath));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		if (jsonObject != null) {
		    //提取targetPointList并生成地点和ID的映射
		    Map<String,String> locationIdMap = new HashMap<>();
		    JSONArray mapList = jsonObject.getJSONObject("data").getJSONArray("mapList");
		    for (int i =0; i<mapList.length(); i++){
			JSONObject map = mapList.getJSONObject(i);
			JSONArray targetPointList = map.getJSONArray("targetPointList");
			for(int j =0;j<targetPointList.length();j++){
				JSONObject point = targetPointList.getJSONObject(j);
				String name = point.getString("name");
				String id = point.getString("id");

				locationIdMap.put(name,id);
				}
		    TempEvent matchedEvent = null;
		    for(Map.Entry<String,String> entry :locationIdMap.entrySet()){
			String location = entry.getKey();
			String id = entry.getValue();
                         Pattern pattern = Pattern.compile(Pattern.quote(location), Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(input);
                    if (matcher.find() && matchedEvent == null) {
                        matchedEvent = new TempEvent("navigation", "", id, isEnd);
                        events.add(matchedEvent);
                        return events;
			}		
			}
			}
   	     }}
	//用于读取文件内容
        //events.add(new TempEvent("forEnd", "forEnd", "forEnd",true));
        // 如果列表不为空，则将最后一个元素的isEnd属性设置为true
        if (matcherAppLaunch.find()){
            String commitRegexAppLaunch = "人脸识别|测试|会议系统";
            Pattern patternCommitAppLaunch = Pattern.compile(commitRegexAppLaunch);
            Matcher matcherCommitAppLaunch = patternCommitAppLaunch.matcher(input);
            if (matcherCommitAppLaunch.find()){
            events.add(new TempEvent("launcher","com.ai.face.verifyPub",isEnd));
            }

            String googleRegexAppLaunch = "谷歌|google";
            Pattern patternGoogleAppLaunch = Pattern.compile(googleRegexAppLaunch);
            Matcher matcherGoogleAppLaunch = patternGoogleAppLaunch.matcher(input);
            if (matcherGoogleAppLaunch.find()){
            events.add(new TempEvent("launcher","com.android.chrome",isEnd));}

            String yanChengRegexAppLaunch = "盐城";
            Pattern patternYanChengAppLaunch = Pattern.compile(yanChengRegexAppLaunch);
            Matcher matcherYanChengAppLaunch = patternYanChengAppLaunch.matcher(input);
            if (matcherYanChengAppLaunch.find()){
            events.add(new TempEvent("launcher","com.example.thefirstpage",isEnd));}
        }
        if (!events.isEmpty()) {
            events.get(events.size() - 1).setIsEnd(true);
        }
        return events;
    }

    @Override
    // 新建的matchWeb方法，用于从Excel文件中匹配关键词并返回网页事件
    public List<TempEvent> matchWeb(String input) {
        List<TempEvent> webEvents = new ArrayList<>();
        String excelFilePath = "src/main/resources/static/webPage.xlsx";
        boolean isEnd = false;

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell keywordCell = row.getCell(0);
                Cell urlCell = row.getCell(1);

                if (keywordCell != null && urlCell != null) {
                    String keyword = keywordCell.getStringCellValue();
                    String url = urlCell.getStringCellValue();

                    // 判断关键词是否完全匹配
                    if (Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(input).matches()) {
                        webEvents.add(new TempEvent("webpage", url, isEnd));
                        break; // 找到匹配后跳出循环
                    }
                }
            }
            if (!webEvents.isEmpty()) {
                webEvents.get(webEvents.size() - 1).setIsEnd(true);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return webEvents;
    }

    @Override
    // 新建的matchWeb方法，用于从Excel文件中匹配关键词并返回网页事件
    public List<TempEvent> matchNavigation(String input) {
        List<TempEvent> navigationEvents = new ArrayList<>();
        String excelFilePath = "src/main/resources/static/navigation.xlsx";
        boolean isEnd = false;

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

//            Sheet sheet = workbook.getSheetAt(0);
//            for (Row row : sheet) {
//                Cell keywordCell = row.getCell(0);
//                Cell idCell = row.getCell(1);
//
//                if (keywordCell != null && idCell != null) {
//                    String keyword = keywordCell.getStringCellValue();
//                    String id = idCell.getStringCellValue();
//
//                    // 判断关键词是否完全匹配
//                    if (Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(input).matches()) {
//                        navigationEvents.add(new TempEvent("navigation", "", id, isEnd));
//                        break; // 找到匹配后跳出循环
//                    }
//                }
//            }
            navigationEvents.add(new TempEvent("navigation", "", input, isEnd));
            if (!navigationEvents.isEmpty()) {
                navigationEvents.get(navigationEvents.size() - 1).setIsEnd(true);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return navigationEvents;
    }

public static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (FileReader reader = new FileReader(filePath)) {
            int i;
            while ((i = reader.read()) != -1) {
                content.append((char) i);
            }
        }catch (IOException e) {
	     e.printStackTrace();
    }
        return content.toString();
    }
    @Override
    public boolean isMeeting(String input) {
        String regexMeeting = "预定会议|订会议|订.*会议";
        Pattern patternMeeting = Pattern.compile(regexMeeting);
        Matcher matcherMeeting = patternMeeting.matcher(input);
        return matcherMeeting.find();
    }
}
