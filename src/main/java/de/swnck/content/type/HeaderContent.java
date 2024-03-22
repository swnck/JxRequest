package de.swnck.content.type;

import de.swnck.content.Content;
import de.swnck.frame.type.HeaderFrame;

import java.util.HashMap;
import java.util.Map;

public class HeaderContent implements Content {

    @Override
    public HeaderFrame paste(Map<String, Object> array) {
        Map<String, Object> finalBody = new HashMap<>(array);
        return new HeaderFrame(finalBody);
    }

    @Override
    public HeaderFrame empty() {
        return new HeaderFrame(new HashMap<>());
    }
}