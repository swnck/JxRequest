package de.swnck.content.type;

import de.swnck.content.Content;
import de.swnck.frame.type.BodyFrame;

import java.util.HashMap;
import java.util.Map;

public class BodyContent implements Content {

    @Override
    public BodyFrame paste(Map<String, Object> array) {
        Map<String, Object> finalBody = new HashMap<>(array);
        return new BodyFrame(finalBody);
    }

    @Override
    public BodyFrame empty() {
        return new BodyFrame(new HashMap<>());
    }
}