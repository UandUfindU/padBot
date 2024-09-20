package com.bigWind.padBot.semanticInterfaces.service;

import com.bigWind.padBot.semanticInterfaces.bean.Request;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

@Service
public interface TransformService {
    public String processRequest(Request request);

    String processRequestStream(Request request);

    void processRequestStream(Request request, ServletOutputStream outputStream) throws IOException;
}
