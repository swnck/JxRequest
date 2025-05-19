package io.github.swnck.request;

import io.github.swnck.util.Method;

/**
 * Represents an HTTP PATCH request.
 * This class is a concrete implementation of {@link AbstractBody}, specifically designed for handling HTTP PATCH operations.
 * It allows specifying a request body as well as the target endpoint URL.
 * <p>
 * By default, this class sets the HTTP method to {@code PATCH}, which is used to apply partial modifications to a resource.
 * Developers can use this class to create PATCH requests with or without specifying an initial URL.
 */
public class PatchRequest extends AbstractBody<PatchRequest>{
    public PatchRequest(String url) {
        super(url, Method.PATCH);
    }

    public PatchRequest() {
        super(Method.PATCH);
    }
}
