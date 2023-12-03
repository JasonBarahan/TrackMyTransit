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
import java.util.Objects;

import static data_access.API.StationApiInterface.API_KEY;

public class GOVehicleApiClass implements TrainApiInterface{
    private final String PARTIAL_API_URL = "OpenDataAPI/api/V1";
    public GOVehicleApiClass() {
    }
    public <T> T retrieveVehicleInfo(String stationId){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("api.openmetrolinx.com")
                .addPathSegment(PARTIAL_API_URL)
                .addPathSegment("Stop")
                .addPathSegment("NextService")
                .addPathSegment(stationId)
                .addQueryParameter("key", API_KEY)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .addHeader("content-type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String trainInfoJsonData = response.body().string();
            JSONObject headerPositionJsonObj = new JSONObject(trainInfoJsonData); // This is where the header is located at


            JSONObject metaDataJsonObj = headerPositionJsonObj.getJSONObject("Metadata");
            String errorCode = (String) metaDataJsonObj.get("ErrorCode");
            if (Objects.equals(errorCode, "200")) {
                JSONObject stationServiceJsonObj = headerPositionJsonObj.getJSONObject("NextService");
                JSONArray vehiclesJsonArray = stationServiceJsonObj.getJSONArray("Lines");

                List<List<String>> vehiclesList = new ArrayList<>();
                for (int i = 0; i < vehiclesJsonArray.length(); i++) {
                    JSONObject currtripEntry = vehiclesJsonArray.getJSONObject(i);
                    List<String> vehiclesInfoList = new ArrayList<>();
                    vehiclesInfoList.add(currtripEntry.getString("LineCode")); // parent line id
                    vehiclesInfoList.add(currtripEntry.getString("LineName")); // line full name
                    vehiclesInfoList.add(currtripEntry.getString("ServiceType"));
                    // indicates if vehicle is a train or a bus. Train is "T", Bus is "B"
                    vehiclesInfoList.add(currtripEntry.getString("DirectionName"));
                    // Vehicle direction (Departure - Destination)
                    vehiclesInfoList.add(currtripEntry.getString("ScheduledDepartureTime"));
                    // vehicle scheduled departure time from this station
                    vehiclesInfoList.add(currtripEntry.getString("ComputedDepartureTime"));
                    // vehicle actual departure time from this station
                    vehiclesInfoList.add(currtripEntry.getString("TripNumber"));
                    // We could calculate delay based on Computed Departure time and Scheduled departure time of a Certain Vehicle
                    vehiclesInfoList.add(String.valueOf(currtripEntry.get("Latitude"))); // vehicle latitude
                    vehiclesInfoList.add(String.valueOf(currtripEntry.get("Longitude"))); // vehicle longitude

                    vehiclesList.add(vehiclesInfoList); //add trainInfoList in trainList

                    System.out.println(vehiclesInfoList); // For debugging purposes

                    System.out.println(httpUrl);
                }
                return (T) vehiclesList; // do not print this yet, wait for full change
            }
            else {
                String errorMsg = (String) metaDataJsonObj.get("ErrorMessage");
                return (T) errorMsg;
            }

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
