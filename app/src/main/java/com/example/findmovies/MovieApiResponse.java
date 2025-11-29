package com.example.findmovies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieApiResponse {
    public static class Metadata {
        @SerializedName("current_page")
        private int page;
        @SerializedName("total_count")
        private int total_results;
        @SerializedName("page_count")
        private int total_pages;

        public int getPage() {
            return page;
        }

        public int getTotal_results() {
            return total_results;
        }

        public int getTotal_pages() {
            return total_pages;
        }

    }

    @SerializedName("data")
    private List<MovieModel> results;

    @SerializedName("metadata")
    private Metadata metadata;

    public int getPage() {
        return metadata.getPage();
    }

    public int getTotal_results() {
        return metadata.getTotal_results();
    }

    public int getTotal_pages() {
        return metadata.getTotal_pages();
    }

    public MovieApiResponse() { }


    public List<MovieModel> getResults() {
        return results;
    }
}
