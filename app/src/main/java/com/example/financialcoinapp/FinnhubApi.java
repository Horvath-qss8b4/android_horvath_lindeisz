package com.example.financialcoinapp;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// Finnhub API leíró interface Retrofithez
public interface FinnhubApi {

    // GET kérés a hírek végpontra
    @GET("api/v1/news")
    Call<List<FinnhubNews>> getNews(

            // Hírek kategóriája (pl. general, crypto, forex)
            @Query("category") String category,

            // API token azonosításhoz
            @Query("token") String apiKey
    );
}
/*
csak API-szerződés, nincs benne logika
Retrofit ebből készít HTTP kérést
a válasz FinnhubNews objektumok listája
 */