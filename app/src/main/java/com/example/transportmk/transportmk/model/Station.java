package com.example.transportmk.transportmk.model;

/**
 * Created by Kosta on 31-Aug-15.
 */
public class Station {
    private long id;
    private String stationName;
    private double stationLatitude;
    private double stationLongitude;
    private String stationCity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public double getStationLatitude() {
        return stationLatitude;
    }

    public void setStationLatitude(double stationLatitude) {
        this.stationLatitude = stationLatitude;
    }

    public double getStationLongitude() {
        return stationLongitude;
    }

    public void setStationLongitude(double stationLongitude) {
        this.stationLongitude = stationLongitude;
    }

    public String getStationCity() {
        return stationCity;
    }

    public void setStationCity(String stationCity) {
        this.stationCity = stationCity;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Station{");
        sb.append("id=").append(id);
        sb.append(", stationName='").append(stationName).append('\'');
        sb.append(", stationLatitude='").append(stationLatitude).append('\'');
        sb.append(", stationLongitude='").append(stationLongitude).append('\'');
        sb.append(", stationCity='").append(stationCity).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
