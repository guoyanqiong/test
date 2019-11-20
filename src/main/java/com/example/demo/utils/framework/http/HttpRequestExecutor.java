package com.example.demo.utils.framework.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

public class HttpRequestExecutor {

	private CloseableHttpClient httpClient;

	private HttpRequestExecutor(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public static HttpRequestExecutor getClient() {
		return new HttpRequestExecutor(HttpClientFactory.createClient());
	}

	// public Response execute(Request request) {
	// try {
	// final HttpResponse httpResponse =
	// httpClient.execute(request.getHttpClientRequest(),
	// request.getHttpContext());
	// return new Response(httpResponse, request.getHttpContext());
	// } catch (IOException e) {
	// throw new RuntimeException(e);
	// }
	// }

	public HttpModel execute(Request request) throws Exception {
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(request.getHttpClientRequest(), request.getHttpContext());
			Response response = new Response(httpResponse, request.getHttpContext());

			HttpModel model = new HttpModel();
			model.setCode(response.getStatusCode());
			model.setBody(response.getOutput());
			return model;

		} catch (Exception e) {
			throw e;
		} finally {
			if (httpResponse != null)
				httpResponse.close();
			// httpClient.close();
		}
	}
}
