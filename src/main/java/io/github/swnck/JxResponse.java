package io.github.swnck;

import io.github.swnck.request.AbstractRequest;
import io.github.swnck.request.PostRequest;
import io.github.swnck.util.ContentType;
import io.github.swnck.util.StatusCode;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Getter
@Setter
public class JxResponse {
    private static final HttpClient CLIENT = HttpClient.newBuilder().build();

    private String body;
    private String contentType;
    private String uri;

    private int statusCode;
    private long durationMs = -1;

    private Map<String, List<String>> headers;

    public JxResponse(AbstractRequest<?> request) {
        String urlWithParams = buildUrlWithParams(request.url, request.getQueryParams());
        String bodyContent = (request instanceof PostRequest) ? ((PostRequest)request).getBody() : null;

        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(new URI(urlWithParams))
                    .method(request.getMethod().toString(),
                            (bodyContent == null) ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(bodyContent));
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
            }).join();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    private String buildUrlWithParams(String url, Map<String, Object> queryParams) {
        if (queryParams == null || queryParams.isEmpty()) return url;
        StringBuilder sb = new StringBuilder(url);
        sb.append(url.contains("?") ? "&" : "?");
        queryParams.forEach((k,v) -> sb.append(k).append('=').append(v).append('&'));
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