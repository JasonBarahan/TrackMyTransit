package api;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class GoStationAPI implements StationAPI {

    // URL header
    private static final String API_URL = "OpenDataAPI/api/V1";

    // API token
    private static final String API_KEY = System.getenv("API_KEY");


    @Override
    public List<String> getStationAmenities(String stationId) {
        // This creates an instance of okHttpClient
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        // This builds the URL
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("api.openmetrolinx.com")
                .addPathSegments(API_URL)

                // specific to station amenities call
                .addPathSegment("Stop")
                .addPathSegment("Details")
                .addPathSegment(stationId)
                .addQueryParameter("key", API_KEY)
                .build();


        // This handles the request creation
        Request request = new Request.Builder()
//                .url(String.format(API_URL + "/Stop/Details/%s?key=%s", stationId, API_KEY))
                .url(httpUrl)
                .build();

        try {
            // TODO: I left print statements in for debugging purposes. Remove when no longer needed.
            // execute api call
            Response response = client.newCall(request).execute();
            System.out.println(response);

            // This formulates the initial response body containing the response data and requested information.
            JSONObject responseBody = new JSONObject(response.body().string());
            System.out.println(responseBody);

            // Response data, used to check for failed calls.
            JSONObject metadata = responseBody.getJSONObject("Metadata");
            System.out.println(metadata);

            // No errors
            if (metadata.getInt("ErrorCode") == 200) {
                // gets data on station-specific details
                JSONObject stationData = responseBody.getJSONObject("Stop");
                System.out.println(stationData);

                // takes the amenities from station-specific details...
                JSONArray amenitiesJSON = stationData.getJSONArray("Facilities");
                System.out.println(amenitiesJSON);

                // ...and turns them into a List<String>
                List<String> amenities = new ArrayList<>();

                for (int i = 0; i < amenitiesJSON.length(); i++) {
                    amenities.add(i, amenitiesJSON.getJSONObject(i).getString("Description"));
                }

                System.out.println(Arrays.toString(amenities.toArray()));
                return amenities;

            } else {
                // Error encountered when making a call; outputs response code and message
                throw new RuntimeException(metadata.getString("ErrorCode") + " - " + metadata.getString("ErrorMessage"));
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getIncomingVehicles(String stationId) {
        // TODO: Implement me
        return null;
    }



//    public static void main(String[] args) {
//        // TODO: Test call. remove main when done
//        GoStationAPI api = new GoStationAPI();
//        System.out.println(api.getStationAmenities("UN"));
//    }
}
