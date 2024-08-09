package com.bigWind.padBot.service;

import com.bigWind.padBot.bean.Request;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

@Service
public interface TransformService {
    public String processRequest(Request request);

    String processRequestStream(Request request);

    void processRequestStream(Request request, ServletOutputStream outputStream) throws IOException;
}
