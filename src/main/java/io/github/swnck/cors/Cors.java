package io.github.swnck.cors;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Cors is a builder class for creating CORS (Cross-Origin Resource Sharing) headers.
 * It allows you to specify the allowed origins, methods, headers, and whether credentials are allowed.
 */
@Getter
public class Cors {
    private final Map<String, Object> corsMap = new HashMap<>();

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

    public Cors allowMethods(String... methods) {
        if (methods == null || methods.length == 0) {
            throw new IllegalArgumentException("Methods cannot be null or empty");
        }

        this.corsMap.put("Access-Control-Allow-Methods", methods);
        return this;
    }

    public Cors allowHeaders(String... headers) {
        if (headers == null || headers.length == 0) {
            throw new IllegalArgumentException("Headers cannot be null or empty");
        }

        this.corsMap.put("Access-Control-Allow-Headers", headers);
        return this;
    }

    public Cors allowCredentials(boolean allowCredentials) {
        this.corsMap.put("Access-Control-Allow-Credentials", allowCredentials);
        return this;
    }
}
