package io.github.swnck.request;

import io.github.swnck.util.Method;

/**
 * Represents an HTTP DELETE request.
 * This class is a concrete implementation of {@link AbstractBody}, specifically designed for handling HTTP DELETE operations.
 * It allows setting the target endpoint URL and, optionally, a request body.
 * <p>
 * The HTTP DELETE method is used for removing resources from the server. This class pre-defines the HTTP method as {@code DELETE}.
 * <p>
 * DeleteRequest provides constructors to initialize a DELETE request either with a specified URL or without one.
 * When a URL is provided, the request will be initialized with that target endpoint.
 */
public class DeleteRequest extends AbstractBody<DeleteRequest> {
    public DeleteRequest(String url) {
        super(url, Method.DELETE);
    }

    public DeleteRequest() {
        super(Method.DELETE);
    }
}
