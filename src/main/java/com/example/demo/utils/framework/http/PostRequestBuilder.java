package com.example.demo.utils.framework.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;

import java.net.URI;

@SuppressWarnings("rawtypes")
public abstract class PostRequestBuilder<T extends PostRequestBuilder> extends RequestBuilder<T> {

	public PostRequestBuilder(String baseURL) {
		super(URI.create(baseURL));
	}

	protected abstract HttpEntity getPostEntity();

	@Override
	protected HttpPost createHttpUriRequest() {
		// final HttpPost httpPost = new HttpPost(uriBuilder.build());
		HttpPost httpPost = HttpClientFactory.getHttpPost(uriBuilder.build());
		httpPost.setEntity(getPostEntity());
		return httpPost;
	}
}
