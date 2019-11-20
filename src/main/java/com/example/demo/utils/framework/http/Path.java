package com.example.demo.utils.framework.http;

public class Path {
    private String path;

    private Path(String path) {
        this.path = path;
    }

    public static Path parse(String path) {
        return new Path(path);
    }

    public Path replaceParameter(String name, String value) {
        final String parameterName = "{" + name + "}";
        if (this.path.contains(parameterName)) {
            this.path = this.path.replace(parameterName, value);
        }
        return this;
    }

    public String asPathString() {
        return path;
    }
}
