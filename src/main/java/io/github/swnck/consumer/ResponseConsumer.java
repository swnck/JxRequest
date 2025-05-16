package io.github.swnck.consumer;

import io.github.swnck.frame.type.ResponseFrame;

@FunctionalInterface
public interface ResponseConsumer {
    void accept(ResponseFrame responseFrame);
}
