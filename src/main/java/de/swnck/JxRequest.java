package de.swnck;

import de.swnck.config.ProxyConfiguration;
import de.swnck.config.RequestConfiguration;
import de.swnck.consumer.ResponseConsumer;
import de.swnck.frame.type.BodyFrame;
import de.swnck.frame.type.HeaderFrame;
import de.swnck.frame.type.ResponseFrame;
import de.swnck.util.ContentType;
import de.swnck.util.StatusCode;
import lombok.Getter;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Getter
public class JxRequest {
    private RequestConfiguration requestConfiguration = null;
    private ProxyConfiguration proxyConfiguration = null;

    public JxRequest(RequestConfiguration requestConfiguration, ProxyConfiguration proxyConfiguration) {
        this.requestConfiguration = requestConfiguration;
        this.proxyConfiguration = proxyConfiguration;
    }

    public JxRequest(RequestConfiguration requestConfiguration) {
        this.requestConfiguration = requestConfiguration;
    }

    public JxRequest(ProxyConfiguration proxyConfiguration) {
        this.proxyConfiguration = proxyConfiguration;
    }


    public ResponseFrame get(HeaderFrame headers, ResponseConsumer responseConsumer, String url) {
        return performRequest("GET", headers, null, responseConsumer, url);
    }

    public ResponseFrame post(HeaderFrame headers, BodyFrame bodyFrame, ResponseConsumer responseConsumer, String url) {
        return performRequest("POST", headers, bodyFrame, responseConsumer, url);
    }

    public ResponseFrame put(HeaderFrame headers, BodyFrame bodyFrame, ResponseConsumer responseConsumer, String url) {
        return performRequest("PUT", headers, bodyFrame, responseConsumer, url);
    }

    public ResponseFrame delete(HeaderFrame headers, BodyFrame bodyFrame, ResponseConsumer responseConsumer, String url) {
        return performRequest("DELETE", headers, bodyFrame, responseConsumer, url);
    }

    private ResponseFrame performRequest(String method, HeaderFrame headers, BodyFrame bodyFrame, ResponseConsumer responseConsumer, String url) {
        try {
            HttpClient.Builder clientBuilder = HttpClient.newBuilder();
            if (proxyConfiguration != null) {
                clientBuilder.proxy(ProxySelector.of(new InetSocketAddress(proxyConfiguration.getHost(), proxyConfiguration.getPort())));
            }
            HttpClient client = clientBuilder.build();

            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .method(method, (bodyFrame == null) ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(bodyFrame.getBodyAsJson()));

            if (headers == null) headers = HeaderFrame.empty();
            if (!headers.hasHeader("Content-Type")) {
                requestBuilder.header("Content-Type", ContentType.TEXT_PLAIN.getMimeType());
            }

            headers.getHeaders().forEach(requestBuilder::header);

            HttpRequest request = requestBuilder.build();
            CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            AtomicReference<ResponseFrame> responseFrame = new AtomicReference<>();
            response.thenAccept(httpResponse -> {
                responseFrame.set(new ResponseFrame(httpResponse.uri(), httpResponse.version(), StatusCode.fromCode(httpResponse.statusCode()), httpResponse.body(), httpResponse.headers()));
                if (responseConsumer != null) responseConsumer.accept(responseFrame.get());
            }).join();

            return responseFrame.get();
        } catch (Exception e) {
            System.err.println("HTTP request error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
