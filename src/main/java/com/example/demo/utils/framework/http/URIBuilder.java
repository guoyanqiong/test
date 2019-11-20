package com.example.demo.utils.framework.http;

import java.net.URI;
import java.net.URISyntaxException;

public class URIBuilder {

	private final String scheme;
	private final String host;
	private final int port;
	private Path path;
	private Query query;

	public URIBuilder(URI baseURI) {
		this.scheme = baseURI.getScheme();
		this.host = baseURI.getHost();
		this.port = baseURI.getPort();
		this.path = Path.parse(baseURI.getPath());
		this.query = Query.parse(baseURI.getQuery());
	}

	public URIBuilder withPath(String path) {
		if (path.contains("?")) {
			final int i = path.indexOf("?");
			this.path = Path.parse(path.substring(0, i));
			this.query.addParameters(Query.parse(path.substring(i + 1, path.length())));
		} else {
			this.path = Path.parse(path);
		}
		return this;
	}

	public URIBuilder withPathParameter(String name, String value) {
		this.path = path.replaceParameter(name, value);
		return this;
	}

	public URIBuilder withQueryParameter(String name, String value) {
		this.query = query.addParameter(name, value);
		return this;
	}

	public URI build() {
		try {
			return new URI(scheme, null, host, port, path.asPathString(), query.asQueryString(), null);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
