package io.github.swnck.util;

/**
 * Enum representing standard HTTP methods.
 * This enum is used to specify the HTTP method type in various network requests.
 * The supported methods are:
 * - GET: Used to retrieve information from the server.
 * - POST: Used to send data to the server, typically for creation.
 * - DELETE: Used to delete a resource on the server.
 * - PUT: Used to update or create a resource on the server.
 * - PATCH: Used to make partial updates to a resource on the server.
 */
public enum Method {
    GET,
    POST,
    DELETE,
    PUT,
    PATCH,
}
