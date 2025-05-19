package io.github.swnck;

import io.github.swnck.request.AbstractBody;
import io.github.swnck.request.AbstractRequest;
import io.github.swnck.util.ContentType;
import io.github.swnck.util.StatusCode;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Represents an HTTP response retrieved using an asynchronous HTTP client.
 * This class is designed to process a given request, initiate the corresponding HTTP call,
 * and handle the response data, including body, status code, headers, and timing.
 */
@Getter
@Setter
public class JxResponse {
    private static final HttpClient CLIENT = HttpClient.newBuilder().build();
    private static final Logger LOGGER = LoggerFactory.getLogger(JxResponse.class);

    private String body;
    private String contentType;
    private String uri;

    private int statusCode;
    private long durationMs = -1;

    private Map<String, List<String>> headers;

    /**
     * Constructs a JxResponse object by initiating and handling an asynchronous HTTP request based on the provided request object.
     * Handles the composition of the request URI with query parameters, method type, headers, and optional body content.
     * Completes the operation by storing the response details such as body, status code, headers, and execution duration.
     *
     * @param request the {@link AbstractRequest} object containing the details of the HTTP request,
     *                including the URL, query parameters, headers, method, timeout, and body (if applicable).
     */
    public JxResponse(AbstractRequest<?> request) {
        String urlWithParams = buildUrlWithParams(request.getUrl(), request.getQueryParams());
        String bodyContent = (request instanceof AbstractBody<?>) ? ((AbstractBody<?>) request).getBody() : null;

        HttpRequest.Builder requestBuilder;

        try {
            requestBuilder = HttpRequest.newBuilder()
                    .uri(new URI(urlWithParams))
                    .timeout(Duration.ofMillis(request.getTimeoutMillis()))
                    .method(request.getMethod().toString(),
                            (bodyContent == null) ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(bodyContent));
        } catch (Exception e) {
            LOGGER.error("Error creating request: {}", e.getMessage());
            return;
        }

        request.getHeaders().forEach((key, value) -> requestBuilder.header(key, value.toString()));

        HttpRequest httpRequest = requestBuilder.build();

        long start = System.currentTimeMillis();

        CompletableFuture<HttpResponse<String>> response = CLIENT.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

        response.thenAccept(httpResponse -> {
            this.body = httpResponse.body();
            this.contentType = httpResponse.headers().firstValue("Content-Type")
                    .orElse(ContentType.TEXT_PLAIN.getMimeType());
            this.statusCode = httpResponse.statusCode();
            this.uri = httpResponse.uri().toString();
            this.headers = httpResponse.headers().map();

            this.durationMs = System.currentTimeMillis() - start;
        }).exceptionally(e -> {
            LOGGER.error("Error during request: {}", e.getMessage());
            durationMs = -1;
            return null;
        }).join();
    }

    /**
     * Constructs a complete URL by appending query parameters to the base URL.
     * If the provided query parameters are null or empty, the original URL is returned as is.
     * Handles encoding of both keys and values in the query parameters to ensure proper URL formatting.
     *
     * @param url the base URL to which query parameters will be appended
     * @param queryParams a map containing query parameter keys and their corresponding values
     * @return the complete URL with query parameters appended and properly encoded
     */
    private String buildUrlWithParams(String url, Map<String, Object> queryParams) {
        if (queryParams == null || queryParams.isEmpty()) return url;
        StringBuilder sb = new StringBuilder(url);
        sb.append(url.contains("?") ? "&" : "?");
        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
            String key = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8);
            String value = entry.getValue() == null ? "" : URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8);
            sb.append(key).append('=').append(value).append('&');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public StatusCode getStatus() {
        return StatusCode.fromCode(statusCode);
    }

    @Override
    public String toString() {
        return "JxResponse{" +
                "body='" + body + '\'' +
                ", contentType='" + contentType + '\'' +
                ", uri='" + uri + '\'' +
                ", statusCode=" + statusCode +
                ", durationMs=" + durationMs +
                ", headers=" + headers +
                '}';
    }
}