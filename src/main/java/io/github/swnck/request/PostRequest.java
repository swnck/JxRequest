package io.github.swnck.request;

import io.github.swnck.util.Method;
import lombok.Getter;

/**
 * Represents an HTTP POST request.
 * This class is a concrete implementation of {@link AbstractBody}, specifically designed for handling HTTP POST operations.
 * It allows setting the request body and the target endpoint URL.
 * <p>
 * The HTTP method for this request is pre-defined as {@code POST}, which is used for sending data to the server,
 * typically for creating a new resource.
 * <p>
 * It provides constructors to create a POST request with or without an initial URL.
 */
@Getter
public class PostRequest extends AbstractBody<PostRequest> {
    public PostRequest(String url) {
        super(url, Method.POST);
    }

    public PostRequest() {
        super(Method.POST);
    }
}
