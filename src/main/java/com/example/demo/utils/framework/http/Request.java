package com.example.demo.utils.framework.http;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

public class Request {
    private final HttpUriRequest request;
    private final HttpContext httpContext;

    public Request(HttpUriRequest request, HttpContext httpContext) {
        this.request = request;
        this.httpContext = httpContext;
    }

    public HttpUriRequest getHttpClientRequest() {
        return request;
    }

    public HttpContext getHttpContext() {
        return httpContext;
    }
}
