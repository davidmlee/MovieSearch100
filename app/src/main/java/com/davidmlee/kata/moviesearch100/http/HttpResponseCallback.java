/**
 * Created by davidmlee on 5/9/17.
 */
package com.davidmlee.kata.moviesearch100.http;

import com.squareup.okhttp.Response;

/**
 * HttpResponseCallback
 */
public interface HttpResponseCallback {
    /**
     * @param response - http Response object
     * @param responseBodyString - (Json) response
     */
    void onSuccess(Response response, String responseBodyString);

    /**
     * (non-Javadoc)
     *
     * @param ex The Exception
     */
    void onError(Exception ex);
}
