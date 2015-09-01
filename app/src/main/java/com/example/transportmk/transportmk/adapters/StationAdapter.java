package com.example.transportmk.transportmk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.transportmk.transportmk.R;
import com.example.transportmk.transportmk.model.Station;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kosta on 01-Sep-15.
 */
public class StationAdapter extends ArrayAdapter<Station> {
    Context context;
    int resource, textViewResourceId;
    List<Station> items, tempItems, suggestions;

    public StationAdapter(Context context, int resource, int textViewResourceId, List<Station> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<Station>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_station, parent, false);
        }
        Station stations = items.get(position);
        if (stations != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_stationName);
            if (lblName != null)
                lblName.setText(stations.getStationName());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((Station) resultValue).getStationName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Station s : tempItems) {
                    if (s.getStationName_en().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(s);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Station> filterList = (ArrayList<Station>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Station s : filterList) {
                    add(s);
                    notifyDataSetChanged();
                }
            }
        }

    };
}
