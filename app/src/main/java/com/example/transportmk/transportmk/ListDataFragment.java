package com.example.transportmk.transportmk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.transportmk.transportmk.adapters.ScheduleAdapter;
import com.example.transportmk.transportmk.model.Schedule;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Kosta on 30-Aug-15.
 */
public class ListDataFragment extends Fragment {

    public ScheduleAdapter mAdapter;

    public ListDataFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_data, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Intent i = getActivity().getIntent();
        if (i != null && i.hasExtra(Intent.EXTRA_TEXT)) {
            Object[] schedule = (Object[]) i.getSerializableExtra(Intent.EXTRA_TEXT);

            ArrayList<Schedule> list = new ArrayList<>();
            for (Object sc : schedule) {
                list.add((Schedule) sc);
            }

            Collections.sort(list, new ScheduleTimeComparator());

            mAdapter = new ScheduleAdapter(getActivity(), list);
            ListView listView = (ListView) rootView.findViewById(R.id.lv_data);
            listView.setAdapter(mAdapter);
        }

        return rootView;
    }
}
