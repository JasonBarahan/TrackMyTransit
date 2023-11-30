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

public class GOStationCoordinatesApiClass implements TrainApiInterface {
    private final String PARTIAL_API_URL = "OpenDataAPI/api/V1";
    public GOStationCoordinatesApiClass () {
    }
    public List<String> retrieveStationCoordinates(String stationId){
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
            Response response = client.newCall(request).execute();
            String trainInfoJsonData = response.body().string();
            JSONObject metaDataJsonObj = new JSONObject(trainInfoJsonData); // This is where the MetaData is located at

            JSONObject stopJsonObj = metaDataJsonObj.getJSONObject("Stop");
            List<String> coordinatesList = new ArrayList<>();
            JSONObject longitude = stopJsonObj.getJSONObject("Longitude");
            JSONObject latitude = stopJsonObj.getJSONObject("Latitude");
            coordinatesList.add(longitude.getString("Longitude"));
            coordinatesList.add(latitude.getString("Latitude"));

            return coordinatesList;

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

    }
}
