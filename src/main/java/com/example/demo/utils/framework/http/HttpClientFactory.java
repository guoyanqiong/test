package com.example.demo.utils.framework.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.net.URI;

public class HttpClientFactory {

	static PoolingHttpClientConnectionManager poolingManager = new PoolingHttpClientConnectionManager();
	static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
	
	static
	{
		poolingManager.setMaxTotal(200);
		poolingManager.setDefaultMaxPerRoute(20);
	}
	
	static CloseableHttpClient getClient()
	{
    	CloseableHttpClient httpClient = HttpClients.custom()
    	        .setDefaultRequestConfig(requestConfig)
    	        .setConnectionManager(poolingManager)
    	        .build();
		return httpClient;
	}
	
	static HttpGet getHttpGet(URI uri)
	{
		HttpGet get = new HttpGet(uri);
		get.setConfig(requestConfig);
		return get;

	}
	
	static HttpPost getHttpPost(URI uri)
	{
		HttpPost post = new HttpPost(uri);
		post.setConfig(requestConfig);
		return post;

	}
	
    private HttpClientFactory() {
        super();
    }

//	public static CloseableHttpClient createThreadedClient() {
    public static CloseableHttpClient createClient() {
		return getClient(); 
//        return new DefaultHttpClient(new ThreadSafeClientConnManager() {{
//            setMaxTotal(20);
//            setDefaultMaxPerRoute(10);
//        }}, httpParams());
        
    }

//	public static AbstractHttpClient createSingleClient() {
//        return new DefaultHttpClient(new SingleClientConnManager(), httpParams());
//    }
//
//    private static HttpParams httpParams() {
//        return new SyncBasicHttpParams()
//                .setParameter(CONNECTION_TIMEOUT, (int) SECONDS.toMillis(2 * 60 * 1000))
//                .setParameter(SO_TIMEOUT, (int) SECONDS.toMillis(2 * 60 * 1000))
//                .setParameter(HANDLE_REDIRECTS, true)
//                .setParameter(ALLOW_CIRCULAR_REDIRECTS, true)
//                .setParameter(HANDLE_AUTHENTICATION, true)
//                .setParameter(USE_EXPECT_CONTINUE, true);
//    }
}
