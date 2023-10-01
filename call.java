
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONException;
public class call {
    private static final String API_TOKEN = System.getenv("API_TOKEN");
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://api.translink.ca/RTTIAPI/V1/stops/55612?apiKey=" + API_TOKEN))
                .header("Authorization", API_TOKEN)
                .header("Content-Type", "application/json")
                .build();
//        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
            JSONObject responseBody = new JSONObject(response.body());

            if (responseBody.getInt("responseCode") == 200) {
                // TODO: we get 301 as responseCode instead of 200, need to figure out why
                // TODO: still need to figure out what to write if the response code is 200.
                // uri might be incorrect because I have no idea what to write for this body
                // Probably have to write more headers/other stuff for the request
//                JSONObject stops = responseBody.getJSONObject("stops");
                System.out.println(response);}
            else {throw new RuntimeException(responseBody.getString("message"));}
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
// TODO: IOException is never used.

