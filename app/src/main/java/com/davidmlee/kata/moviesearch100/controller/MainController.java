package com.davidmlee.kata.moviesearch100.controller;

import android.app.Activity;

import com.davidmlee.kata.moviesearch100.MainActivity;
import com.davidmlee.kata.moviesearch100.core.MyApp;
import com.davidmlee.kata.moviesearch100.core.ScreenMap;
import com.davidmlee.kata.moviesearch100.models.FilmEntity;
import com.davidmlee.kata.moviesearch100.query.QueryResponseCallback;
import com.davidmlee.kata.moviesearch100.query.SearchMovies;
import com.davidmlee.kata.moviesearch100.util.Util;

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

    /**
     * @param str_search_text - string to search for the movie list
     */
    static public void searchMovies(final String str_search_text) {
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
                            results = jsonTop.getJSONArray("results");
                            int numEntries = results.length();
                            JSONObject jsonEntry;
                            FilmEntity fe;
                            MainController.filmAry.clear();
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
                            if (weakReferenceMainActivity.get() != null && ! MyApp.getIsAppBackground()) {
                                Activity myMainActivity = ScreenMap.getCurrentResumedActivity();
                                if (myMainActivity != null &&
                                    myMainActivity.getLocalClassName().contains(MainActivity.class.getSimpleName())) {
                                    ((MainActivity)weakReferenceMainActivity.get()).refreshMovieList();
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception ex) {

                    }
                });
            }
        }.start();
    }
}
