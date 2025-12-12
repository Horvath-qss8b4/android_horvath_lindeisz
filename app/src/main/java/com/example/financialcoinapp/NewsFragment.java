package com.example.financialcoinapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Hírek listáját megjelenítő Fragment
public class NewsFragment extends Fragment {

    // RecyclerView a hírekhez
    private RecyclerView recyclerNews;

    // Adapter a RecyclerView-hoz
    private NewsAdapter adapter;

    // Hírek adatlistája
    private List<FinnhubNews> newsList = new ArrayList<>();

    // Finnhub API kulcs
    private static final String API_KEY = "d38ordhr01qthpo0q3igd38ordhr01qthpo0q3j0";

    // Fragmenthez tartozó layout
    public NewsFragment() {
        super(R.layout.fragment_news);
    }

    // UI inicializálása
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // RecyclerView összekötése és elrendezése
        recyclerNews = view.findViewById(R.id.recyclerNews);
        recyclerNews.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Elválasztó vonalak a listaelemek között
        recyclerNews.addItemDecoration(
                new androidx.recyclerview.widget.DividerItemDecoration(
                        requireContext(),
                        androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
                )
        );

        // Adapter létrehozása kattintáskezeléssel
        adapter = new NewsAdapter(newsList, item -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, NewsDetailFragment.newInstance(item))
                    .addToBackStack(null)
                    .commit();
        });
        recyclerNews.setAdapter(adapter);

        // Gomb a coin lista megnyitásához
        Button btnToCoins = view.findViewById(R.id.btnToCoins);
        btnToCoins.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new CoinsFragment())
                        .addToBackStack(null)
                        .commit()
        );

        // Hírek betöltése API-ból
        loadNews();
    }

    // Finnhub API hívás a hírekhez
    private void loadNews() {

        // Retrofit kliens létrehozása
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://finnhub.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // API interface példányosítása
        FinnhubApi api = retrofit.create(FinnhubApi.class);

        // Hírek lekérése (crypto kategória)
        Call<List<FinnhubNews>> call = api.getNews("crypto", API_KEY);

        // Aszinkron hálózati kérés
        call.enqueue(new Callback<List<FinnhubNews>>() {

            @Override
            public void onResponse(@NonNull Call<List<FinnhubNews>> call,
                                   @NonNull Response<List<FinnhubNews>> response) {

                // Sikeres válasz esetén frissítjük a listát
                if (response.isSuccessful() && response.body() != null) {
                    newsList.clear();
                    newsList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<FinnhubNews>> call, @NonNull Throwable t) {
                // Hálózati vagy API hiba kezelése (itt nincs UI visszajelzés)
            }
        });
    }
}

/*
Összefoglalás:
- RecyclerView + Adapter mintát használ a hírek megjelenítéséhez
- Retrofit segítségével Finnhub API-ból tölti le az adatokat
- Kattintásra részletes hír Fragment nyílik (BackStack használatával)
- Gombbal átváltás lehetséges a coin lista Fragmentre
*/
