package io.github.swnck.request.body.type.raw;

import io.github.swnck.util.ContentType;

public class XmlRawBody extends RawBody {
    public XmlRawBody() {
        super(ContentType.APPLICATION_XML);
    }
}
