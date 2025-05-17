package io.github.swnck.request.body;

import io.github.swnck.util.ContentType;
import lombok.Getter;

@Getter
public abstract class Body {
    public ContentType contentType;

    public Body(ContentType contentType) {
        this.contentType = contentType;
    }
}
