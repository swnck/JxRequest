package io.github.swnck.request;

import io.github.swnck.util.Method;
import lombok.Getter;

/**
 * AbstractBody is an abstract base class to handle HTTP request bodies in concrete implementations of various HTTP methods.
 * It provides mechanisms for setting and validating the body content of HTTP requests.
 *
 * @param <T> the type of the subclass extending AbstractBody
 */
@Getter
public abstract class AbstractBody<T extends AbstractBody<T>> extends AbstractRequest<T> {

    /**
     * The `body` variable represents the content of the HTTP request body.
     * It is used to store the data that will be transmitted in the request
     * for HTTP methods supporting a body, such as POST, PUT, PATCH, and DELETE.
     */
    private String body;

    /**
     * Constructs a new {@code AbstractBody} instance with the specified URL and HTTP method.
     * This constructor initializes the HTTP request body handler using the provided URL and method.
     *
     * @param url the URL to which the request will be sent; must not be null. If the URL does not
     *            start with "http://" or "https://", "http://" will be prefixed.
     * @param method the HTTP method to be used for the request; must not be null. Supported methods
     *               include GET, POST, DELETE, PUT, and PATCH.
     */
    public AbstractBody(String url, Method method) {
        super(url, method);
    }

    /**
     * Constructs a new {@code AbstractBody} instance with the specified HTTP method.
     * This constructor initializes the HTTP request body handler using the provided method.
     *
     * @param method the HTTP method to be used for the request; must not be null.
     *               Supported methods include GET, POST, DELETE, PUT, and PATCH.
     * @throws IllegalArgumentException if the method is null.
     */
    public AbstractBody(Method method) {
        super(method);
    }

    /**
     * Sets the body of the HTTP request.
     *
     * @param body the content to be set as the body of the HTTP request; must not be null
     * @return the current instance of the subclass implementation for method chaining
     * @throws IllegalArgumentException if the provided body is null
     */
    @SuppressWarnings("unchecked")
    public T setBody(String body) {
        if (body == null) {
            throw new IllegalArgumentException("AbstractBody cannot be null");
        }

        this.body = body;
        return (T) this;
    }
}