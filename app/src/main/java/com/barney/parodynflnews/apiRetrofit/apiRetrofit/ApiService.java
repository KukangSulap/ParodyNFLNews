package com.barney.parodynflnews.apiRetrofit.apiRetrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("top-headlines?sources=nfl-news&apiKey=035b4223382c4fea90152d57254f2613")
    Call<com.barney.parodynflnews.model.ResponseNFL> apiService();

}
