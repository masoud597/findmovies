package com.example.findmovies;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private DataRepository dataRepository;
    private MutableLiveData<Resource<MovieApiResponse>> apiData;
    private int current_page = 1;
    private int total_pages = 1;

    public MainViewModel() {
        dataRepository = new DataRepository();
        apiData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<MovieApiResponse>> getApiData() {
        if (apiData == null) {
            apiData = new MutableLiveData<>();
        }
        return apiData;
    }

    public void fetchData(int page) {
        if (page <= total_pages && page > 0) {
            current_page = page;
            dataRepository.fetchData(page, apiData);
        }

    }

    public void fetchCurrentPage() {
        fetchData(current_page);
    }

    public void nextPage() {
        fetchData(current_page + 1);
    }

    public void previousPage() {
        fetchData(current_page - 1);
    }

    public int getCurrent_page() {
        return current_page;
    }

    public int getTotal_pages() {
        if (total_pages == 1) {
            if (apiData.getValue() != null && apiData.getValue().getData() != null) {
                total_pages = apiData.getValue().getData().getTotal_pages();
            }
        }
        return total_pages;
    }
}
