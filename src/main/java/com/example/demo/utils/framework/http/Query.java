package com.example.demo.utils.framework.http;

import java.util.ArrayList;
import java.util.List;

public class Query {

    private static final String SEPARATOR = "&";
    private final List<QueryParameter> parameters;

    public static Query parse(String query) {
        final List<QueryParameter> parameters = new ArrayList<QueryParameter>();
        if (query != null && query.length()>=1) {
            for (String parameter : query.split(SEPARATOR)) {
                parameters.add(QueryParameter.parse(parameter));
            }
        }
        return new Query(parameters);
    }

    public Query(List<QueryParameter> parameters) {
        this.parameters = parameters;
    }

    public Query addParameter(String name, String value) {
        this.parameters.add(new QueryParameter(name, value));
        return this;
    }

    public void addParameters(Query otherQuery) {
        this.parameters.addAll(otherQuery.parameters);
    }

    public String asQueryString() {
        final StringBuilder queryString = new StringBuilder();
        boolean first = true;
        for (QueryParameter parameter : parameters) {
            if (first) {
                queryString.append(parameter.asQueryString());
                first = false;
            } else {
                queryString.append(SEPARATOR).append(parameter.asQueryString());
            }
        }
        return queryString.length() == 0 ? null : queryString.toString();
    }
}
