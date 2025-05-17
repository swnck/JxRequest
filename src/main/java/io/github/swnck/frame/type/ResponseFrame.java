package io.github.swnck.frame.type;

import com.google.gson.Gson;
import io.github.swnck.frame.Frame;
import io.github.swnck.util.StatusCode;
import lombok.Getter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;

@Getter
public class ResponseFrame implements Frame {
    public final URI uri;
    public final HttpHeaders headers;
    public final HttpClient.Version version;
    private final StatusCode statusCode;
    private final String content;

    public ResponseFrame(URI uri, HttpClient.Version version, StatusCode statusCode, String content, HttpHeaders headers) {
        this.headers = headers;
        this.uri = uri;
        this.version = version;
        this.statusCode = statusCode;
        this.content = content;
    }

    public int getStatusCode() {
        return statusCode.getCode();
    }

    public StatusCode getStatusCodeEnum() {
        return statusCode;
    }

    public String getJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this.content);
    }

    public Gson getJsonObject() {
        Gson gson = new Gson();
        return gson.fromJson(this.content, Gson.class);
    }
}