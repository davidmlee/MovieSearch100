package com.davidmlee.kata.moviesearch100.controller;

import android.content.Intent;

import com.davidmlee.kata.moviesearch100.MovieDetailActivity;
import com.davidmlee.kata.moviesearch100.core.MyApp;
import com.davidmlee.kata.moviesearch100.models.FilmDetailEntity;
import com.davidmlee.kata.moviesearch100.query.QueryResponseCallback;
import com.davidmlee.kata.moviesearch100.query.SearchMovies;
import com.davidmlee.kata.moviesearch100.util.Util;

import org.json.JSONObject;

/**
 * (non-Javadoc)
 * Created by davidmlee on 5/14/17.
 */

public class MovieDetailController {
    static FilmDetailEntity filmDetailEntity;

    static public FilmDetailEntity getDetail() {
        return MovieDetailController.filmDetailEntity;
    }
    /**
     * @param str_id - string to search for the movie list
     */
    static public void queryMovieDetail(final String str_id) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                SearchMovies.sendQueryDetail(str_id, new QueryResponseCallback() {
                    @Override
                    public void onSuccess(String responseBodyString) {
                        JSONObject jsonTop;
                        try {
                            jsonTop = new JSONObject(responseBodyString);
                            FilmDetailEntity fde = new FilmDetailEntity();
                            fde.setId(Util.getString(jsonTop, "id", ""));
                            fde.setTitle(Util.getString(jsonTop, "title", ""));
                            fde.setOverview(Util.getString(jsonTop, "overview", ""));
                            fde.setReleaseDate(Util.getString(jsonTop, "release_date", ""));
                            fde.setStatus(Util.getString(jsonTop, "status", ""));
                            fde.setAdult(Util.getBool(jsonTop, "adult"));
                            fde.setVideo(Util.getBool(jsonTop, "video"));
                            MovieDetailController.setFilmDetailEntity(fde);
                            if (! MyApp.getIsAppBackground()) {
                                Intent i = new Intent(MainController.getMainActivity(), MovieDetailActivity.class);
                                MainController.getMainActivity().startActivity(i);
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

    static private void setFilmDetailEntity(FilmDetailEntity fde) {
        MovieDetailController.filmDetailEntity = fde;
    }
}
