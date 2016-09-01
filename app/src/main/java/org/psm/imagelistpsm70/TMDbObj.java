package org.psm.imagelistpsm70;

import java.util.ArrayList;

/**
 * Created by PSM on 2016. 9. 1..
 */
public class TMDbObj {
    private boolean adult;
    private String backdrop_path;
    // belongs_to_collection
    private int budget;
    private ArrayList<GenresObj> genres;
    private String homepage;
    private int id;
    private String poster_path;
    private ArrayList<Production_CopaniesObj> production_companies;


    public TMDbObj() {

    }

    class GenresObj {
        int id;
        String name;
    }

    class Production_CopaniesObj {
        String name;
        int id;
    }


    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public ArrayList<GenresObj> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<GenresObj> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public ArrayList<Production_CopaniesObj> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(ArrayList<Production_CopaniesObj> production_companies) {
        this.production_companies = production_companies;
    }
}
