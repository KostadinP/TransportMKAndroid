package com.example.transportmk.transportmk;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Kosta on 31-Aug-15.
 */
public class DBFlowApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);

        FetchDataService.startActionFetchStations(this);
    }
}
