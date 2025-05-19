package io.github.swnck.cors;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * The Cors class provides a mechanism for configuring Cross-Origin Resource Sharing (CORS) headers
 * for an HTTP response. It allows customization of origins, HTTP methods, headers,
 * and other CORS-related configurations.
 * <p>
 * This class uses a builder-style pattern, enabling chained method calls for setting
 * various CORS options. The settings are maintained in an internal map, `corsMap`, where
 * the configuration key-value pairs are stored.
 */
@Getter
public class Cors {
    /**
     * A map that stores CORS (Cross-Origin Resource Sharing) configuration settings.
     * The keys represent specific configuration options, such as allowed origins,
     * HTTP methods, headers, and other related parameters, while the values
     * represent their corresponding settings.
     * <p>
     * This map is used internally to maintain the CORS configurations applied
     * via various methods in the class. The settings stored in this map can
     * be applied to HTTP response headers to enable the desired CORS behavior.
     * <p>
     * The `corsMap` is populated and modified using methods provided in the
     * `Cors` class, allowing for dynamic and customizable CORS configurations.
     */
    private final Map<String, Object> corsMap = new HashMap<>();

    /**
     * Configures the allowed origin for Cross-Origin Resource Sharing (CORS).
     * This method sets the "Access-Control-Allow-Origin" header to the specified origin.
     * The wildcard character "*" can be used to allow all origins.
     *
     * @param origin the allowed origin for CORS. The value can be a specific origin (e.g., "https://example.com")
     *               or "*" to allow requests from any origin. Must not be null.
     * @return the updated {@code Cors} instance, allowing for method chaining.
     * @throws IllegalArgumentException if the origin is null.
     */
    public Cors allowOrigin(String origin) {
        if (origin == null) {
            throw new IllegalArgumentException("Origin cannot be null");
        }

        if (origin.equals("*")) {
            this.corsMap.put("Access-Control-Allow-Origin", "*");
            return this;
        }

        this.corsMap.put("Access-Control-Allow-Origin", origin);
        return this;
    }

    /**
     * Configures the allowed HTTP methods for Cross-Origin Resource Sharing (CORS).
     * This method sets the "Access-Control-Allow-Methods" header to the specified methods.
     *
     * @param methods the HTTP methods to allow for CORS (e.g., "GET", "POST", "PUT").
     *                Must not be null or empty.
     * @return the updated {@code Cors} instance, allowing for method chaining.
     * @throws IllegalArgumentException if the methods array is null or empty.
     */
    public Cors allowMethods(String... methods) {
        if (methods == null || methods.length == 0) {
            throw new IllegalArgumentException("Methods cannot be null or empty");
        }

        this.corsMap.put("Access-Control-Allow-Methods", methods);
        return this;
    }

    /**
     * Configures the allowed HTTP headers for Cross-Origin Resource Sharing (CORS).
     * This method sets the "Access-Control-Allow-Headers" header to the specified headers.
     *
     * @param headers the HTTP headers to allow for CORS. Must not be null or empty.
     * @return the updated {@code Cors} instance, allowing for method chaining.
     * @throws IllegalArgumentException if the headers array is null or empty.
     */
    public Cors allowHeaders(String... headers) {
        if (headers == null || headers.length == 0) {
            throw new IllegalArgumentException("Headers cannot be null or empty");
        }

        this.corsMap.put("Access-Control-Allow-Headers", headers);
        return this;
    }

    /**
     * Configures whether credentials are allowed for Cross-Origin Resource Sharing (CORS).
     * This method sets the "Access-Control-Allow-Credentials" header to the specified value.
     * Setting this to true allows the sharing of credentials such as cookies, authentication headers, or
     * TLS client certificates in cross-origin requests.
     *
     * @param allowCredentials a boolean indicating whether credentials are allowed. If true,
     *                         the response indicates that the resource is prepared to
     *                         share credentials with the client.
     * @return the updated {@code Cors} instance, allowing for method chaining.
     */
    public Cors allowCredentials(boolean allowCredentials) {
        this.corsMap.put("Access-Control-Allow-Credentials", allowCredentials);
        return this;
    }
}
