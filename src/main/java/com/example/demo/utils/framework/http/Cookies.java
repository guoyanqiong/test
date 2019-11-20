package com.example.demo.utils.framework.http;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.ArrayList;
import java.util.List;

public class Cookies {

    public static CookieStore toClientCookieStore(List<ClientCookie> cookies) {
        final CookieStore cookieStore = new BasicCookieStore();
        for (final ClientCookie cookie : cookies) {
            cookieStore.addCookie(cookie);
        }
        return cookieStore;
    }

    public static List<ClientCookie> toClientCookie(String domain, List<Cookie> cookies) {
        final List<ClientCookie> basicClientCookies = new ArrayList<ClientCookie>();
        for (final Cookie cookie : cookies) {
            basicClientCookies.add(toClientCookie(domain, cookie));
        }
        return basicClientCookies;
    }

    public static ClientCookie toClientCookie(String domain, Cookie cookie) {
        final BasicClientCookie basicClientCookie = new BasicClientCookie(cookie.getName(), cookie.getValue());
        basicClientCookie.setDomain(toCookieDomain(domain, cookie));
        basicClientCookie.setPath(cookie.getPath());
        basicClientCookie.setSecure(cookie.isSecure());
        return basicClientCookie;
    }

    private static String toCookieDomain(String domain, Cookie cookie) {
        return cookie.getDomain() == null ? domain : cookie.getDomain();
    }
}
