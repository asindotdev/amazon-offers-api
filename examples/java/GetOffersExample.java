// Offers Already Known – Java example (Java 15+)
// Usage: ASIN_API_KEY=your-key java GetOffersExample.java
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetOffersExample {
    static final HttpClient CLIENT = HttpClient.newHttpClient();
    static final String KEY = System.getenv("ASIN_API_KEY");

    static String enc(String v) {
        return URLEncoder.encode(v, StandardCharsets.UTF_8);
    }

    static HttpResponse<byte[]> call(String method, String url, String body) throws Exception {
        HttpRequest.Builder b = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", "Bearer " + KEY)
            .timeout(Duration.ofSeconds(120));
        b = body == null ? b.method(method, HttpRequest.BodyPublishers.noBody())
            : b.header("Content-Type", "application/json").method(method, HttpRequest.BodyPublishers.ofString(body));
        HttpResponse<byte[]> r = CLIENT.send(b.build(), HttpResponse.BodyHandlers.ofByteArray());
        if (r.statusCode() >= 400) {
            System.err.println("HTTP " + r.statusCode() + ": " + new String(r.body(), StandardCharsets.UTF_8));
            System.exit(1);
        }
        return r;
    }

    static String field(String json, String name) {
        Matcher m = Pattern.compile("\"" + name + "\"\\s*:\\s*\"([^\"]*)\"").matcher(json);
        return m.find() ? m.group(1) : "";
    }

    public static void main(String[] args) throws Exception {
        String query = "asin=" + enc("B09DFCB66S") + "&"
            + "marketplace=" + enc("com");
        HttpResponse<byte[]> response = call("GET", "https://www.asin.dev/api/v1/offers" + (query.isEmpty() ? "" : "?" + query), null);
        String text = new String(response.body(), StandardCharsets.UTF_8);
        System.out.println(text.length() > 2000 ? text.substring(0, 2000) : text);
    }
}
