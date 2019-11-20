package com.example.demo.utils.framework.http;

import org.apache.http.client.methods.HttpGet;

import java.net.URI;

public class GetRequestBuilder extends RequestBuilder<GetRequestBuilder> {

    public GetRequestBuilder(String baseURL) {
        super(URI.create(baseURL));
    }

    public GetRequestBuilder withParameter(String name, String value) {
        uriBuilder.withQueryParameter(name, value);
        return thisInstance();
    }

    @Override
    protected HttpGet createHttpUriRequest() {
//        return new HttpGet(uriBuilder.build());
    	return HttpClientFactory.getHttpGet(uriBuilder.build());
    }

}
