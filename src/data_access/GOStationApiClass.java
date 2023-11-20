package data_access;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import java.io.IOException;
public class GOStationApiClass implements ApiClassInterface{

    private final String PARTIAL_API_URL;
    private final String API_KEY;

    public GOStationApiClass (String partialApiUrl, String apiKey) {
        //private static final String PARTIAL_API_URL = "OpenDataAPI/api/V1"; // denotes "common portion" of API url across all possible calls
        //private static final String API_KEY = System.getenv("API_KEY");
        this.PARTIAL_API_URL = partialApiUrl;
        this.API_KEY = apiKey;
    }

    public void retrieveStationAmenities(String stationId){
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
            System.out.println(response.body().string());

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

    }
}
