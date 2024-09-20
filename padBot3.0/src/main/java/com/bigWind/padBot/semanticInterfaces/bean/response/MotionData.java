package com.bigWind.padBot.semanticInterfaces.bean.response;

/*
* 每一个Data前面的名称就是对应的eventType
* */
public class MotionData extends EventData{
    public int type;

    public args args;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MotionData(double distance){
        this.type = 1001;
        this.args = new args(0.5, distance);
    }

}

class args{
    public double linearVel;
    public double distance;

    args(double linearVel, double distance){
        this.linearVel = linearVel;
        this.distance = distance;
    }
    public double getLinearVel() {
        return linearVel;
    }

    public void setLinearVel(double linearVel) {
        this.linearVel = linearVel;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
