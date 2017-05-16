package com.davidmlee.kata.moviesearch100.controller;

import android.app.Activity;

import com.davidmlee.kata.moviesearch100.MainActivity;
import com.davidmlee.kata.moviesearch100.core.MyApp;
import com.davidmlee.kata.moviesearch100.core.ScreenMap;
import com.davidmlee.kata.moviesearch100.models.FilmEntity;
import com.davidmlee.kata.moviesearch100.query.QueryResponseCallback;
import com.davidmlee.kata.moviesearch100.query.SearchMovies;
import com.davidmlee.kata.moviesearch100.util.Util;
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

    static private ArrayList<FilmEntity> filmAry = new ArrayList<>(); // List of movies for list adapter
    static private int total_results = -1;
    static private int total_pages = -1;
    static private int last_fetched_page_num = -1;
    static private String search_text;

    /**
     * @return filmAry - List of movies for list adapter
     */
    static public ArrayList<FilmEntity> getFilmList() {
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
    static public Activity getMainActivity() {
        return weakReferenceMainActivity.get();
    }

    static public int getTotalResults() {
        return MainController.total_results;
    }

    static public void setTotalResults(int total_results_in) {
        MainController.total_results = total_results_in;
    }

    static public int getTotalPages() {
        return MainController.total_pages;
    }

    static public void setTotalPages(int total_pages_in) {
        MainController.total_pages = total_pages_in;
    }

    static public int getLastFetchedPageNum() {
        return MainController.last_fetched_page_num;
    }

    static public void setLastFetchedPageNum(int last_fetched_page_num_in) {
        MainController.last_fetched_page_num = last_fetched_page_num_in;
    }
    static public String getSearchText() {
        return MainController.search_text;
    }

    static public void setSearchText(String search_text_in) {
        MainController.search_text = search_text_in;
    }
    /**
     * @param str_search_text - string to search for the movie list
     */
    static public void searchMovies(final String str_search_text) {
        // New search
        MainController.filmAry.clear(); // Clear the data list for a new search
        MainController.setTotalResults(-1);
        MainController.setTotalPages(-1);
        MainController.setLastFetchedPageNum(-1);
        MainController.setSearchText("");
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
                            MainController.setTotalResults(jsonTop.getInt("total_results"));
                            MainController.setTotalPages(jsonTop.getInt("total_pages"));
                            MainController.setLastFetchedPageNum(jsonTop.getInt("page"));
                            MainController.setSearchText(str_search_text);
                            results = jsonTop.getJSONArray("results");
                            int numEntries = results.length();
                            JSONObject jsonEntry;
                            FilmEntity fe;
                            for (int i = 0; i < numEntries; i++) {
                                jsonEntry = results.getJSONObject(i);
                                fe = new FilmEntity();
                                fe.setId(Util.getString(jsonEntry, "id", ""));
                                fe.setTitle(Util.getString(jsonEntry, "title", ""));
                                fe.setOverview(Util.getString(jsonEntry, "overview", ""));
                                fe.setPosterPath(Util.getString(jsonEntry, "poster_path", ""));
                                fe.setReleaseDate(Util.getString(jsonEntry, "release_date", ""));
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
        int tmp_total_pages = total_pages;
        int tmp_last_fetched_page_num = last_fetched_page_num;
        if (getLastFetchedPageNum() >= getTotalPages()) {
            return;
        }
        // Subsequent search
        new Thread() {
            @Override
            public void run() {
                super.run();
                String pageNumStr = String.valueOf(MainController.getLastFetchedPageNum() + 1);
                SearchMovies.sendSearchByPage(MainController.getSearchText(), pageNumStr, new QueryResponseCallback() {
                    @Override
                    public void onSuccess(String responseBodyString) {
                        JSONObject jsonTop;
                        JSONArray results;
                        try {
                            jsonTop = new JSONObject(responseBodyString);
                            MainController.setLastFetchedPageNum(jsonTop.getInt("page"));
                            results = jsonTop.getJSONArray("results");
                            int numEntries = results.length();
                            JSONObject jsonEntry;
                            FilmEntity fe;
                            for (int i = 0; i < numEntries; i++) {
                                jsonEntry = results.getJSONObject(i);
                                fe = new FilmEntity();
                                fe.setId(Util.getString(jsonEntry, "id", ""));
                                fe.setTitle(Util.getString(jsonEntry, "title", ""));
                                fe.setOverview(Util.getString(jsonEntry, "overview", ""));
                                fe.setPosterPath(Util.getString(jsonEntry, "poster_path", ""));
                                fe.setReleaseDate(Util.getString(jsonEntry, "release_date", ""));
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
