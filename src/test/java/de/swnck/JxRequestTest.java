package de.swnck;

import de.swnck.config.RequestConfiguration;
import de.swnck.frame.type.BodyFrame;
import de.swnck.frame.type.HeaderFrame;
import de.swnck.frame.type.ResponseFrame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JxRequestTest {
    @Test
    void getRequest() {
        JxRequest jxRequest = new JxRequest();

        jxRequest.get(HeaderFrame.empty(), (ResponseFrame responseFrame) -> {
            System.out.println(responseFrame.getContent());
            assertNotNull(responseFrame.getContent());
        }, "https://google.com");
    }

    @Test
    void postRequest() {
        JxRequest jxRequest = new JxRequest();

        jxRequest.post(HeaderFrame.empty(), BodyFrame.empty(),
                (ResponseFrame responseFrame) -> {
                    System.out.println(responseFrame.getContent());
            assertNotNull(responseFrame.getContent()); //Todo: impl. Http-Proxy
        }, "https://google.com");
    }
}
