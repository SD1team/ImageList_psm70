package org.psm.imagelistpsm70;

import java.util.ArrayList;

/**
 * Created by PSM on 2016. 9. 1..
 */
public class TMDbNowPlayingObj {
    private int page;
    private ArrayList<ResultsObj> results;
    private DatesObj dates;
    private int total_pages;
    private int total_results;

    public TMDbNowPlayingObj() {

    }

    public class ResultsObj {
        String poster_path;
        boolean adult;
        String overview;
        String release_date;
        int[] genre_ids;
        int id;
        String original_title;
        String original_language;
        String title;
        String backdrop_path;
        float popularity;
        int vote_count;
        boolean video;
        float vote_average;
    }

    public class DatesObj {
        String maximum;
        String minimum;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<ResultsObj> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResultsObj> results) {
        this.results = results;
    }

    public DatesObj getDates() {
        return dates;
    }

    public void setDates(DatesObj dates) {
        this.dates = dates;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
