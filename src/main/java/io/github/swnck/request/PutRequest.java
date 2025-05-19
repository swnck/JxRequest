package io.github.swnck.request;

import io.github.swnck.util.Method;

/**
 * Represents an HTTP PUT request.
 * This class is a concrete implementation of {@link AbstractBody}, specifically tailored to handle HTTP PUT operations.
 * It supports specifying a request body as well as the endpoint URL.
 * <p>
 * By default, this class initializes the HTTP method to {@code PUT}, as required for PUT requests.
 * It provides constructors to create a PUT request either with a specified URL or without one.
 */
public class PutRequest extends AbstractBody<PutRequest> {
    public PutRequest(String url) {
        super(url, Method.PUT);
    }

    public PutRequest() {
        super(Method.PUT);
    }
}
