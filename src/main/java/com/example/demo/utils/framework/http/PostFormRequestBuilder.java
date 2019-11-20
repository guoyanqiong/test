package com.example.demo.utils.framework.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PostFormRequestBuilder extends PostRequestBuilder<PostFormRequestBuilder> {

    private final List<NameValuePair> parameters = new ArrayList<NameValuePair>();

    public PostFormRequestBuilder(String baseURL) {
        super(baseURL);
    }

    public PostFormRequestBuilder withParameter(String name, String value) {
        parameters.add(new BasicNameValuePair(name, value));
        return thisInstance();
    }

    @Override
    protected HttpEntity getPostEntity() {
        try {
            return new UrlEncodedFormEntity(parameters);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
