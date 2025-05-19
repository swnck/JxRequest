package io.github.swnck.request;

import io.github.swnck.JxResponse;
import io.github.swnck.cors.Cors;
import io.github.swnck.util.ContentType;
import io.github.swnck.util.Method;
import io.github.swnck.util.SimulationAgent;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * AbstractRequest represents a template for constructing and sending HTTP requests.
 * This abstract class provides functionalities for customization of HTTP request properties
 * such as headers, query parameters, HTTP method, URL, content type, timeout, and CORS support.
 * It is designed to be extended by specific subclasses representing different types of HTTP requests.
 *
 * @param <T> The type of the subclass extending AbstractRequest, enabling method chaining for configuration.
 */
@Getter
@Setter
public abstract class AbstractRequest<T extends AbstractRequest<T>>{

    /**
     * A collection of key-value pairs representing the HTTP headers for the request.
     * <p>
     * Each key in the map corresponds to a header name, and its associated value
     * represents the header value. These headers are used to define additional
     * metadata or configuration for the outgoing HTTP request.
     * <p>
     * This map is initialized as an empty {@code HashMap} and can be modified
     * using associated methods such as {@code setHeader} or {@code setHeaders}.
     * <p>
     * The map is marked as {@code final}, ensuring that it cannot be reassigned
     * after being initialized, while still allowing modification of its contents.
     */
    private final Map<String, Object> headers = new HashMap<>();

    /**
     * Represents a collection of query parameters to be included in an HTTP request.
     * <p>
     * This map stores key-value pairs where the key is the name of the query parameter
     * and the value is the parameter's value. It is used to construct the query string
     * included in the request's URL. Adding, modifying, or removing parameters in this
     * map directly affects the query parameters of the associated request.
     * <p>
     * Query parameters can be set or updated using methods such as {@code setQueryParam(String, String)}
     * or {@code setQueryParams(Map<String, Object>)}.
     * <p>
     * The map is initialized as an empty {@code HashMap} and will contain no parameters by default.
     * <p>
     * This field is immutable, and its reference cannot be reassigned.
     */
    private final Map<String, Object> queryParams = new HashMap<>();

    /**
     * Represents the HTTP method used for the current request.
     * This field stores the specific HTTP method type, such as GET, POST,
     * DELETE, PUT, or PATCH, to dictate how the request interacts with
     * the target resource.
     * <p>
     * The {@code method} is initialized during the construction of the
     * {@code AbstractRequest} or its subclasses, and it governs the
     * behavior of the HTTP operation performed by the request.
     * <p>
     * Supported HTTP methods:
     * - GET: Retrieve data from the server.
     * - POST: Send data to the server for creation.
     * - DELETE: Remove a resource on the server.
     * - PUT: Create or overwrite a resource on the server.
     * - PATCH: Make partial modifications to a resource on the server.
     *
     * @see Method
     */
    private Method method;

    /**
     * Represents the Cross-Origin Resource Sharing (CORS) configuration for the request.
     * This variable holds an instance of the {@code Cors} class, which defines the CORS
     * headers to be included with the HTTP request.
     * <p>
     * The {@code Cors} class provides methods for specifying allowed origins, HTTP methods,
     * headers, and credential settings for configuring cross-origin requests.
     * <p>
     * If set, the CORS headers defined in this variable are applied to the request,
     * enabling the appropriate handling of cross-origin policies.
     * <p>
     * Default value is {@code null} when no CORS configuration is set.
     */
    private Cors cors = null;

    /**
     * The URL to which the HTTP request will be sent.
     * This field is expected to hold the target endpoint of the request,
     * including the protocol (e.g., "http://" or "https://") and, optionally,
     * any path or query components.
     * <p>
     * The value of this field must be initialized either through a constructor
     * or using the `setUrl` method, ensuring the string starts with a valid
     * protocol. If not explicitly provided, "http://" is prefixed by default.
     * <p>
     * This field is critical for configuring the desired endpoint for the request
     * execution and is used in conjunction with other request elements
     * such as headers, query parameters, and HTTP methods.
     */
    private String url;

    /**
     * The timeout duration in milliseconds for the execution of the request.
     * This value specifies the maximum amount of time the request is allowed to run
     * before being forcibly terminated or marked as timed out.
     * <p>
     * A default value of 100000 milliseconds (100 seconds) is assigned, signifying the request
     * will wait up to 100 seconds unless a different timeout is set using the
     * appropriate configuration method. Adjusting this value allows tuning
     * the request's timeout based on specific use cases or requirements.
     */
    private int timeoutMillis = 100000;

