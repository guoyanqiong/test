package com.example.demo.utils.framework.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("rawtypes")
public abstract class RequestBuilder<T extends RequestBuilder> {

    protected final URIBuilder uriBuilder;
    protected final HttpContextBuilder httpContextBuilder;
    private final Collection<NameValuePair> headers = new ArrayList<NameValuePair>();

    public static GetRequestBuilder get(String baseUrl) {
        return new GetRequestBuilder(baseUrl);
    }

    public static PutStreamEntityRequestBuilder putRequest(String baseUrl) {
        return new PutStreamEntityRequestBuilder(baseUrl);
    }

    public static DeleteRequestBuilder deleteRequest(String baseUrl) {
        return new DeleteRequestBuilder(baseUrl);
    }

    public static PostFormRequestBuilder postForm(String baseUrl) {
        return new PostFormRequestBuilder(baseUrl);
    }

    public static PostStreamEntityRequestBuilder postRequest(String baseUrl) {
        return new PostStreamEntityRequestBuilder(baseUrl);
    }

    public RequestBuilder(URI baseURI) {
        uriBuilder = new URIBuilder(baseURI);
        httpContextBuilder = new HttpContextBuilder(baseURI.getHost());
    }

    public T withPath(String path) {
        uriBuilder.withPath(path);
        return thisInstance();
    }

    public T withPathParameter(String name, String value) {
        uriBuilder.withPathParameter(name, value);
        return thisInstance();
    }

    public T withCookies(List<Cookie> cookies) {
        httpContextBuilder.withCookies(cookies);
        return thisInstance();
    }

    public T withContext(HttpContext httpContext) {
        httpContextBuilder.useContext(httpContext);
        return thisInstance();
    }

    public T withHeader(NameValuePair nameValuePair) {
        this.headers.add(nameValuePair);
        return thisInstance();
    }

    public T withHeader(final String name, final String value) {
        this.headers.add(new BasicNameValuePair(name, value));
        return thisInstance();
    }

    public final Request build(){
        return new Request(enrichedWithHeaders(createHttpUriRequest()), httpContextBuilder.build());
    }

    private HttpUriRequest enrichedWithHeaders(final HttpUriRequest httpUriRequest) {
        for (NameValuePair aHeader :this.headers){
            httpUriRequest.addHeader(aHeader.getName(), aHeader.getValue());
        }
        return httpUriRequest;
    }

    protected abstract HttpUriRequest createHttpUriRequest();

    @SuppressWarnings("unchecked")
    protected T thisInstance() {
        return (T) this;
    }
}
