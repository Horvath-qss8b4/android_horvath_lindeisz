package com.example.financialcoinapp;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FinnhubApi {
    @GET("api/v1/news")
    Call<List<FinnhubNews>> getNews(
            @Query("category") String category,
            @Query("token") String apiKey
    );
}

