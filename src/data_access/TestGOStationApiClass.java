/*package data_access;

import data_access.API.TrainApiInterface;
import entity.Station;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.util.Map;

public class TestGOStationApiClass implements TrainApiInterface {

    private static final String PARTIAL_API_URL = "OpenDataAPI/api/V1";
    public TestGOStationApiClass () {
    }

    public static Map<String, List<Object>> stationAmenitiesCallResult(String stationId) {
        Map<String, List<Object>> callCodeAndData = new HashMap<>();
        List<Object> callCodeMessageAndData = new ArrayList<>();
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
            String fullStopJsonData = response.body().string();
            JSONObject fullStopJsonObj = new JSONObject(fullStopJsonData); // This is where the metadata is located at
            JSONObject metadataObj = fullStopJsonObj.getJSONObject("Metadata");
            String metadataErrorCode = metadataObj.getString("ErrorCode");
            String metadataErrorMessage = metadataObj.getString("ErrorMessage");

            callCodeMessageAndData.add(metadataErrorCode);
            callCodeMessageAndData.add(fullStopJsonObj);
            callCodeAndData.put(metadataErrorCode, callCodeMessageAndData);
            return callCodeAndData;

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<String> retrieveStationAmenities2(String stationId) {
        //TODO: BIG ASSUMPTION - ASSUME THIS METHOD IS ONLY CALLED WITH A ERRORCODE OF 200 (IE: VALID API CALL)
        Map<String, List<Object>> retrieveCallResult = stationAmenitiesCallResult(stationId);
        JSONObject retrievedStopJsonDataObj = (JSONObject) retrieveCallResult.get("200").get(1);
        JSONArray amenitiesJsonArray = retrievedStopJsonDataObj.getJSONArray("Facilities");
        List<String> amenitiesList = new ArrayList<String>();
        for (int i = 0; i < amenitiesJsonArray.length(); i++) {
            JSONObject currAmenitiesEntry = amenitiesJsonArray.getJSONObject(i);
            amenitiesList.add(currAmenitiesEntry.getString("Description"));
            System.out.println(currAmenitiesEntry.getString("Description")); // For debugging purposes
        }
        return amenitiesList;

    }

    public static void main(String[] args) {

        //Case 0: Testing out stationAmenitiesCallMessage
        Map<String, String> callMessage1 = stationAmenitiesCallResult("UN");
        System.out.println(callMessage1.get("200"));

        //Case 1: Valid station code input
        List<String> stationAmenitiesList = retrieveStationAmenities("UN");
        System.out.println(stationAmenitiesList.get(0)); //For testing purposes

        //Case 1: Valid station code input
        List<String> stationAmenitiesList2 = retrieveStationAmenities("ABC");
        System.out.println(stationAmenitiesList2);
        //System.out.println(stationAmenitiesList2.get(0)); //For testing purposes

    }
}*/

/*public static List<String> retrieveStationAmenities2(String stationId){
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
            JSONObject metadataObj = fullStopJsonObj.getJSONObject("Metadata");
            String metadataErrorCode = metadataObj.getString("ErrorCode");
            String metadataErrorMessage = metadataObj.getString("ErrorMessage");
            if (metadataErrorCode.equals("200")) {
                JSONObject stopJsonDataObj = fullStopJsonObj.getJSONObject("Stop");
                JSONArray amenitiesJsonArray = stopJsonDataObj.getJSONArray("Facilities");
                List<String> amenitiesList = new ArrayList<String>();
                for (int i = 0; i < amenitiesJsonArray.length(); i++) {
                    JSONObject currAmenitiesEntry = amenitiesJsonArray.getJSONObject(i);
                    amenitiesList.add(currAmenitiesEntry.getString("Description"));
                    System.out.println(currAmenitiesEntry.getString("Description")); // For debugging purposes
                }
                return amenitiesList;
            } else {
                System.out.println("Invalid query");
                return null;
            }

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }*/