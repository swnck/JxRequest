package io.github.swnck.request.body.type.raw;

import io.github.swnck.request.body.Body;
import io.github.swnck.util.ContentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class RawBody extends Body {
    private String body;

    public RawBody(ContentType contentType) {
        super(contentType);
    }
}
