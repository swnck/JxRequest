package de.swnck;

import de.swnck.config.RequestConfiguration;
import de.swnck.consumer.ResponseConsumer;
import de.swnck.frame.type.BodyFrame;
import de.swnck.frame.type.HeaderFrame;
import de.swnck.frame.type.ResponseFrame;
import de.swnck.util.ContentType;
import de.swnck.util.StatusCode;
import lombok.Getter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@Getter
public class JxRequest {
    private final RequestConfiguration configuration;

    public JxRequest(RequestConfiguration configuration) {
        this.configuration = configuration;
    }

    public void get(HeaderFrame headers, ResponseConsumer responseConsumer, String url) {
        performRequest("GET", headers, new BodyFrame(), responseConsumer, url);
    }

    public void post(HeaderFrame headers, BodyFrame bodyFrame, ResponseConsumer responseConsumer, String url) {
        performRequest("POST", headers, bodyFrame, responseConsumer, url);
    }

    private void performRequest(String method, HeaderFrame headers, BodyFrame bodyFrame, ResponseConsumer responseConsumer, String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .method(method, HttpRequest.BodyPublishers.ofString(bodyFrame.getBodyAsJson()));

            if (!headers.hasHeader("Content-Type")) {
                requestBuilder.header("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
            }

            headers.getHeaders().forEach((key, value) -> {
                requestBuilder.header(key, value.toString());
            });

            HttpRequest request = requestBuilder.build();
            CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            response.thenAccept(httpResponse -> {
                ResponseFrame responseFrame = new ResponseFrame(StatusCode.fromCode(httpResponse.statusCode()), httpResponse.body());
                responseConsumer.accept(responseFrame);
            }).join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
