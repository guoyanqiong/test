package com.example.demo.utils.framework.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPut;

import java.net.URI;

@SuppressWarnings("rawtypes")
public abstract class PutRequestBuilder<T extends PutRequestBuilder> extends RequestBuilder<T> {

    public PutRequestBuilder(String baseURL) {
        super(URI.create(baseURL));
    }

    protected abstract HttpEntity getPutEntity();

    @Override
    protected HttpPut createHttpUriRequest() {
        final HttpPut httpPut = new HttpPut(uriBuilder.build());
        httpPut.setEntity(getPutEntity());
        return httpPut;
    }
}
