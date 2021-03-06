package com.davidmlee.kata.moviesearch100.query;

import com.davidmlee.kata.moviesearch100.core.Contants;
import com.davidmlee.kata.moviesearch100.http.HttpRequest;
import com.davidmlee.kata.moviesearch100.http.HttpResponseCallback;
import okhttp3.Response;

import static com.davidmlee.kata.moviesearch100.core.Contants.BASE_URL;

/**
 * (non-Javadoc)
 *
 * Created by davidmlee on 5/13/17.
 */
public class SearchMovies {
    /**
     * @param searchStr The string to search
     * @param queryResponseCallback The callback object for return
     */
    public static void sendSearch(String searchStr, final QueryResponseCallback queryResponseCallback) {
        // https://api.themoviedb.org/3/search/movie?api_key=xxx&query=Frank
        String urlStr = BASE_URL + "search/movie" + "?api_key=" + Contants.API_KEY + "&query=" + searchStr;
        HttpRequest.sendResquest(urlStr, new HttpResponseCallback() {
            @Override
            public void onSuccess(Response response, String responseBodyString) {
                queryResponseCallback.onSuccess(responseBodyString);
            }

            @Override
            public void onError(Response response, Exception ex) {
                if (ex != null) {
                    ex.printStackTrace();
                }
                queryResponseCallback.onError(response, ex);
            }
        });
    }
    /**
     * @param searchStr The string to search
     * @param pageNumStr The page to get (1 based)
     * @param queryResponseCallback The callback object for return
     */
    public static void sendSearchByPage(String searchStr, String pageNumStr, final QueryResponseCallback queryResponseCallback) {
        // https://api.themoviedb.org/3/search/movie?api_key=xxx&query=Frank
        String urlStr = BASE_URL + "search/movie" + "?api_key=" + Contants.API_KEY + "&query=" + searchStr + "&page=" + pageNumStr;
        HttpRequest.sendResquest(urlStr, new HttpResponseCallback() {
            @Override
            public void onSuccess(Response response, String responseBodyString) {
                queryResponseCallback.onSuccess(responseBodyString);
            }

            @Override
            public void onError(Response response, Exception ex) {
                if (ex != null) {
                    ex.printStackTrace();
                }
                queryResponseCallback.onError(response, ex);
            }
        });
    }
    //https://api.themoviedb.org/3/search/movie?&query=Betsy&api_key=39c128e1fdd7924971c0861760885501
    //        http://api.themoviedb.org/3/movie/550?api_key=39c128e1fdd7924971c0861760885501

    /**
     * @param movieId The movie id
     * @param queryResponseCallback The callback object for return
     */
    public static void sendQueryDetail(String movieId, final QueryResponseCallback queryResponseCallback) {
        // https://api.themoviedb.org/3/movie/550?api_key=xxx
        String urlStr = BASE_URL + "movie/" + movieId + "?api_key=" + Contants.API_KEY;
        HttpRequest.sendResquest(urlStr, new HttpResponseCallback() {
            @Override
            public void onSuccess(Response response, String responseBodyString) {
                queryResponseCallback.onSuccess(responseBodyString);
            }

            @Override
            public void onError(Response response, Exception ex) {
                if (ex != null) {
                    ex.printStackTrace();
                }
                queryResponseCallback.onError(response, ex);
            }
        });
    }
}
