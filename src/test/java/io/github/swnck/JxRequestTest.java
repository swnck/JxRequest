package io.github.swnck;

import io.github.swnck.request.GetRequest;
import io.github.swnck.request.PostRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JxRequestTest {
    @Test
    void get() {
        GetRequest jxRequest = JxRequest.get()
                .setUrl("google.com");

        JxResponse response = jxRequest.send();
        assertNotNull(response);
    }

    @Test
    void post() {
        PostRequest jxRequest = JxRequest.post()
                .setUrl("http://localhost:8080/users/test")
                .setBody("{\"name\": \"test\"}");

        JxResponse response = jxRequest.send();
        assertNotNull(response);
    }
}
