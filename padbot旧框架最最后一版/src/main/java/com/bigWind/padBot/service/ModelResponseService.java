package com.bigWind.padBot.service;

import com.bigWind.padBot.bean.Response;
import org.springframework.stereotype.Service;

@Service
public interface ModelResponseService {
    //这里暂时先将输入作为简单的eventType进行处理，如果后续eventType是列表，可以考虑用在添加一个新方法，或利用正则表达式进行处理
    public Response ResponseBuild(String sessionId,String eventType,String input);

    //流式返回结构
    public String streamResponseBuild(Response response);

    public String convertResponseToJson(Response response);

    Response ResponseBuild(String sessionId, int eventId, String eventType, String input, boolean isEnd);

    //为了方便遍历，需要一个对已有response进行事件添加的方法
    Response ResponseBuild(Response response, String eventType, String input, boolean isEnd);

    Response ResponseBuild(Response response, String eventType, String input, String input2, boolean isEnd);
}
