package com.example.financialcoinapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CmcResponse {
    @SerializedName("data")
    public List<Coin> data;

    public static class Coin {
        public int id;
        public String name;
        public String symbol;
        public Quote quote;
    }

    public static class Quote {
        @SerializedName("USD")
        public Usd usd;
    }

    public static class Usd {
        @SerializedName("price")
        public Double price;

        @SerializedName("percent_change_24h")
        public Double percentChange24h;
    }
}