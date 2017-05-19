package com.davidmlee.kata.moviesearch100.controller;

import android.app.Activity;
import android.widget.Toast;

import com.davidmlee.kata.moviesearch100.R;
import com.davidmlee.kata.moviesearch100.models.SearchResult;
import com.davidmlee.kata.moviesearch100.view.MainActivity;
import com.davidmlee.kata.moviesearch100.core.MyApp;
import com.davidmlee.kata.moviesearch100.core.ScreenMap;
import com.davidmlee.kata.moviesearch100.models.FilmSummaryEntity;
import com.davidmlee.kata.moviesearch100.query.QueryResponseCallback;
import com.davidmlee.kata.moviesearch100.query.SearchMovies;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * class MainController
 *
 * Created by davidmlee on 5/13/17.
 */

public class MainController {
    private static WeakReference<Activity> weakReferenceMainActivity = null;
    static private SearchResult searchResult = new SearchResult();
    static private ArrayList<FilmSummaryEntity> filmAry = new ArrayList<>(); // List of movies for list adapter
    /**
     * @return filmAry - List of movies for list adapter
     */
    static public ArrayList<FilmSummaryEntity> getFilmList() {
        return filmAry;
    }

    /**
     * @param mainActivity1 movie list activity
     */
    static public void setMainActivity(Activity mainActivity1) {
        weakReferenceMainActivity = new WeakReference<>(mainActivity1);
    }

    /**
     * @return mainActivity1 movie list activity
     */
    static Activity getMainActivity() {
        return weakReferenceMainActivity.get();
    }
    /**
     * @param str_search_text - string to search for the movie list
     */
    static public void searchMovies(final String str_search_text) {
        // New search
        MainController.filmAry.clear(); // Clear the data list for a new search
        MainController.searchResult.setTotalResults(-1);
        MainController.searchResult.setTotalPages(-1);
        MainController.searchResult.setLastFetchedPageNum(-1);
        MainController.searchResult.setSearchText("");
        new Thread() {
            @Override
            public void run() {
                super.run();
                SearchMovies.sendSearch(str_search_text, new QueryResponseCallback() {
                    @Override
                    public void onSuccess(String responseBodyString) {
                        JSONObject jsonTop;
                        JSONArray results;
                        try {
                            jsonTop = new JSONObject(responseBodyString);
                            MainController.searchResult.setTotalResults(jsonTop.getInt("total_results"));
                            MainController.searchResult.setTotalPages(jsonTop.getInt("total_pages"));
                            MainController.searchResult.setLastFetchedPageNum(jsonTop.getInt("page"));
                            MainController.searchResult.setSearchText(str_search_text);
                            results = jsonTop.getJSONArray("results");
                            int numEntries = results.length();
                            FilmSummaryEntity fe;
                            for (int i = 0; i < numEntries; i++) {
                                fe = FilmSummaryEntity.populateFetchableResource(results.getJSONObject(i));
                                MainController.filmAry.add(fe);
                            } // for
                            if (weakReferenceMainActivity.get() != null) {
                                ((MainActivity)weakReferenceMainActivity.get()).resetMovieList();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response httpResponse, Exception ex) {
                        String errorString = "";
                        if (httpResponse != null) {
                            errorString = httpResponse.toString();
                        } else if (ex != null) {
                            errorString = ex.getLocalizedMessage();
                        }
                        if (weakReferenceMainActivity.get() != null && ! MyApp.getIsAppBackground()) {
                            Activity myMainActivity = ScreenMap.getCurrentResumedActivity();
                            if (myMainActivity != null &&
                                    myMainActivity.getLocalClassName().contains(MainActivity.class.getSimpleName())) {
                                ((MainActivity)weakReferenceMainActivity.get()).displayQueryError(errorString);
                            }
                        }
                    }
                });
            }
        }.start();
    }
    /**
     */
    static public void searchMoviesNextPage() {
        int tmp_total_pages = searchResult.getTotalPages();
        int tmp_last_fetched_page_num = searchResult.getLastFetchedPageNum();
        if (searchResult.getLastFetchedPageNum() >= searchResult.getTotalPages()) {
            if (weakReferenceMainActivity.get() != null) {
                ((MainActivity)weakReferenceMainActivity.get()).promptUser(MyApp.getStrRes(R.string.label_movie_list_bottom_reached), Toast.LENGTH_SHORT);
            }
            return;
        }
        // Subsequent search
        new Thread() {
            @Override
            public void run() {
                super.run();
                String pageNumStr = String.valueOf(MainController.searchResult.getLastFetchedPageNum() + 1);
                SearchMovies.sendSearchByPage(MainController.searchResult.getSearchText(), pageNumStr, new QueryResponseCallback() {
                    @Override
                    public void onSuccess(String responseBodyString) {
                        JSONObject jsonTop;
                        JSONArray results;
                        try {
                            jsonTop = new JSONObject(responseBodyString);
                            MainController.searchResult.setLastFetchedPageNum(jsonTop.getInt("page"));
                            results = jsonTop.getJSONArray("results");
                            int numEntries = results.length();
                            FilmSummaryEntity fe;
                            for (int i = 0; i < numEntries; i++) {
                                fe = FilmSummaryEntity.populateFetchableResource(results.getJSONObject(i));
                                MainController.filmAry.add(fe);
                            } // for
                            if (weakReferenceMainActivity.get() != null) {
                                ((MainActivity)weakReferenceMainActivity.get()).appendToMovieList();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response httpResponse, Exception ex) {
                        String errorString = "";
                        if (httpResponse != null) {
                            errorString = httpResponse.toString();
                        } else if (ex != null) {
                            errorString = ex.getLocalizedMessage();
                        }
                        if (weakReferenceMainActivity.get() != null && ! MyApp.getIsAppBackground()) {
                            Activity myMainActivity = ScreenMap.getCurrentResumedActivity();
                            if (myMainActivity != null &&
                                    myMainActivity.getLocalClassName().contains(MainActivity.class.getSimpleName())) {
                                ((MainActivity)weakReferenceMainActivity.get()).displayQueryError(errorString);
                            }
                        }
                    }
                });
            }
        }.start();
    }
}
