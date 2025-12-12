package com.example.financialcoinapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

// CoinMarketCap API válaszának modellje
public class CmcResponse {

    // Az API "data" tömbje (coin lista)
    @SerializedName("data")
    public List<Coin> data;

    // Egyetlen coin adatai
    public static class Coin {
        public int id;          // Coin egyedi azonosító
        public String name;     // Coin neve (pl. Bitcoin)
        public String symbol;   // Rövidítés (pl. BTC)
        public Quote quote;     // Árfolyam adatok
    }

    // Pénznem szerinti bontás
    public static class Quote {

        // Az API-ban a kulcs nagybetűs: "USD"
        @SerializedName("USD")
        public Usd usd;
    }

    // USD-hez tartozó árfolyam adatok
    public static class Usd {

        // Aktuális ár USD-ben
        @SerializedName("price")
        public Double price;

        // 24 órás százalékos változás
        @SerializedName("percent_change_24h")
        public Double percentChange24h;
    }
}
/*
pontosan illeszkedik a CoinMarketCap JSON struktúrához
Retrofit + Gson automatikusan feltölti
Fragmentben közvetlenül használható (coin.quote.usd.price)
 */