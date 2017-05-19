package com.davidmlee.kata.moviesearch100.models;

/**
 * Created by davidmlee on 5/18/17.
 */

public class SearchResult {

    private int total_results = -1; // Keep for debug
    private int total_pages = -1;
    private int last_fetched_page_num = -1;
    private String search_text;

    public int getTotalResults() {
        return this.total_results;
    }

    public void setTotalResults(int total_results1) {
        this.total_results = total_results1;
    }

    public int getTotalPages() {
        return this.total_pages;
    }

    public void setTotalPages(int total_pages1) {
        this.total_pages = total_pages1;
    }

    public int getLastFetchedPageNum() {
        return this.last_fetched_page_num;
    }

    public void setLastFetchedPageNum(int last_fetched_page_num1) {
        this.last_fetched_page_num = last_fetched_page_num1;
    }

    public String getSearchText() {
        return this.search_text;
    }

    public void setSearchText(String search_text1) {
        this.search_text = search_text1;
    }
}
