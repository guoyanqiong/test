package com.example.demo.utils.framework.http;

public class QueryParameter {

    private static final String SEPARATOR = "=";

    private final String name;
    private final String value;

    public static QueryParameter parse(String keyAndValue) {
        String[] values = keyAndValue.split(SEPARATOR);
        return new QueryParameter(values[0], values[1]);
    }

    public QueryParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String asQueryString() {
        return name + SEPARATOR + value;
    }
}
