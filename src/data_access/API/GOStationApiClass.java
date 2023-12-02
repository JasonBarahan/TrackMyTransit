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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GOStationApiClass implements TrainApiInterface {

    private final String PARTIAL_API_URL = "OpenDataAPI/api/V1";

    private final String API_METADATA_SUCCESS_MESSAGE = "OK";

    public GOStationApiClass () {
    }

    @Override
    public String getApiMetadataSuccessMessage() {
        return API_METADATA_SUCCESS_MESSAGE;
    }

    // Return Type: Map<String, List<Object>>
    // Key of Map: Represents the MetaData error code.
    // Note: The text next to the : denotes the "message", see further comments for details
        // Code 200: OK [Successful API Call]
        // Code 503: Unavailable [Server hosting the GO API goes down]
        // Code 401: Unauthorized [Invalid API used, either due to incorrect key input or key is deactivated]
        // Code 204: No Content [GO Transit removes/renames a station code, but changes not reflected in text file]
    //Value: A list containing Objects. The list specifically contains [String, JSONObject]
        // List[0]: Contains a String. This string would be the "message" based on what error code you have
            // Eg: If the error code is 200, then the "message" would be "OK".
        // List[1]: Contains the full stop JSON object, contains the information from the stop/details endpoints.

    @Override
    public Map<String, List<Object>> stationAmenitiesCallResult(String stationId) {
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
            JSONObject fullStopJsonObj = new JSONObject(fullStopJsonData);
            JSONObject metadataObj = fullStopJsonObj.getJSONObject("Metadata");
            String metadataErrorCode = metadataObj.getString("ErrorCode");
            String metadataErrorMessage = metadataObj.getString("ErrorMessage");

            callCodeMessageAndData.add(metadataErrorMessage);
            callCodeMessageAndData.add(fullStopJsonObj);
            callCodeAndData.put(metadataErrorCode, callCodeMessageAndData);
            return callCodeAndData;

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public List<String> retrieveStationAmenities(String stationId){
        Map<String, List<Object>> retrieveCallResult = stationAmenitiesCallResult(stationId);
        JSONObject retrievedStopJsonDataObj = (JSONObject) retrieveCallResult.get("200").get(1);
        JSONObject stopJsonDataObj = retrievedStopJsonDataObj.getJSONObject("Stop");
        JSONArray amenitiesJsonArray = stopJsonDataObj.getJSONArray("Facilities");
        List<String> amenitiesList = new ArrayList<String>();
        for (int i = 0; i < amenitiesJsonArray.length(); i++) {
            JSONObject currAmenitiesEntry = amenitiesJsonArray.getJSONObject(i);
            amenitiesList.add(currAmenitiesEntry.getString("Description"));
        }
        return amenitiesList;
    }

    //TODO: This was the ORIGINAL retrieveStationAmenities method prior to changes to handle error API calls
    /*public List<String> retrieveStationAmenities2(String stationId){
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

    }*/
}
