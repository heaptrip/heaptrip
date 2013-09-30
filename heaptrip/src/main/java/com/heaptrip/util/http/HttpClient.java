package com.heaptrip.util.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpClient {

	public String doStringGet(String url) {
		String response = null;
		try {
			response = EntityUtils.toString(doRequestGet(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public byte[] doBytePost(String url) {
		byte[] response = null;
		try {
			response = EntityUtils.toByteArray(doRequestPost(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public InputStream doInputStreamPost(String url) {
		InputStream result = null;
		if (url != null && !url.isEmpty()) {
			byte[] dataByUrl = doBytePost(url);
			if (dataByUrl != null)
				result = new ByteArrayInputStream(dataByUrl);
		}
		return result;
	}

	private HttpEntity doRequestGet(String url) {
		HttpEntity entity = null;
		HttpGet httpGet = new HttpGet(url.toString());
		try {
			entity = new DefaultHttpClient().execute(httpGet).getEntity();
		} catch (Exception e) {
		} finally {
			httpGet.releaseConnection();
		}
		return entity;
	}

	private HttpEntity doRequestPost(String url) {
		HttpEntity entity = null;
		HttpPost httpPost = new HttpPost(url.toString());
		try {
			entity = new DefaultHttpClient().execute(httpPost).getEntity();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpPost.releaseConnection();
		}
		return entity;
	}

}
