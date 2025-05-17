package io.github.swnck.request.body.type.raw;

import io.github.swnck.util.ContentType;

public class HtmlRawBody extends RawBody {
    public HtmlRawBody() {
        super(ContentType.TEXT_HTML);
    }
}
