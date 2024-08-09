package com.bigWind.padBot.service.impl;

/*2024-8-1
* jl
* 向模型端发送请求，为保证可扩展性，参数为url与请求体中需要代换的question字符串发送请求后接收模型回复，并输出字符串*/

import com.bigWind.padBot.service.HttpRequestService;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;

@Service
public class HttpRequestServiceImpl implements HttpRequestService {
    @Override
    public String sendPostRequest(String url,String requestBody){
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            // 发送POST请求
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            // 读取响应
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

        }
}
