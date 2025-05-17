package io.github.swnck.request;

import io.github.swnck.util.Method;
import lombok.Getter;

@Getter
public class GetRequest extends AbstractRequest<GetRequest> {

    public GetRequest(String url) {
        super(url, Method.GET);
    }

    public GetRequest() {
        super(Method.GET);
    }
}
