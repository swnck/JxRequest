package io.github.swnck;

import io.github.swnck.request.*;
import lombok.Getter;

/**
 * Provides static factory methods to generate various types of HTTP requests.
 * This class simplifies the creation of requests like GET, POST, PUT, PATCH, and DELETE,
 * with or without URLs, providing flexibility for diverse use cases.
 * Each method returns a specific request object tailored to the HTTP method being invoked.
 */
@Getter
public class JxRequest {

    public static GetRequest get(String url) {
        return new GetRequest(url);
    }

    public static GetRequest get() {
        return new GetRequest();
    }


    public static PostRequest post(String url) {
        return new PostRequest(url);
    }

    public static PostRequest post() {
        return new PostRequest();
    }


    public static DeleteRequest delete(String url) {
        return new DeleteRequest(url);
    }

    public static DeleteRequest delete() {
        return new DeleteRequest();
    }


    public static PatchRequest patch(String url) {
        return new PatchRequest(url);
    }

    public static PatchRequest patch() {
        return new PatchRequest();
    }


    public static PutRequest put(String url) {
        return new PutRequest(url);
    }

    public static PutRequest put() {
        return new PutRequest();
    }
}
