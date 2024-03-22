package de.swnck.frame.type;

import de.swnck.frame.Frame;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class HeaderFrame implements Frame {

    private Map<String, Object> headers;

    public HeaderFrame() {
        this.headers = new HashMap<>();
    }

    public HeaderFrame(Map<String, Object> header) {
        this.headers = header;
    }

    public static HeaderFrame empty() {
        return new HeaderFrame(new HashMap<>());
    }

    public void addHeaderEntry(String headerName, Object headerValue) {
        headers.put(headerName, headerValue);
    }

    public boolean hasHeader(String headerName) {
        return headers.containsKey(headerName);
    }
}