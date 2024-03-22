package de.swnck;

import de.swnck.config.ProxyConfiguration;
import de.swnck.config.RequestConfiguration;
import de.swnck.frame.type.BodyFrame;
import de.swnck.frame.type.HeaderFrame;
import de.swnck.frame.type.ResponseFrame;
import org.junit.jupiter.api.Test;

public class JxRequestTest {
    @Test
    void getRequest() {
        JxRequest jxRequest = new JxRequest(
                RequestConfiguration.builder().build(),
                ProxyConfiguration.builder().
                        host("85.214.250.48")
                        .port(3128)
                        .build()
        );

        jxRequest.get(HeaderFrame.empty(), (ResponseFrame responseFrame) -> {
            //assertNotNull(responseFrame.getContent()); Todo: impl. Http-Proxy
        }, "https://google.com");
    }

    @Test
    void postRequest() {
        JxRequest jxRequest = new JxRequest(
                RequestConfiguration.builder().build()
        );

        jxRequest.post(HeaderFrame.empty(), BodyFrame.empty(),
                (ResponseFrame responseFrame) -> {
            //assertNotNull(responseFrame.getContent()); Todo: impl. Http-Proxy
        }, "https://google.com");
    }
}
