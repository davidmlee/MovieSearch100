/**
 * (non-Javadoc)
 *
 * Created by davidmlee on 5/9/17.
 */
package com.davidmlee.kata.moviesearch100.query;

/**
 * HttpResponseCallback
 */
public interface QueryResponseCallback {
    /**
     * @param responseBodyString The response string
     */
    void onSuccess(String responseBodyString);

    /**
     * @param ex The Exception
     */
    void onError(Exception ex);
}
