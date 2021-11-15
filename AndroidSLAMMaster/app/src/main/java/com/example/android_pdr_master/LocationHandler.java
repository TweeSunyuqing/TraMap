package com.example.android_pdr_master;

public class LocationHandler {
    /*x轴坐标*/
    private Double xAxis;
    /*y轴坐标*/
    private Double yAxis;
    public LocationHandler(Double xAxis, Double yAxis) {
        super();
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }
    public Double getxAxis() {
        return xAxis;
    }
    public void setxAxis(Double xAxis) {
        this.xAxis = xAxis;
    }
    public Double getyAxis() {
        return yAxis;
    }
    public void setyAxis(Double yAxis) {
        this.yAxis = yAxis;
    }
}


