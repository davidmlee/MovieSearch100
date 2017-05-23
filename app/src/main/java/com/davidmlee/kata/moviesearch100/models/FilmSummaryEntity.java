package com.davidmlee.kata.moviesearch100.models;

import com.davidmlee.kata.moviesearch100.util.Util;

import org.json.JSONObject;

/**
 * FilmSummaryEntity
 */
public class FilmSummaryEntity {

    private String id;
    private String title;
    private String overview;
    private String posterPath;
    private String releasedate;

    private FilmSummaryEntity() {
    }

    public static FilmSummaryEntity populateFetchableResource(JSONObject jsonEntry) {
        FilmSummaryEntity fe = new FilmSummaryEntity();
        fe.setId(Util.getString(jsonEntry, "id", ""));
        fe.setTitle(Util.getString(jsonEntry, "title", ""));
        fe.setOverview(Util.getString(jsonEntry, "overview", ""));
        fe.setPosterPath(Util.getString(jsonEntry, "poster_path", ""));
        fe.setReleaseDate(Util.getString(jsonEntry, "release_date", ""));
        return fe;
    }

    public String getReleaseDate() {
        return releasedate;
    }

    private void setReleaseDate(String d1) {
        releasedate = d1;
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

    public String getPosterPath() {
        return posterPath;
    }

    private void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
