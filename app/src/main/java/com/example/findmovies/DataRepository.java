package com.example.findmovies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataRepository {
    private ApiService apiService;

    public DataRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://moviesapi.ir/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public LiveData<Resource<MovieApiResponse>> fetchData(int page, MutableLiveData<Resource<MovieApiResponse>> data) {
        data.setValue(Resource.Loading());
        Call<MovieApiResponse> call = apiService.getMovies(page);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(Resource.Success(response.body()));
                } else {
                    data.postValue(Resource.Error("Error : " + response.code(), null));
                }
            }

            @Override
            public void onFailure(Call<MovieApiResponse> call, Throwable t) {
                data.setValue(Resource.Error(t.getMessage(), null));
            }
        });
        return data;
    }
}

