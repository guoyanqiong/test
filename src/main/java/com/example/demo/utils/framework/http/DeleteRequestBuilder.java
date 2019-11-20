package com.example.demo.utils.framework.http;

import org.apache.http.client.methods.HttpDelete;

import java.net.URI;

public class DeleteRequestBuilder extends RequestBuilder<DeleteRequestBuilder> {

    public DeleteRequestBuilder(String baseURL) {
        super(URI.create(baseURL));
    }

    public DeleteRequestBuilder withParameter(String name, String value) {
        uriBuilder.withQueryParameter(name, value);
        return thisInstance();
    }

    @Override
    protected HttpDelete createHttpUriRequest() {
        return new HttpDelete(uriBuilder.build());
    }

}
