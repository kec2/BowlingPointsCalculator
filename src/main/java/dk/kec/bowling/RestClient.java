package dk.kec.bowling;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import com.google.gson.Gson;

import dk.kec.bowling.model.Result;
import dk.kec.bowling.model.ScoreCard;
import dk.kec.bowling.model.Status;

/**
 *
 * This class is used to communicate with the the SKAT rest API for bowling
 * points. UTF-8 is used for encoding/decoding the communication.
 * <p>
 * There is an optional System property named DEBUG (boolean) that if true
 * prints extra information. Default value is false.
 * <p>
 *
 * Usage:<br>
 *
 * <pre>
 * ScoreCard = RestClient.getPoints();
 * \/\/ do some calculations on the points.
 * Status = RestClient.validatePoints(Result);
 * </pre>
 */
public class RestClient {
    private static final String ENTRY_POINT = "http://13.74.31.101/api/points";
    private static boolean debug = Boolean.getBoolean("DEBUG");

    /**
     * Don't let anyone instantiate this class.
     */
    private RestClient() {
    }

    /**
     * Get a set of points for a bowling match.
     *
     * @return A score card that contains the points and a token. This token is need
     *         in order to verify the calculated points
     * @throws IOException
     *         if the response status code is different from 200 OK
     * @throws InterruptedException
     *         if the HTTP send operation is interrupted
     */
    public static ScoreCard getPoints() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ENTRY_POINT))
                .version(Version.HTTP_2)
                .timeout(Duration.ofMinutes(1))
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (debug) {
            printResponse(response);
        }

        if (response.statusCode() != 200) {
            throw new IOException("Error happend while retriving points\nStatus code: " + response.statusCode()
                    + "\nReason: " + response.body());
        }

        return new Gson().fromJson(response.body(), ScoreCard.class);
    }

    /**
     * Verify the calculated bowling points.
     *
     * @param result
     *        the result of the calculated bowling points
     * @return the status for the validation
     * @throws IOException
     *         if the response status code is different from 200 OK
     * @throws InterruptedException
     *         if the HTTP send operation is interrupted
     */
    public static Status validatePoints(Result result) throws IOException, InterruptedException {
        String json = new Gson().toJson(result);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ENTRY_POINT))
                .version(Version.HTTP_2)
                .header("Content-Type", "application/json")
                .timeout(Duration.ofMinutes(1))
                .POST(BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (debug) {
            printResponse(response);
        }

        if (response.statusCode() != 200) {
            throw new IOException("Error happend while validating points\nStatus code: " + response.statusCode()
                    + "\nReason: " + response.body());
        }

        return new Gson().fromJson(response.body(), Status.class);
    }

    private static void printResponse(HttpResponse<String> response) {
        System.out.println("\nResponse header:");
        response.headers().map().forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });
        System.out.println("\nResponse body:");
        System.out.println(response.body());
        System.out.println();
    }
}
