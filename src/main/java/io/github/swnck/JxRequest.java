package io.github.swnck;

import io.github.swnck.request.GetRequest;
import io.github.swnck.request.PostRequest;
import lombok.Getter;

@Getter
public class JxRequest {

    public static GetRequest get(String url) {
        return new GetRequest(url);
    }

    public static PostRequest post(String url) {
        return  new PostRequest(url);
    }

    public static GetRequest get() {
        return new GetRequest();
    }

    public static PostRequest post() {
        return  new PostRequest();
    }
}
