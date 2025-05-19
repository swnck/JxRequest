# JxRequest

### ❗Under development
**JxRequest** is a lightweight Java HTTP request builder library inspired by the builder pattern and fluent APIs. It simplifies the process of crafting and sending HTTP requests, and supports setting headers, query parameters, CORS, and content types with ease.

---

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Quick Start](#quick-start)
- [Request Types](#request-types)
    - [GET Request](#get-request)
    - [POST Request](#post-request)
- [Headers & Query Parameters](#headers--query-parameters)
- [CORS Support](#cors-support)
- [Content Types](#content-types)
- [Response Handling](#response-handling)
- [License](#license)

---

## Features
- [x] Support for
  - [x] GET requests
  - [x] POST requests
  - [x] PUT requests
  - [x] DELETE requests
  - [x] PATCH requests
- [ ] Proxy configuration
- [ ] Class (entity) mapping
- [ ] Support for body (form-urlencoded, multipart, binary)
- [ ] Retry request many times
- [x] Query parameters 
- [X] Timeout configuration
- [x] CORS support

---

## Installation

To use JxRequest in your project, add the following dependency to your `pom.xml` if you're using Maven:

```xml
<dependency>
    <groupId>io.github.swnck</groupId>
    <artifactId>jxrequest</artifactId>
    <version>1.0.0</version>
</dependency>
```

If you're using Gradle, add the following to your `build.gradle`:

```groovy
dependencies {
    implementation 'io.github.swnck:jxrequest:1.0.0'
}
```
If you're using a different build tool, you can download the JAR file from the [releases page](https://github.com/swnck/JxRequest/releases) and add it to your classpath manually.

---

## Quick Start

```java
import io.github.swnck.JxRequest;
import io.github.swnck.JxResponse;

JxResponse response = JxRequest.get("https://api.example.com/data")
    .setHeader("Authorization", "Bearer token")
    .setQueryParam("limit", "10")
    .send();
```

## Request Types

### GET Request

```java
GetRequest getRequest = JxRequest.get("https://api.example.com/items")
    .setQueryParam("category", "books")
    .setHeader("Accept", "application/json");
```

### POST Request

```java
PostRequest postRequest = JxRequest.post("https://api.example.com/items")
    .setContentType("application/json")
    .setBody("{\"name\": \"Notebook\", \"price\": 9.99}");
```

---

## Headers & Query Parameters

You can easily add headers and query parameters via fluent setters:

```java
request.setHeader("X-Custom-Header", "value");
request.setQueryParam("sort", "asc");
```
You can also add multiple entries using maps:
```java
request.setHeaders(Map.of("Accept", "application/json", "User-Agent", "JxRequest"));
request.setQueryParams(Map.of("page", "2", "limit", "50"));
```
---

## CORS Support

CORS headers can be automatically added to your requests:

```java
Cors cors = new Cors()
    .allowOrigin("*")
    .allowMethods("GET", "POST")
    .allowHeaders("Content-Type", "Authorization")
    .allowCredentials(true);

request.setCors(cors);
```

---

## Content Types

Set the Content-Type of the request:

```java
request.setContentType("application/json");
```

You can also use predefined content types:

```java
request.setContentType(ContentType.APPLICATION_JSON);
```

---

## Response Handling

The send() method returns a JxResponse instance. You can extend this response class to support response body parsing, status code checking, etc.
```java
JxResponse response = request.send();
System.out.println("Status: " + response.getStatusCode());
System.out.println("Body: " + response.getBody());
```

---

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

_Nick Schweizer – Crafted with ❤️ in Java_