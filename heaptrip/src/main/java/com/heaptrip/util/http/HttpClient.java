package com.heaptrip.util.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.heaptrip.util.http.bixo.config.UserAgent;
import com.heaptrip.util.http.bixo.exceptions.BaseFetchException;
import com.heaptrip.util.http.bixo.fetcher.SimpleHttpFetcher;

public class HttpClient {

	public String doStringGet(String url) {
		String response = null;
		HttpGet httpGet = new HttpGet(url.toString());
		try {
			response = EntityUtils.toString(new DefaultHttpClient().execute(httpGet).getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpGet.releaseConnection();
		}
		return response;
	}

	public InputStream doInputStreamGet(String url) {
		InputStream response = null;
		try {
			response = new ByteArrayInputStream(doBrowserGetRequest(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	private byte[] doBrowserGetRequest(String url) {
		byte[] res = null;
		UserAgent userAgent = new UserAgent(
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.220 Safari/535.1");
		try {
			SimpleHttpFetcher fetcher = new SimpleHttpFetcher(userAgent);
			res = fetcher.get(url).getContent();
		} catch (BaseFetchException e) {
			e.printStackTrace();
		}
		return res;
	}

}
