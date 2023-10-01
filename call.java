
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
                .uri(new URI("https://api.translink.ca/RTTIAPI/V1/stops/55612?apiKey=" + API_TOKEN))
                .header("Authorization", API_TOKEN)
                .header("Content-Type", "application/json")
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject responseBody = new JSONObject(response.body());
            System.out.println(responseBody);
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}

