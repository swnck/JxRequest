package de.swnck.content;

import de.swnck.frame.Frame;

import java.util.Map;

public interface Content {
    Frame paste(Map<String, Object> array);
    Frame empty();
}
