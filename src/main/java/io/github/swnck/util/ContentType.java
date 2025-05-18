package io.github.swnck.util;

import lombok.Getter;

@Getter
public enum ContentType {
    TEXT_PLAIN("text/plain"),
    APPLICATION_JSON("application/json"),
    APPLICATION_XML("application/xml"),
    TEXT_HTML("text/html"),
    APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded"),
    MULTIPART_FORM_DATA("multipart/form-data"),
    APPLICATION_JAVASCRIPT("application/javascript"),
    APPLICATION_PDF("application/pdf"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    APPLICATION_OCTET_STREAM("application/octet-stream");

    private final String mimeType;

    ContentType(String mimeType) {
        this.mimeType = mimeType;
    }

    public static ContentType fromString(String mimeType) {
        for (ContentType contentType : ContentType.values()) {
            if (contentType.mimeType.equalsIgnoreCase(mimeType)) {
                return contentType;
            }
        }
        throw new IllegalArgumentException("Unsupported Content-Type: " + mimeType);
    }

    public static String toString(ContentType contentType) {
        return contentType.mimeType;
    }
}