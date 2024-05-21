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
        JxRequest jxRequest = new JxRequest(
                RequestConfiguration.builder().build()
        );

        jxRequest.get(HeaderFrame.empty(), (ResponseFrame responseFrame) -> {
            System.out.println(responseFrame.getContent());
            assertNotNull(responseFrame.getContent());
        }, "https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=ya29.a0AXooCgs28m9nJUQattcNXQP25qV4QNzlSmrQYYRQycx4cIVQ2KUtCbdsUCWuZctxg7yUfBt7FMvmhGTa9OBKCMckKqWmhG__jPYOaz84QH9E5c8h77GNr19sb4bPmbSHY80MCHspJZ0bO5V8dq14xLpE2bYEPCC_Ce-5aCgYKAe8SARISFQHGX2MiXoRwnMcL6qoZ2AUC7XT4Ow0171");
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
