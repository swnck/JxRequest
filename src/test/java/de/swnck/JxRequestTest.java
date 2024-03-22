package de.swnck;

import de.swnck.config.RequestConfiguration;
import de.swnck.frame.type.HeaderFrame;
import de.swnck.frame.type.ResponseFrame;
import org.junit.jupiter.api.Test;

public class JxRequestTest {
    @Test
    void getRequest() {
        JxRequest jxRequest = new JxRequest(
                RequestConfiguration.builder().build()
        );

        jxRequest.get(new HeaderFrame(), (ResponseFrame responseFrame) -> {
            //assertNotNull(responseFrame.getContent()); Todo: impl. Http-Proxy
        }, "https://google.com");
    }
}
