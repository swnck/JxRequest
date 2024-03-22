package de.swnck.frame.type;

import de.swnck.frame.Frame;
import de.swnck.util.StatusCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ResponseFrame implements Frame {
    private final StatusCode statusCode;
    private final String content;

    public ResponseFrame(StatusCode statusCode, String content) {
        this.statusCode = statusCode;
        this.content = content;
    }

    public int getStatusCode() {
        return statusCode.getCode();
    }
}