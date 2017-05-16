/**
 * Created by davidmlee on 5/9/17.
 */
package com.davidmlee.kata.moviesearch100.http;

import okhttp3.Response;

/**
 * HttpResponseCallback
 */
public interface HttpResponseCallback {
    /**
     * @param httpResponse - http Response object
     * @param responseBodyString - (Json) response
     */
    void onSuccess(Response httpResponse, String responseBodyString);

    /**
     * (non-Javadoc)
     *
     * @param httpResponse - http Response object
     * @param ex The Exception
     */
    void onError(Response httpResponse, Exception ex);
}
