package com.bigWind.padBot.bean.response;

public class WebPageData extends EventData{
    public String path;
    public int duration;
    public int output;

    //构造函数，处理模型返回结果并将其进行标准化，暂时的处理是将模型返回结果直接放进content里面进行处理
    public WebPageData(String path,int duration,int output){
        this.path=path;
        this.duration=duration;
        this.output=output;
    }

    public WebPageData(String path,int output){
        this.path=path;
        this.duration= 60;
        this.output=output;
    }

    public WebPageData(String path){
        this.path=path;
        this.duration= 60;
        this.output=1;
    }

    /*
    *   - **path**: 网页路径。
        - **duration**: 展示时间，单位秒，缺省则一直展示。
        - **output**: 显示的设备，头部屏幕为0，胸前屏幕为1。
    * */


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "WebPageData{" +
                "path='" + path + '\'' +
                ", duration=" + duration +
                ", output=" + output +
                '}';
    }
}
