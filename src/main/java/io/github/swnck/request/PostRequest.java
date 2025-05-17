package io.github.swnck.request;

import io.github.swnck.util.Method;
import lombok.Getter;

@Getter
public class PostRequest extends AbstractRequest<PostRequest> {
    private String body;

    public PostRequest(String url) {
        super(url, Method.POST);
    }

    public PostRequest() {
        super(Method.POST);
    }

    public PostRequest setBody(String body) {
        if (body == null) {
            throw new IllegalArgumentException("Body cannot be null");
        }

        this.body = body;
        return this;
    }
}
