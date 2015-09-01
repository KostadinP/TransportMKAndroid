package com.example.transportmk.transportmk.model;

import com.example.transportmk.transportmk.db.AppDatabase;
import com.google.gson.annotations.Expose;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Kosta on 31-Aug-15.
 */
@Table(databaseName = AppDatabase.DB_NAME)
public class Station extends BaseModel {

    @Expose
    @Column
    @PrimaryKey
    private long id;

    @Expose
    @Column
    private String stationName;

    @Expose
    @Column
    private String stationName_en;

    @Expose
    @Column
    private double stationLatitude;

    @Expose
    @Column
    private double stationLongitude;

    @Expose
    @Column
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

    public String getStationName_en() {
        return stationName_en;
    }

    public void setStationName_en(String stationName_en) {
        this.stationName_en = stationName_en;
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
        sb.append(", stationName_en='").append(stationName_en).append('\'');
        sb.append('}').append("\n");
        return sb.toString();
    }
}
