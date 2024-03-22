package de.swnck;

import de.swnck.config.RequestConfiguration;
import de.swnck.frame.type.BodyFrame;
import de.swnck.frame.type.HeaderFrame;
import de.swnck.frame.type.ResponseFrame;

import java.util.Map;

public class JxRequestTest {
    public static void main(String[] args) {
        JxRequest jxRequest = new JxRequest(RequestConfiguration.builder()
                .build());
        jxRequest.get(new HeaderFrame(), (ResponseFrame responseFrame) -> {
            System.out.println(responseFrame.getContent());
        }, "");

        jxRequest.post(new HeaderFrame(), BodyFrame.builder()
                .body(Map.of(
                        "email", "",
                        "password", ""
                )).build(), (ResponseFrame responseFrame) -> {
            System.out.println(responseFrame.getContent());
        }, "");
    }
}
