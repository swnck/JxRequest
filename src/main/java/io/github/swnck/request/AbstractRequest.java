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

@Getter
@Setter
public abstract class AbstractRequest<T extends AbstractRequest<T>>{

    private final Map<String, Object> headers = new HashMap<>();
    private final Map<String, Object> queryParams = new HashMap<>();

    private Method method;
    private Cors cors = null;

    private String url;

    private int timeoutMillis = 100000;

    public AbstractRequest(String url, Method method) {
        this.method = method;

        this.setUrl(url);
        this.setContentType(ContentType.TEXT_PLAIN);
    }

    public AbstractRequest(Method method) {
        this.method = method;
    }

    public JxResponse send() {
        return new JxResponse(this);
    }

    @SuppressWarnings("unchecked")
    public T simulate(SimulationAgent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("Simulation agent cannot be null");
        }

        headers.put("User-Agent", agent.getUserAgent());
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T simulate(String agent) {
        if (agent == null) {
            throw new IllegalArgumentException("Simulation agent cannot be null");
        }

        headers.put("User-Agent", agent);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setContentType(String contentType) {
        if (contentType == null) {
            throw new IllegalArgumentException("Content-Type cannot be null");
        }

        headers.put("Content-Type", contentType);

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setContentType(ContentType contentType) {
        if (contentType == null) {
            throw new IllegalArgumentException("Content-Type cannot be null");
        }

        headers.put("Content-Type", contentType.getMimeType());

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setHeader(String key, String value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Header key and value cannot be null");
        }

        headers.put(key, value);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setHeaders(Map<String, Object> headers) {
        if (headers == null) {
            throw new IllegalArgumentException("Headers cannot be null");
        }

        this.headers.putAll(headers);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setQueryParam(String key, String value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Query parameter key and value cannot be null");
        }

        queryParams.put(key, value);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setQueryParams(Map<String, Object> queryParams) {
        if (queryParams == null) {
            throw new IllegalArgumentException("Query parameters cannot be null");
        }

        this.queryParams.putAll(queryParams);
        return (T) this;
    }

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

    @SuppressWarnings("unchecked")
    public T setTimeout(int timeoutMillis) {
        if (timeoutMillis < 0) {
            throw new IllegalArgumentException("Timeout must be >= 0");
        }
        this.timeoutMillis = timeoutMillis;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setTimeout(Duration timeout) {
        if (timeoutMillis < 0) {
            throw new IllegalArgumentException("Timeout must be >= 0");
        }
        this.timeoutMillis = (int) timeout.toMillis();
        return (T) this;
    }
}