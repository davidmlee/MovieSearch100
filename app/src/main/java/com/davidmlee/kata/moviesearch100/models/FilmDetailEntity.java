package com.davidmlee.kata.moviesearch100.models;

import com.davidmlee.kata.moviesearch100.util.Util;

import org.json.JSONObject;

/**
 * Created by davidmlee on 5/14/17.
 */

public class FilmDetailEntity {
    private String id;
    private boolean bAdult;
    private String title;
    private String overview;
    private String releasedate;
    private String status;
    private boolean bVideo;

    private FilmDetailEntity() {
    }

    public static FilmDetailEntity populateFetchableResource(JSONObject jsonEntry) {
        FilmDetailEntity fde = new FilmDetailEntity();
        fde.setId(Util.getString(jsonEntry, "id", ""));
        fde.setTitle(Util.getString(jsonEntry, "title", ""));
        fde.setOverview(Util.getString(jsonEntry, "overview", ""));
        fde.setReleaseDate(Util.getString(jsonEntry, "release_date", ""));
        fde.setStatus(Util.getString(jsonEntry, "status", ""));
        fde.setAdult(Util.getBool(jsonEntry, "adult"));
        fde.setVideo(Util.getBool(jsonEntry, "video"));
        return fde;
    }

    public String getReleaseDate() {
        return releasedate;
    }

    private void setReleaseDate(String d1) {
        releasedate = d1;
    }

    public boolean getAdult() {
        return bAdult;
    }

    private void setAdult(boolean d1) {
        this.bAdult = d1;
    }

    public boolean getVideo() {
        return bVideo;
    }

    private void setVideo(boolean d1) {
        this.bVideo = d1;
    }

    public String getStatus() {
        return status;
    }

    private void setStatus(String d1) {
        this.status = d1;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return this.overview;
    }

    private void setOverview(String overview) {
        this.overview = overview;
    }
}
