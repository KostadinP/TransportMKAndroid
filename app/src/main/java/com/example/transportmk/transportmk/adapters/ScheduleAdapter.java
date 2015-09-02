package com.example.transportmk.transportmk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.transportmk.transportmk.R;
import com.example.transportmk.transportmk.model.Schedule;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Kosta on 02-Sep-15.
 */
public class ScheduleAdapter extends ArrayAdapter<Schedule> {

    // View lookup cache
    private static class ViewHolder {
        ImageView imageView;
        TextView depatureTime;
        TextView arrivalTime;
        TextView regularityType;
    }


    public ScheduleAdapter(Context context, ArrayList<Schedule> schedules) {
        super(context, R.layout.list_item, schedules);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Schedule schedule = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.list_image);
            viewHolder.depatureTime = (TextView) convertView.findViewById(R.id.departureTime);
            viewHolder.arrivalTime = (TextView) convertView.findViewById(R.id.arrivalTime);
            viewHolder.regularityType = (TextView) convertView.findViewById(R.id.regularityType);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        String vehicleType = schedule.getVehicleType();
        if (vehicleType.equals("BUS")) {
            Picasso.with(getContext())
                    .load(R.drawable.bus_e31508_50)
                    .into(viewHolder.imageView);
        } else {
            Picasso.with(getContext())
                    .load(R.drawable.train_1c144d_50)
                    .into(viewHolder.imageView);
        }
        viewHolder.depatureTime.setText(schedule.getDepartureTime());
        viewHolder.arrivalTime.setText(schedule.getArrivalTime());
        viewHolder.regularityType.setText(schedule.getRegularityType());
        // Return the completed view to render on screen
        return convertView;
    }
}
