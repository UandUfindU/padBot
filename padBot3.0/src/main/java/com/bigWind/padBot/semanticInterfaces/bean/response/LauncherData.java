package com.bigWind.padBot.semanticInterfaces.bean.response;

public class LauncherData extends EventData{
    public String packageName;
    public int output ;


    public LauncherData(String packageName) {
        this.packageName = packageName;
        this.output = 1;
    }

    //构造函数，处理模型返回结果并将其进行标准化，暂时的处理是将模型返回结果直接放进content里面进行处理

    /*
    *   - **packageName**: app路径，如"com.example.app"
    * */

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "LauncherData{" +
                "packageName='" + packageName + '\'' +
                '}';
    }
}