    /**
     * Constructs a new instance of {@code AbstractRequest} with the specified URL and HTTP method.
     * This constructor initializes the request with the provided URL and method, sets the default
     * content type to {@code ContentType.TEXT_PLAIN}, and verifies the URL format.
     *
     * @param url the URL to which the request will be sent; must not be null.
     *            If the URL does not start with "http://" or "https://", "http://" will be prefixed.
     * @param method the HTTP method to be used for the request; must not be null.
     *               Supported methods include GET, POST, DELETE, PUT, and PATCH.
     */
    public AbstractRequest(String url, Method method) {
        this.method = method;

        this.setUrl(url);
        this.setContentType(ContentType.TEXT_PLAIN);
    }

    /**
     * Constructs a new {@code AbstractRequest} instance with the specified HTTP method.
     * This constructor initializes the request with the provided method, allowing
     * subclasses to define additional configurations or behaviors as needed.
     *
     * @param method the HTTP method to be used for the request; must not be null.
     *               Supported methods include GET, POST, DELETE, PUT, and PATCH.
     * @throws IllegalArgumentException if the method is null.
     */
    public AbstractRequest(Method method) {
        this.method = method;
    }

    /**
     * Executes the HTTP request defined by this instance and returns the resulting response.
     * <p>
     * This method initiates the request processing with the configured URL, method, headers,
     * query parameters, and optional body content (if applicable). It constructs an HTTP client request
     * and performs the call using asynchronous handling, capturing details such as the status code,
     * headers, body content, and response timing.
     *
     * @return an instance of {@link JxResponse} containing the HTTP response details such as
     * status code, headers, body, and execution duration.
     */
    public JxResponse send() {
        return new JxResponse(this);
    }

    /**
     * Simulates a request by setting the appropriate `User-Agent` header
     * based on the specified simulation agent.
     *
     * @param agent the simulation agent to use for setting the `User-Agent` header;
     *              must not be null.
     * @return the updated request instance for further chaining.
     * @throws IllegalArgumentException if the provided agent is null.
     */
    @SuppressWarnings("unchecked")
    public T simulate(SimulationAgent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("Simulation agent cannot be null");
        }

