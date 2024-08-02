package com.bigWind.padBot.service;

import com.bigWind.padBot.bean.Response;
import org.springframework.stereotype.Service;

@Service
public interface ModelResponseService {
    //这里暂时先将输入作为简单的eventType进行处理，如果后续eventType是列表，可以考虑用在添加一个新方法，或利用正则表达式进行处理
    public Response ResponseBuild(String sessionId,String eventType,String input);
    public String convertResponseToJson(Response response);
}
