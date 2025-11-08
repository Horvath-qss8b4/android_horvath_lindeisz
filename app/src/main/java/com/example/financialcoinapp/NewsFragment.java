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

public class NewsFragment extends Fragment {

    private RecyclerView recyclerNews;
    private NewsAdapter adapter;
    private List<FinnhubNews> newsList = new ArrayList<>();
    private static final String API_KEY = "d38ordhr01qthpo0q3igd38ordhr01qthpo0q3j0";

    public NewsFragment() {
        super(R.layout.fragment_news);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerNews = view.findViewById(R.id.recyclerNews);
        recyclerNews.setLayoutManager(new LinearLayoutManager(requireContext()));

        recyclerNews.addItemDecoration(
                new androidx.recyclerview.widget.DividerItemDecoration(
                        requireContext(),
                        androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
                )
        );

        adapter = new NewsAdapter(newsList, item -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, NewsDetailFragment.newInstance(item))
                    .addToBackStack(null)
                    .commit();
        });
        recyclerNews.setAdapter(adapter);

        Button btnToCoins = view.findViewById(R.id.btnToCoins);
        btnToCoins.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new CoinsFragment())
                        .addToBackStack(null)
                        .commit()
        );

        loadNews();
    }

    private void loadNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://finnhub.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FinnhubApi api = retrofit.create(FinnhubApi.class);
        Call<List<FinnhubNews>> call = api.getNews("crypto", API_KEY);

        call.enqueue(new Callback<List<FinnhubNews>>() {
            @Override
            public void onResponse(@NonNull Call<List<FinnhubNews>> call,
                                   @NonNull Response<List<FinnhubNews>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    newsList.clear();
                    newsList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<FinnhubNews>> call, @NonNull Throwable t) {
                // Hiba kezelése
            }
        });
    }
}