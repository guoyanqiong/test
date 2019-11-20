package com.example.demo.utils.framework.http;

import org.apache.http.HttpEntity;
import org.apache.http.entity.InputStreamEntity;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

public class PostStreamEntityRequestBuilder extends PostRequestBuilder<PostStreamEntityRequestBuilder> {
    private String data = "";

    public PostStreamEntityRequestBuilder(String baseURL) {
        super(baseURL);
    }

    public PostStreamEntityRequestBuilder withParameter(String name, String value) {
        uriBuilder.withQueryParameter(name, value);
        return thisInstance();
    }

    public PostStreamEntityRequestBuilder withData(String data) {
        this.data = data;
        return thisInstance();
    }

    @Override
    protected HttpEntity getPostEntity() {
        try {
            final InputStreamEntity reqEntity = new InputStreamEntity(new ByteArrayInputStream(data.getBytes("utf-8")), -1);
            reqEntity.setContentType("binary/octet-stream");
            reqEntity.setChunked(true);
            return reqEntity;
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unable to create InputStreamEntity for " + data);
        }
    }
}
