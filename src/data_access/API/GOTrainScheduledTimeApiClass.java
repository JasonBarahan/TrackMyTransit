package data_access.API;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static data_access.API.TrainApiInterface.API_KEY;
import static java.time.LocalTime.now;

public class GOTrainScheduledTimeApiClass {
    private final String PARTIAL_API_URL = "OpenDataAPI/api/V1";
    public GOTrainScheduledTimeApiClass () {
    }
    public HashMap<String, String> retrieveTrainScheduledTime(String tripNumber){

        String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf =
                new SimpleDateFormat(DATE_FORMAT);
        Calendar c1 = Calendar.getInstance(); // today
        System.out.println("Today is " + sdf.format(c1.getTime())); // for test purpose

    OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("api.openmetrolinx.com")
                .addPathSegment(PARTIAL_API_URL)
                .addPathSegment("Schedule")
                .addPathSegment("Trip")
                .addPathSegment(sdf.format(c1.getTime())) //date
                .addPathSegment(tripNumber) //tripNumber for a certain trip(train)
                .addQueryParameter("key", API_KEY)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .addHeader("content-type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String trainScheduledTimeJsonData = response.body().string();
            JSONObject trainScheduledTimeJsonObj = new JSONObject(trainScheduledTimeJsonData); // This is where the header is located at
            JSONArray tripsJsonArray =  trainScheduledTimeJsonObj.getJSONArray("Trips");
            JSONArray stopsJsonArray = tripsJsonArray.getJSONArray(6);

            HashMap<String, String> trainscheduledtimeList = new HashMap<>();
            // Key is Station_id in String format, Value is Departure Time in String format
            for (int i = 0; i < stopsJsonArray.length(); i++) {
                JSONObject currstopEntry = stopsJsonArray.getJSONObject(i);
                JSONObject departuretimeObj = currstopEntry.getJSONObject("DepartureTime");
                trainscheduledtimeList.put(currstopEntry.getString("Code"), departuretimeObj.getString("Scheduled"));

                System.out.println(currstopEntry.getString("Code")); // For debugging purposes
                System.out.println(departuretimeObj.getString("Scheduled")); // For debugging purposes
                System.out.println(httpUrl);
            }
            return trainscheduledtimeList; // do not print this yet, wait for full change

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

    }

}
