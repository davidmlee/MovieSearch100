package com.davidmlee.kata.moviesearch100.models;

/**
 * film class
 */
public class FilmEntity {

    private String id;
    private String title;
    private String overview;
    private String posterPath;
    private String releasedate;

    public FilmEntity() {
    }

    public String getReleaseDate() {
        return releasedate;
    }

    public void setReleaseDate(String d1) {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
