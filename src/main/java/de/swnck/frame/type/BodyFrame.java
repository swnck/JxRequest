package de.swnck.frame.type;

import com.google.gson.Gson;
import de.swnck.frame.Frame;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class BodyFrame implements Frame {

    private Map<String, Object> body;

    public BodyFrame() {
        this.body = new HashMap<>();
    }

    public BodyFrame(Map<String, Object> body) {
        this.body = body;
    }

    public String getBodyAsJson() {
        Gson gson = new Gson();
        return gson.toJson(this.body);
    }
}