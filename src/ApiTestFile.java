import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;

import java.io.IOException;


public class ApiTestFile {
    private static final String API_URL = "https://api.translink.ca/rttiapi/v1/stops/55612?";

    public static void getStopInformation() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("api.translink.ca")
                .addPathSegment("rttiapi")
                .addPathSegment("v1")
                .addPathSegment("stops")
                .addPathSegment("55612?")
                .addQueryParameter("apikey", System.getenv("API_KEY"))
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
