package com.example.financialcoinapp;

// Finnhub API egyetlen hír elemének modellje
public class FinnhubNews {

    public String headline;   // Hír címe
    public String summary;    // Rövid összefoglaló
    public String url;        // Hír teljes cikke
    public String source;     // Forrás (pl. Reuters)
    public long datetime;     // Közzététel ideje (UNIX timestamp)
}
/*
JSON → Java modell
Retrofit + Gson automatikusan feltölti
Közvetlenül használható listában / adapterben
 */