package com.example.transportmk.transportmk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.transportmk.transportmk.model.Schedule;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Kosta on 30-Aug-15.
 */
public class ListDataFragment extends Fragment {

    public ArrayAdapter<String> mAdapter;

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
            Schedule[] schedule = (Schedule[]) i.getSerializableExtra(Intent.EXTRA_TEXT);
            for (Schedule sc : schedule) {
                Log.v("TAG", sc.getId() + " " + sc.getDepartureTime());
            }
        }

        String[] lista = {"Stavka1", "Stavka2", "Stavka3", "Stavka4"};
        ArrayList<String> list = new ArrayList<>(Arrays.asList(lista));

        mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.tv_field, list);

        ListView listView = (ListView) rootView.findViewById(R.id.lv_data);
        listView.setAdapter(mAdapter);


        return rootView;
    }
}
