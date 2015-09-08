package com.example.transportmk.transportmk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.transportmk.transportmk.adapters.StationAdapter;
import com.example.transportmk.transportmk.model.Station;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;


public class FormFragment extends Fragment {

    AutoCompleteTextView tbFrom;
    AutoCompleteTextView tbTo;
    Spinner spTransportType;
    List<Station> mList;
    StationAdapter adapter;

    public FormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_form, container, false);

        mList = new Select().from(Station.class).queryList();
        tbFrom = (AutoCompleteTextView) rootView.findViewById(R.id.tbFrom);
        tbTo = (AutoCompleteTextView) rootView.findViewById(R.id.tbTo);
        spTransportType = (Spinner) rootView.findViewById(R.id.transportType);
        adapter = new StationAdapter(getActivity(), R.layout.fragment_form, R.id.lbl_stationName, mList);
        tbFrom.setAdapter(adapter);
        tbTo.setAdapter(adapter);

        Button button = (Button) rootView.findViewById(R.id.btnGetLine);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchDataService.startActionFetchLine(getActivity(),
                        tbFrom.getText().toString(),
                        tbTo.getText().toString(),
                        spTransportType.getSelectedItem().toString());
            }
        });


        return rootView;
    }
}
