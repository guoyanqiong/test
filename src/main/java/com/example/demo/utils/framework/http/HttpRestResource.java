package com.example.demo.utils.framework.http;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HttpRestResource {
    private final HttpEntity httpEntity;
    
    public HttpRestResource(HttpEntity httpEntity) {
        this.httpEntity = httpEntity;
    }
    
    public String getContentType() {
        return getHeaderValue(httpEntity.getContentType());
    }

    public String getContentEncoding() {
        return getHeaderValue(httpEntity.getContentEncoding());
    }

    public String toString() {
        final byte[] bytesReceivedInResponse = toByteArray();
        try {
            return new String(bytesReceivedInResponse, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new String(bytesReceivedInResponse);
        }
    }

    public byte[] toByteArray() {
        try {
            return EntityUtils.toByteArray(httpEntity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getHeaderValue(Header header) {
        return header != null ? header.getValue() : null;
    }
}
