package io.github.swnck.util;

import lombok.Getter;

/**
 * Enum defining various commonly used content types (MIME types).
 * Each constant in this enum represents a specific media type used to describe the nature and format
 * of information being transferred over the web, often in HTTP request or response headers.
 * <p>
 * The enum provides predefined MIME types such as:
 * - text/plain: Plain text.
 * - application/json: JSON formatted data.
 * - application/xml: XML formatted data.
 * - text/html: HTML formatted data.
 * - application/x-www-form-urlencoded: Form data typically encoded for HTTP POST requests.
 * - multipart/form-data: Multipart data used for file uploads.
 * - application/javascript: JavaScript content.
 * - application/pdf: PDF documents.
 * - image/jpeg: JPEG image.
 * - image/png: PNG image.
 * - application/octet-stream: Binary data.
 * <p>
 * It includes a utility method to convert a ContentType to its corresponding MIME string.
 */
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

    /**
     * Represents the MIME type associated with the content type defined by the enum constant.
     * MIME type is a standard identifier used to indicate the nature and format of a document,
     * file, or other piece of data being transmitted on the internet.
     * <p>
     * This variable is immutable and is assigned during the initialization of each enum constant.
     */
    private final String mimeType;

    /**
     * Constructs a new ContentType enum constant with the specified MIME type.
     *
     * @param mimeType the MIME type string associated with the content type
     */
    ContentType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Converts the given ContentType to its MIME string representation.
     *
     * @param contentType the ContentType to convert to a string
     * @return the MIME type string associated with the given ContentType
     */
    public static String toString(ContentType contentType) {
        return contentType.mimeType;
    }
}