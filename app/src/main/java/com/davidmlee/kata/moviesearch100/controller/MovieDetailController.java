package com.davidmlee.kata.moviesearch100.controller;

import android.app.Activity;
import android.content.Intent;

import com.davidmlee.kata.moviesearch100.view.MainActivity;
import com.davidmlee.kata.moviesearch100.view.MovieDetailActivity;
import com.davidmlee.kata.moviesearch100.core.MyApp;
import com.davidmlee.kata.moviesearch100.core.ScreenMap;
import com.davidmlee.kata.moviesearch100.models.FilmDetailEntity;
import com.davidmlee.kata.moviesearch100.query.QueryResponseCallback;
import com.davidmlee.kata.moviesearch100.query.SearchMovies;
import okhttp3.Response;

import org.json.JSONObject;

/**
 * (non-Javadoc)
 * Created by davidmlee on 5/14/17.
 */

public class MovieDetailController {
    static private FilmDetailEntity filmDetailEntity;

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
                            FilmDetailEntity fde = FilmDetailEntity.populateFetchableResource(jsonTop);
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
                    public void onError(Response httpResponse, Exception ex) {
                        String errorString = "";
                        if (httpResponse != null) {
                            errorString = httpResponse.toString();
                        } else if (ex != null) {
                            errorString = ex.getLocalizedMessage();
                        }
                        if (MainController.getMainActivity() != null && ! MyApp.getIsAppBackground()) {
                            Activity myMainActivity = ScreenMap.getCurrentResumedActivity();
                            if (myMainActivity != null &&
                                    myMainActivity.getLocalClassName().contains(MainActivity.class.getSimpleName())) {
                                ((MainActivity)myMainActivity).displayQueryError(errorString);
                            }
                        }
                    }
                });
            }
        }.start();
    }

    static private void setFilmDetailEntity(FilmDetailEntity fde) {
        MovieDetailController.filmDetailEntity = fde;
    }
}
