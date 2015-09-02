package com.example.transportmk.transportmk;

import com.example.transportmk.transportmk.model.Schedule;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Kosta on 02-Sep-15.
 */
public class ScheduleTimeComparator implements Comparator<Schedule>, Serializable {
    @Override
    public int compare(Schedule lhs, Schedule rhs) {
        return lhs.getDepartureTime().compareTo(rhs.getDepartureTime());
    }
}
