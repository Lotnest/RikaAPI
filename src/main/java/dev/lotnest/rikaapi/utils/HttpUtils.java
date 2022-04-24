package dev.lotnest.rikaapi.utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.web.util.UriUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HttpUtils {

    public static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    public static final String SERVER_ERROR_OCCURRED = "Wystąpił bład serwera, spróbuj ponownie później.";

    private HttpUtils() {
    }

    public static @NotNull String getJsonValue(@NotNull String url, @NotNull String jsonKey) {
        try {
            return getJsonBody(url).thenApply(responseBody -> getJsonValueFromBody(responseBody, jsonKey))
                    .get(10L, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            return SERVER_ERROR_OCCURRED;
        }
    }

    public static @NotNull String getJsonValueFromBody(@NotNull String responseBody, @NotNull String jsonKey) {
        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject.getString(jsonKey);
    }

    public static @NotNull CompletableFuture<String> getJsonBody(@NotNull String url) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        return HTTP_CLIENT.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }

    public static CompletableFuture<String> postAndGetJsonValue(@NotNull String url, @NotNull String jsonKey) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(UriUtils.encodeQuery(url, StandardCharsets.UTF_8)))
                .header("Accept", "application/json")
                .build();


        return HTTP_CLIENT.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(responseBody -> getJsonValueFromBody(responseBody, jsonKey));
    }
}
