package com.heaptrip.util.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpClient {

	public String doStringGet(String url) {

		String response = null;

		try {
			response = EntityUtils.toString(doRequestGet(url));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	public byte[] doByteGet(String url) {

		byte[] response = null;

		try {
			response = EntityUtils.toByteArray(doRequestGet(url));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	private HttpEntity doRequestGet(String url) {
		HttpEntity entity = null;

		HttpGet httpGet = new HttpGet(url.toString());

		try {
			entity = new DefaultHttpClient().execute(httpGet).getEntity();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpGet.releaseConnection();
		}

		return entity;

	}

}
