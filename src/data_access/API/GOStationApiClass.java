package data_access.API;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GOStationApiClass implements TrainApiInterface {

    private final String PARTIAL_API_URL = "OpenDataAPI/api/V1";
    public GOStationApiClass () {
    }
    public List<String> retrieveStationAmenities(String stationId){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("api.openmetrolinx.com")
                .addPathSegment(PARTIAL_API_URL)
                .addPathSegment("Stop")
                .addPathSegment("Details")
                .addPathSegment(stationId) //getting station information for the station with the id denoted by stationId
                .addQueryParameter("key", API_KEY)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .addHeader("content-type", "application/json")
                .build();
        try {
            // TODO: Do we need additional error checking based on error codes in Metadata?
            Response response = client.newCall(request).execute();
            String fullStopJsonData = response.body().string();
            JSONObject fullStopJsonObj = new JSONObject(fullStopJsonData); // This is where the metadata is located at
            JSONObject stopJsonDataObj = fullStopJsonObj.getJSONObject("Stop");
            JSONArray amenitiesJsonArray = stopJsonDataObj.getJSONArray("Facilities");
            List<String> amenitiesList = new ArrayList<String>();
            for (int i = 0; i < amenitiesJsonArray.length(); i++) {
                JSONObject currAmenitiesEntry = amenitiesJsonArray.getJSONObject(i);
                amenitiesList.add(currAmenitiesEntry.getString("Description"));
                //System.out.println(currAmenitiesEntry.getString("Description")); // For debugging purposes
            }
            return amenitiesList;

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

    }
}
