package de.swnck.consumer;

import de.swnck.frame.type.ResponseFrame;

@FunctionalInterface
public interface ResponseConsumer {
    void accept(ResponseFrame responseFrame);
}
