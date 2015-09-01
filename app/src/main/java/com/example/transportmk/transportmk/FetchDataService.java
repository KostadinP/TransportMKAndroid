package com.example.transportmk.transportmk;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.transportmk.transportmk.model.Line;
import com.example.transportmk.transportmk.model.Station;
import com.example.transportmk.transportmk.model.Station$Table;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class FetchDataService extends IntentService {

    private static final String ACTION_FETCH_STATIONS = "com.example.transportmk.transportmk.action.FOO";
    private static final String ACTION_FETCH_LINE = "com.example.transportmk.transportmk.action.BAZ";

    private static final String EXTRA_FROMCITY = "com.example.transportmk.transportmk.extra.PARAM1";
    private static final String EXTRA_TOCITY = "com.example.transportmk.transportmk.extra.PARAM2";

    public final String LOG_TAG = this.getClass().getSimpleName();

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionFetchStations(Context context) {
        Intent intent = new Intent(context, FetchDataService.class);
        intent.setAction(ACTION_FETCH_STATIONS);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionFetchLine(Context context, String param1, String param2) {
        Intent intent = new Intent(context, FetchDataService.class);
        intent.setAction(ACTION_FETCH_LINE);
        intent.putExtra(EXTRA_FROMCITY, param1);
        intent.putExtra(EXTRA_TOCITY, param2);
        context.startService(intent);
    }

    public FetchDataService() {
        super("FetchDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_STATIONS.equals(action)) {
                handleActionFoo();
            } else if (ACTION_FETCH_LINE.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_FROMCITY);
                final String param2 = intent.getStringExtra(EXTRA_TOCITY);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo() {
        // These two need to be declared outside the try/catch
// so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

// Will contain the raw JSON response as a string.
        String jsonStr;

        try {
            // Construct the URL
            URL url = new URL("https://transport-mk.herokuapp.com/data/rest/stations");

            // Create the request and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return;
            }
            jsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            return;
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
        parseAndSaveJson(jsonStr);
    }

    private void parseAndSaveJson(String jsonStr) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        Station[] stations = gson.fromJson(jsonStr, Station[].class);
        for (Station s : stations) {
            if (s.exists()) {
                //Delete.table(Station.class);
                s.update();
            } else s.save();
        }
        //Log.v(LOG_TAG, new Select().from(Station.class).where().limit(10).queryList().toString());
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleActionBaz(String param1, String param2) {
        // These two need to be declared outside the try/catch
// so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

// Will contain the raw JSON response as a string.
        String jsonStr = null;

        try {
            // Construct the URL
            URL url = new URL("https://transport-mk.herokuapp.com/data/rest/lines/schedulesByStations");

            Station from = new Select().from(Station.class).where(
                    Condition.column(Station$Table.STATIONNAME).eq(param1)).querySingle();
            Station to = new Select().from(Station.class).where(
                    Condition.column(Station$Table.STATIONNAME).eq(param2)).querySingle();
            if (from == null || to == null) {
                return;
            }

            String urlParameters = "startStationId=" + from.getId() + "&endStationId=" + to.getId();
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
                return;
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
                return;
            }
            jsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            return;
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
        parseLineJson(jsonStr);
    }

    private void parseLineJson(String jsonStr) {
        Gson gson = new Gson();
        Line line = gson.fromJson(jsonStr, Line.class);

        Intent i = new Intent(this, ListActivity.class);
        i.putExtra(Intent.EXTRA_TEXT, line.getScheduleList());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
