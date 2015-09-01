package com.example.transportmk.transportmk.model;

/**
 * Created by Kosta on 01-Sep-15.
 */
public class Line {

    private long id;

    private String traveledTime;

    private String kmTraveled;

    private Schedule[] scheduleList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTraveledTime() {
        return traveledTime;
    }

    public void setTraveledTime(String traveledTime) {
        this.traveledTime = traveledTime;
    }

    public String getKmTraveled() {
        return kmTraveled;
    }

    public void setKmTraveled(String kmTraveled) {
        this.kmTraveled = kmTraveled;
    }

    public Schedule[] getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(Schedule[] scheduleList) {
        this.scheduleList = scheduleList;
    }
}
