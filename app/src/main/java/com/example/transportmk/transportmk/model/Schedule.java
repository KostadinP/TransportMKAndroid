package com.example.transportmk.transportmk.model;

import java.io.Serializable;

/**
 * Created by Kosta on 01-Sep-15.
 */
public class Schedule implements Serializable {

    private long id;

    private String vehicleType;

    private String transporter;

    private String regularityType;

    private String departureTime;

    private String arrivalTime;

    private int price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getTransporter() {
        return transporter;
    }

    public void setTransporter(String transporter) {
        this.transporter = transporter;
    }

    public String getRegularityType() {
        return regularityType;
    }

    public void setRegularityType(String regularityType) {
        this.regularityType = regularityType;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
