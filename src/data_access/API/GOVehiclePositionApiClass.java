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

public class GOVehiclePositionApiClass implements TrainApiInterface {

    private final String PARTIAL_API_URL = "OpenDataAPI/api/V1";
    public GOVehiclePositionApiClass () {
    }
    public List<String> retrieveVehiclePosition(String stationId){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("api.openmetrolinx.com")
                .addPathSegment(PARTIAL_API_URL)
                .addPathSegment("UP")
                .addPathSegment("Gtfs")
                .addPathSegment("Feed")
                .addPathSegment("VehiclePosition")
                .addPathSegment(stationId) //getting station information for the station with the id denoted by stationId
                .addQueryParameter("key", API_KEY)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .addHeader("content-type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String vehiclePositionJsonData = response.body().string();
            JSONObject vehiclePositionJsonObj = new JSONObject(vehiclePositionJsonData); // This is where the header is located at
            // JSONObject vehiclePositionJsonDataObj = vehiclePositionJsonObj.getJSONObject("entity");
            JSONArray entityJsonArray = vehiclePositionJsonObj.getJSONArray("entity");
            List<String> entityList = new ArrayList<String>();
            for (int i = 0; i < entityJsonArray.length(); i++) {
                JSONObject currEntityEntry = entityJsonArray.getJSONObject(i);
                entityList.add(currEntityEntry.getString("id")); // left at "id" for now, to be changed to vehicle
                //System.out.println(currAmenitiesEntry.getString("Description")); // For debugging purposes
                System.out.println(httpUrl);
            }
            return entityList; // do not print this yet, wait for full change

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

    }
}
