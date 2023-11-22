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

import static data_access.API.TrainApiInterface.API_KEY;

public class GOTrainApiClass {
    private final String PARTIAL_API_URL = "OpenDataAPI/api/V1";
    public GOTrainApiClass () {
    }
    public List<List<String>> retrieveTrainInfo(){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("api.openmetrolinx.com")
                .addPathSegment(PARTIAL_API_URL)
                .addPathSegment("ServiceataGlance")
                .addPathSegment("Trains")
                .addPathSegment("All")
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

            JSONObject tripsJsonObj = headerPositionJsonObj.getJSONObject("Trips");
            JSONArray tripJsonArray = tripsJsonObj.getJSONArray("Trip");

            List<List<String>> trainList = new ArrayList<>();
            for (int i = 0; i < tripJsonArray.length(); i++) {
                JSONObject currtripEntry = tripJsonArray.getJSONObject(i);
                List<String> trainInfoList = new ArrayList<>();
                trainInfoList.add(currtripEntry.getString("TripNumber")); // "TripNumber" is a String for Train Number
                trainInfoList.add(currtripEntry.getString("LineCode")); // second item of trainInfoList is its Parent Line Code
                trainInfoList.add(currtripEntry.getString("Display")); // 3rd item of trainInfoList is Train Name
                trainInfoList.add(currtripEntry.getString("DelaySeconds")); //4th item is train delay (in seconds)

                trainList.add(trainInfoList); //add trainInfoList in trainList

                System.out.println(currtripEntry.getString("trainInfoList")); // For debugging purposes

                System.out.println(httpUrl);
            }
            return trainList; // do not print this yet, wait for full change

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

    }
}
