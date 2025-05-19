package io.github.swnck.request;

import io.github.swnck.util.Method;
import lombok.Getter;

/**
 * GetRequest is a specialized implementation of the AbstractRequest class
 * tailored for HTTP GET requests. This class provides constructors to
 * initialize a GET request with or without a specific URL.
 * <p>
 * The GetRequest class uses the HTTP GET method, which is primarily
 * designed for retrieving data from a server. It is a read-only operation
 * and is used to fetch resources identified by a specific URL.
 * <p>
 * This class ensures a consistent and predefined behavior for GET requests
 * and extends the functionality of the AbstractRequest class by
 * predefining the HTTP method.
 */
@Getter
public class GetRequest extends AbstractRequest<GetRequest> {

    public GetRequest(String url) {
        super(url, Method.GET);
    }

    public GetRequest() {
        super(Method.GET);
    }
}
