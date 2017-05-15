package com.davidmlee.kata.moviesearch100.models;

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

    public FilmDetailEntity() {
    }

    public String getReleaseDate() {
        return releasedate;
    }

    public void setReleaseDate(String d1) {
        releasedate = d1;
    }

    public boolean getAdult() {
        return bAdult;
    }

    public void setAdult(boolean d1) {
        this.bAdult = d1;
    }

    public boolean getVideo() {
        return bVideo;
    }

    public void setVideo(boolean d1) {
        this.bVideo = d1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String d1) {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
