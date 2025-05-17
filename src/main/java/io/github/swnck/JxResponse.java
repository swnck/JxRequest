package io.github.swnck;

import io.github.swnck.request.AbstractRequest;
import io.github.swnck.request.PostRequest;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

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

    public JxResponse(AbstractRequest<?> request) {
        String urlWithParams = buildUrlWithParams(request.url, request.getQueryParams());
        String bodyContent = (request instanceof PostRequest) ? ((PostRequest) request).getBody() : null;

        HttpRequest.Builder requestBuilder;

        try {
            requestBuilder = HttpRequest.newBuilder()
                    .uri(new URI(urlWithParams))
                    .method(request.getMethod().toString(),
                            (bodyContent == null) ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(bodyContent));
        } catch (Exception e) {
            LOGGER.error("Error creating request: {}", e.getMessage());
            return;
        }

        request.getHeaders().forEach((key, value) -> requestBuilder.header(key, value.toString()));
        requestBuilder.header("Content-Type", Objects.requireNonNullElse(request.contentType, ContentType.TEXT_PLAIN).getMimeType());

        HttpRequest httpRequest = requestBuilder.build();

        long start = System.currentTimeMillis();

        CompletableFuture<HttpResponse<String>> response = CLIENT.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

        response.thenAccept(httpResponse -> {
            this.body = httpResponse.body();
            this.contentType = httpResponse.headers().firstValue("Content-Type").orElse(ContentType.TEXT_PLAIN.getMimeType());
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