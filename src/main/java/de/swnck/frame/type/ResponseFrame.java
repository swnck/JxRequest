package de.swnck.frame.type;

import de.swnck.frame.Frame;
import de.swnck.util.StatusCode;
import jdk.jshell.Snippet;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

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
}