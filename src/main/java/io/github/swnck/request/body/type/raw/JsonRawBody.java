package io.github.swnck.request.body.type.raw;

import io.github.swnck.util.ContentType;

public class JsonRawBody extends RawBody {
    public JsonRawBody() {
        super(ContentType.APPLICATION_JSON);
    }
}
