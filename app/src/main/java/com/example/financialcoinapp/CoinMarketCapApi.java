package com.example.financialcoinapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

// CoinMarketCap REST API leíró interface Retrofithez
public interface CoinMarketCapApi {

    // HTTP fejlécek minden kéréshez
    @Headers({
            // API kulcs azonosításhoz (CoinMarketCap)
            "X-CMC_PRO_API_KEY: 7aac0f8c-0342-4583-8114-218519da1b6b",
            // Válasz formátuma JSON
            "Accept: application/json"
    })

    // GET kérés a legfrissebb coin listához
    @GET("v1/cryptocurrency/listings/latest")
    Call<CmcResponse> getLatestListings(

            // Hányadik elemtől induljon a lista
            @Query("start") int start,

            // Lekért coinok száma
            @Query("limit") int limit,

            // Átváltási pénznem (pl. USD)
            @Query("convert") String convert
    );
}
/*
nem tartalmaz logikát, csak API-leírást
Retrofit ebből automatikusan HTTP hívást csinál
A visszatérési típus (Call<CmcResponse>) köti össze a JSON → modell láncot
 */