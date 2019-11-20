package com.example.demo.utils.framework.http;

import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.client.protocol.ClientContext.COOKIE_STORE;

public class HttpContextBuilder {

    private final String domain;
    private final List<ClientCookie> cookies;
    private HttpContext overrideHttpContext;

    public HttpContextBuilder(String domain) {
        this.domain = domain;
        this.cookies = new ArrayList<ClientCookie>();
    }

    public HttpContextBuilder withCookies(List<Cookie> cookies) {
        this.cookies.addAll(Cookies.toClientCookie(domain, cookies));
        return this;
    }

    public HttpContextBuilder useContext(HttpContext httpContext) {
        this.overrideHttpContext = httpContext;
        return this;
    }

    public HttpContext build() {
        return hasOverriddenHttpContext() ? overrideHttpContext : buildNewCookiesAwareHttpContext();
    }

    private boolean hasOverriddenHttpContext() {
        return null != overrideHttpContext;
    }

    @SuppressWarnings("deprecation")
	private HttpContext buildNewCookiesAwareHttpContext() {
        final HttpContext httpContext = new BasicHttpContext();
        if (hasCookies()) {
            httpContext.setAttribute(COOKIE_STORE, Cookies.toClientCookieStore(cookies));
        }
        return httpContext;
    }

    private boolean hasCookies() {
        return !this.cookies.isEmpty();
    }
}
