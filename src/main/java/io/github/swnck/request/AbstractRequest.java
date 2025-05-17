package io.github.swnck.request;

import io.github.swnck.JxResponse;
import io.github.swnck.cors.Cors;
import io.github.swnck.util.ContentType;
import io.github.swnck.util.Method;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class AbstractRequest<T extends AbstractRequest<T>> {
    public Method method;

    public final Map<String, Object> headers = new HashMap<>();
    public final Map<String, Object> queryParams = new HashMap<>();
    public ContentType contentType = ContentType.TEXT_PLAIN;

    public Cors cors = null;
    public String url;

    public AbstractRequest(String url, Method method) {
        this.method = method;
        this.url = url;
    }

    public AbstractRequest(Method method) {
        this.method = method;
    }

    public JxResponse send() {
        return new JxResponse(this);
    }

    @SuppressWarnings("unchecked")
    public T setContentType(String contentType) {
        if (contentType == null) {
            throw new IllegalArgumentException("Content-Type cannot be null");
        }

        this.contentType = ContentType.fromString(contentType);
        headers.put("Content-Type", contentType);

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

        this.url = url;
        return (T) this;
    }
}