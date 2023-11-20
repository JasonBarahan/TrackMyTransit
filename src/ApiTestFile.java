import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;

import java.io.IOException;


public class ApiTestFile {
    private static final String PARTIAL_API_URL = "OpenDataAPI/api/V1";

    public static void getStopInformation() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("api.openmetrolinx.com")
                .addPathSegment(PARTIAL_API_URL)
                .addPathSegment("Stop")
                .addPathSegment("Details")
                .addPathSegment("UN") //getting station information for UN, which denotes Union Station
                .addQueryParameter("key", System.getenv("API_KEY"))
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

    public static void main(String[] args) {
        getStopInformation();
    }
}
