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

public class GOTrainsApiClass implements TrainApiInterface {

    private final String PARTIAL_API_URL = "OpenDataAPI/api/V1";
    public GOTrainsApiClass () {
    }

    /**
     * Searches for all trains and their information including ID and position.
     *
     * @param stationId ID of station for incoming trains
     * @return      API information
     */
    public List<String> retrieveTrains(String stationId){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("api.openmetrolinx.com")
                .addPathSegment(PARTIAL_API_URL)
                .addPathSegment("ServiceataGlance")
                .addPathSegment("Trains")
                .addPathSegment("All")
                // TODO: may need for future
                // .addPathSegment(stationId) //getting station information for the station with the id denoted by stationId
                .addQueryParameter("key", API_KEY)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .addHeader("content-type", "application/json")
                .build();

        /**
         * Search for information
         */
        try {
            Response response = client.newCall(request).execute();
            String trainJsonData = response.body().string();
            JSONObject metaDataJsonObj = new JSONObject(trainJsonData); // This is where the Metadata is located at
            JSONObject tripsJsonDataObj = metaDataJsonObj.getJSONObject("Trips");
            JSONArray tripJsonArray = tripsJsonDataObj.getJSONArray("Trip");
            List<String> tripList = new ArrayList<String>();
            for (int i = 0; i < tripJsonArray.length(); i++) {
                JSONObject currTripEntry = tripJsonArray.getJSONObject(i);
                tripList.add(currTripEntry.getString("id")); // left at "id" for now, to be changed to vehicle
                //System.out.println(currAmenitiesEntry.getString("Description")); // For debugging purposes
                System.out.println(httpUrl);
            }
            return tripList; // do not print this yet, wait for full change

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

    }
}
