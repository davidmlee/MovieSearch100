package com.davidmlee.kata.moviesearch100.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.davidmlee.kata.moviesearch100.R;
import com.davidmlee.kata.moviesearch100.controller.MainController;
import com.davidmlee.kata.moviesearch100.controller.MovieDetailController;
import com.davidmlee.kata.moviesearch100.core.MyApp;
import com.davidmlee.kata.moviesearch100.models.FilmDetailEntity;

/**
 * Created by davidmlee on 5/14/17.
 */

public class MovieDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        FilmDetailEntity fde = MovieDetailController.getDetail();
        ((TextView)findViewById(R.id.tv_title_field)).setText(fde.getTitle());
        ((TextView)findViewById(R.id.tv_overview_field)).setText(fde.getOverview());
        ((TextView)findViewById(R.id.tv_release_date_field)).setText(fde.getReleaseDate());
        ((TextView)findViewById(R.id.tv_status_field)).setText(fde.getStatus());
        ((TextView)findViewById(R.id.tv_adult_field)).setText(fde.getAdult() ? MyApp.getStrRes(R.string.label_yes) : MyApp.getStrRes(R.string.label_no));
        ((TextView)findViewById(R.id.tv_video_field)).setText(fde.getVideo() ? MyApp.getStrRes(R.string.label_yes) : MyApp.getStrRes(R.string.label_no));
    }
}
