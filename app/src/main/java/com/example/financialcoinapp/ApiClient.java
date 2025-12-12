package com.example.financialcoinapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    // API alap URL – minden végpont ehhez adódik hozzá
    private static final String BASE_URL = "https://pro-api.coinmarketcap.com/";

    // Egyetlen Retrofit példány (Singleton)
    private static Retrofit retrofit = null;

    // Központi belépési pont az API-hoz
    public static Retrofit getClient() {

        // Csak egyszer hozzuk létre
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    // API alapcím beállítása
                    .baseUrl(BASE_URL)

                    // JSON → Java objektum átalakítás (Gson)
                    .addConverterFactory(GsonConverterFactory.create())

                    // Retrofit példány elkészítése
                    .build();
        }

        // Meglévő vagy frissen létrehozott kliens visszaadása
        return retrofit;
    }
}
