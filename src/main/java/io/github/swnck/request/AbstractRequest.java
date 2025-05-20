package io.github.swnck.request;

import io.github.swnck.JxResponse;
import io.github.swnck.cors.Cors;
import io.github.swnck.util.ContentType;
import io.github.swnck.util.Method;
import io.github.swnck.util.SimulationAgent;
import lombok.Getter;
import lombok.Setter;

import java.net.http.HttpClient;
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
    private final Map<String, Object> headers = new HashMap<>();

    private final Map<String, Object> queryParams = new HashMap<>();

    private Method method;

    private Cors cors = null;

    private String url;

    private int timeoutMillis = 100000;

    private HttpClient.Version version = HttpClient.Version.HTTP_1_1;

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
     * Sets the HTTP protocol version for the current request.
     *
     * This method allows the user to specify the desired {@link HttpClient.Version}
     * to be used for the request. The specified version is stored and updated
     * within the request instance.
     *
     * @param version the HTTP protocol version to set; must not be null.
     * @return the updated instance of the request, allowing for method chaining.
     * @throws IllegalArgumentException if the provided version is null.
     */
    @SuppressWarnings("unchecked")
    public T setVersion(HttpClient.Version version) {
        if (version == null) {
            throw new IllegalArgumentException("Version cannot be null");
        }
        this.version = version;
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