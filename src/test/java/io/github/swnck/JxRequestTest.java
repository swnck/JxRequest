package io.github.swnck;

import io.github.swnck.request.GetRequest;
import io.github.swnck.request.PostRequest;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JxRequestTest {
    @Test
    void get() {
        GetRequest jxRequest = JxRequest.get("http://localhost:8080/users/");
        JxResponse response = jxRequest.send();
        System.out.println(response);
        assertNotNull(response);
    }

    @Test
    void post() {
        PostRequest jxRequest = JxRequest.post()
                .setUrl("http://localhost:8080/users/test")
                .setBody("{\"name\": \"test\"}");

        JxResponse response = jxRequest.send();
        System.out.println(response);
        assertNotNull(response);
    }

    @Test
    void postWithParam() {
        PostRequest jxRequest = JxRequest.post()
                .setUrl("http://localhost:8080/users/test3")
                .setQueryParam("message", "test");

        JxResponse response = jxRequest.send();
        assertNotNull(response);
    }
}
