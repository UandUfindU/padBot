package com.bigWind.padBot.semanticInterfaces.bean.response;

public class NavigationData extends EventData{
    public String pathId;
    public int count;
    public String pointId;

    //构造函数，处理模型返回结果并将其进行标准化，暂时的处理是将模型返回结果直接放进content里面进行处理
    NavigationData(String pathId, int count, String pointId){
        this.pathId = pathId;
        this.count = count;
        this.pointId = pointId;
    }

    public NavigationData(String pathId, String pointId){
        this.pathId = pathId;
        this.count = 1;
        this.pointId = pointId;
    }

    public NavigationData(String pathId){
        this.pathId = pathId;
        this.count = 1;
        this.pointId = "默认点位";
    }
    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    @Override
    public String toString() {
        return "正在执行导航任务，任务信息{" +
                "pathId='" + pathId + '\'' +
                ", count=" + count +
                ", pointId=" + pointId +
                '}';
    }
}