        headers.put("User-Agent", agent.getUserAgent());
        return (T) this;
    }

    /**
     * Simulates a request by setting the `User-Agent` header with the provided agent value.
     *
     * @param agent the `User-Agent` string to be used for the simulation; must not be null.
     * @return the updated instance of the request, allowing for method chaining.
     * @throws IllegalArgumentException if the provided agent is null.
     */
    @SuppressWarnings("unchecked")
    public T simulate(String agent) {
        if (agent == null) {
            throw new IllegalArgumentException("Simulation agent cannot be null");
        }

        headers.put("User-Agent", agent);
        return (T) this;
    }

    /**
     * Sets the Content-Type header for the request to the specified value.
     *
     * @param contentType the value to set as the Content-Type; must not be null.
     * @return the updated instance of the request, allowing for method chaining.
     * @throws IllegalArgumentException if the provided contentType is null.
     */
    @SuppressWarnings("unchecked")
    public T setContentType(String contentType) {
        if (contentType == null) {
            throw new IllegalArgumentException("Content-Type cannot be null");
        }

        headers.put("Content-Type", contentType);

        return (T) this;
    }

    /**
     * Sets the Content-Type header of the request to the specified value.
     *
     * @param contentType the ContentType to be used as the Content-Type; must not be null.
     * @return the updated instance of the request, allowing for method chaining.
     * @throws IllegalArgumentException if the provided contentType is null.
     */
    @SuppressWarnings("unchecked")
    public T setContentType(ContentType contentType) {
        if (contentType == null) {
            throw new IllegalArgumentException("Content-Type cannot be null");
        }

        headers.put("Content-Type", contentType.getMimeType());

        return (T) this;
    }

    /**
     * Sets a header for the current request. If a header with the given key already exists,
     * it will be replaced with the specified value.
     *
     * @param key the name of the header to set; must not be null.
     * @param value the value of the header to set; must not be null.
     * @return the updated instance of the request, allowing for method chaining.
     * @throws IllegalArgumentException if the key or value is null.
     */
    @SuppressWarnings("unchecked")
    public T setHeader(String key, String value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Header key and value cannot be null");
        }

        headers.put(key, value);
        return (T) this;
    }

    /**
     * Sets multiple headers for the current request by merging the provided map of headers.
     * If a header with the same key already exists, it will be overwritten with the new value.
     *
     * @param headers a map of header names and their corresponding values to set; must not be null.
     * @return the updated instance of the request, allowing for method chaining.
     * @throws IllegalArgumentException if the provided headers map is null.
     */
    @SuppressWarnings("unchecked")
    public T setHeaders(Map<String, Object> headers) {
        if (headers == null) {
            throw new IllegalArgumentException("Headers cannot be null");
        }

        this.headers.putAll(headers);
        return (T) this;
    }

    /**
     * Sets a query parameter for the current request.
     * If the query parameter with the given key already exists, it will be replaced with the specified value.
     *
     * @param key the key of the query parameter to set; must not be null.
     * @param value the value of the query parameter to set; must not be null.
     * @return the updated instance of the request, allowing for method chaining.
     * @throws IllegalArgumentException if the key or value is null.
     */
    @SuppressWarnings("unchecked")
    public T setQueryParam(String key, String value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Query parameter key and value cannot be null");
        }

        queryParams.put(key, value);
        return (T) this;
    }

    /**
     * Sets multiple query parameters for the current request by merging the provided map of parameters.
     * If a query parameter with the same key already exists, it will be overwritten with the new value.
     *
     * @param queryParams a map of query parameter keys and*/
    @SuppressWarnings("unchecked")
    public T setQueryParams(Map<String, Object> queryParams) {
        if (queryParams == null) {
            throw new IllegalArgumentException("Query parameters cannot be null");
        }

        this.queryParams.putAll(queryParams);
        return (T) this;
    }

    /**
     * Sets the CORS (Cross-Origin Resource Sharing) configuration for the request.
     * <p>
     * The provided CORS object contains the necessary headers to configure
     * access control for cross-origin requests. These headers are extracted
     * from the supplied {@code Cors} object and added to the request's headers.
     *
     * @param cors the {@code Cors} object containing CORS headers to set;
     *             if null, this method does nothing.
     * @return the updated instance of the request, allowing for method chaining.
     */
    @SuppressWarnings("unchecked")
    public T setCors(Cors cors) {
        if (cors == null) return (T) this;

        cors.getCorsMap().forEach(
                (key, value) -> {
                    if (value instanceof String) {
                        this.headers.put(key, value);
                    } else if (value instanceof String[]) {
                        this.headers.put(key, String.join(", ", (String[]) value));
                    } else {
                        this.headers.put(key, value);
                    }
                }
        );

        this.cors = cors;
        return (T) this;
    }

    /**
     * Sets the URL for the request. If the provided URL does not start with
     * "http://" or "https://", "http://" is prefixed automatically.
     *
     * @param url the URL to set; must not be null. If the URL is null,
     *            an IllegalArgumentException is thrown.
     * @return the updated instance of the request, allowing for method chaining.
     * @throws IllegalArgumentException if the provided URL is null.
     */
    @SuppressWarnings("unchecked")
    public T setUrl(String url) {
        if (url == null) {
            throw new IllegalArgumentException("URL cannot be null");
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }

        this.url = url;
        return (T) this;
    }

    /**
     * Sets the timeout duration for the request execution in milliseconds.
     * The timeout value specifies the maximum duration the request is allowed to take before timing out.
     *
     * @param timeoutMillis the timeout duration in milliseconds; must be a non-negative value.
     *                      If a negative value is provided, an {@link IllegalArgumentException} is thrown.
     * @return the updated instance of the request, allowing for method chaining.
     * @throws IllegalArgumentException if the specified timeout value is less than 0.
     */
    @SuppressWarnings("unchecked")
    public T setTimeout(int timeoutMillis) {
        if (timeoutMillis < 0) {
            throw new IllegalArgumentException("Timeout must be >= 0");
        }
        this.timeoutMillis = timeoutMillis;
        return (T) this;
    }

    /**
     * Sets the timeout duration for the request execution.
     * The timeout specifies the period after which the request will time out if not completed.
     *
     * @param timeout the timeout duration as a {@link Duration} object; must be non-negative.
     *                If the duration is negative, an {@link IllegalArgumentException} is thrown.
     * @return the updated instance of the request, allowing for method chaining.
     * @throws IllegalArgumentException if the specified timeout duration is less than 0.
     */
    @SuppressWarnings("unchecked")
    public T setTimeout(Duration timeout) {
        if (timeoutMillis < 0) {
            throw new IllegalArgumentException("Timeout must be >= 0");
        }
        this.timeoutMillis = (int) timeout.toMillis();
        return (T) this;
    }
}