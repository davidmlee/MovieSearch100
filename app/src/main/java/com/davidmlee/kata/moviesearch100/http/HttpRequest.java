/**
 * HttpRequest.java
 * Created by davidmlee on 5/13/17.
 */
package com.davidmlee.kata.moviesearch100.http;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * class HttpRequest
 */
public class HttpRequest {
	synchronized static public void sendResquest(String url, HttpResponseCallback httpResponseCallback) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(url)
				.build();
		Call call = client.newCall(request);
		Response response;
		String responseBodyStr;
		try {
			response = call.execute();
			if (response.isSuccessful()) {
                responseBodyStr = response.body().string();
				httpResponseCallback.onSuccess(response, responseBodyStr);
			} else {
				httpResponseCallback.onError(null);
			}
        } catch (Exception ex) {
            ex.printStackTrace();
            httpResponseCallback.onError(ex);
		}
	}
}
