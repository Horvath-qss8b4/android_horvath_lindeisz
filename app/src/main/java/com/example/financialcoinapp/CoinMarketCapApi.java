package com.example.financialcoinapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
public interface CoinMarketCapApi {
    @Headers({
            "X-CMC_PRO_API_KEY: 7aac0f8c-0342-4583-8114-218519da1b6b",  // <-- ide tedd a kulcsod
            "Accept: application/json"
    })
    @GET("v1/cryptocurrency/listings/latest")
    Call<CmcResponse> getLatestListings(
            @Query("start") int start,
            @Query("limit") int limit,
            @Query("convert") String convert
    );
}