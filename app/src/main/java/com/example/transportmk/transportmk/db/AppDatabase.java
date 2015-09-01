package com.example.transportmk.transportmk.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Kosta on 31-Aug-15.
 */
@Database(name = AppDatabase.DB_NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    public static final String DB_NAME = "app_database";
    public static final int VERSION = 2;
}
