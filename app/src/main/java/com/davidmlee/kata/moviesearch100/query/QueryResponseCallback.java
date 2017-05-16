/**
 * (non-Javadoc)
 *
 * Created by davidmlee on 5/9/17.
 */
package com.davidmlee.kata.moviesearch100.query;

import okhttp3.Response;

/**
 * HttpResponseCallback
 */
public interface QueryResponseCallback {
    /**
     * @param responseBodyString The response string
     */
    void onSuccess(String responseBodyString);

    /**
     * @param httpResponse - http Response object
     * @param ex The Exception
     */
    void onError(Response httpResponse, Exception ex);
}
