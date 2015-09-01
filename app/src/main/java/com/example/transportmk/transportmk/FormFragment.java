package com.example.transportmk.transportmk;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.transportmk.transportmk.adapters.StationAdapter;
import com.example.transportmk.transportmk.model.Line;
import com.example.transportmk.transportmk.model.Schedule;
import com.example.transportmk.transportmk.model.Station;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class FormFragment extends Fragment {

    AutoCompleteTextView tbFrom;
    AutoCompleteTextView tbTo;
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

        Button button = (Button) rootView.findViewById(R.id.btnGetLine);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("TAG", "do something on btn click");
                new FetchLineTask().execute();
            }
        });

        mList = new Select().from(Station.class).queryList();
        tbFrom = (AutoCompleteTextView) rootView.findViewById(R.id.tbFrom);
        tbTo = (AutoCompleteTextView) rootView.findViewById(R.id.tbTo);
        adapter = new StationAdapter(getActivity(), R.layout.fragment_form, R.id.lbl_stationName, mList);
        tbFrom.setAdapter(adapter);
        tbTo.setAdapter(adapter);

        return rootView;
    }

    public class FetchLineTask extends AsyncTask<Void, Void, Void> {

        public final String LOG_TAG = FetchLineTask.class.getSimpleName();

        @TargetApi(Build.VERSION_CODES.KITKAT)
        protected Void doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
// so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

// Will contain the raw JSON response as a string.
            String jsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url = new URL("https://transport-mk.herokuapp.com/data/rest/lines/schedulesByStations");

                String urlParameters = "startStationId=2&endStationId=12";
                byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                int postDataLength = postData.length;

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("charset", "utf-8");
                urlConnection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                urlConnection.setUseCaches(false);
                try (DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream())) {
                    wr.write(postData);
                }
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonStr = buffer.toString();
                Log.v(LOG_TAG, jsonStr);
                parseJson(jsonStr);


            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        private void parseJson(String jsonStr) {
            Gson gson = new Gson();
            Line line = gson.fromJson(jsonStr, Line.class);
            for (Schedule sc : line.getScheduleList()) {
                Log.v("TAG", sc.getId() + " " + sc.getDepartureTime());
            }
        }
    }
}
