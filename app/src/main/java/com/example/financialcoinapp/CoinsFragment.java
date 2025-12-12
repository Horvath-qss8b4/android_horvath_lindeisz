package com.example.financialcoinapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Fragment, amely a kriptovaluták adatait jeleníti meg
public class CoinsFragment extends Fragment {

    // Ide írjuk ki a coin adatokat
    private TextView txtCoins;

    // A fragmenthez tartozó layout
    public CoinsFragment() {
        super(R.layout.fragment_coins);
    }

    // Akkor fut le, amikor a nézet már létrejött
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TextView összekötése az XML-lel
        txtCoins = view.findViewById(R.id.txtCoins);

        // Vissza gomb
        Button btnBack = view.findViewById(R.id.btnBackToNews);

        // Visszalépés az előző fragmentre
        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        // Coin adatok betöltése
        loadTopCoins();
    }

    // CoinMarketCap API hívás
    private void loadTopCoins() {

        // Retrofit API kliens létrehozása
        CoinMarketCapApi api = ApiClient.getClient().create(CoinMarketCapApi.class);

        // Legfrissebb coin lista lekérése
        Call<CmcResponse> call = api.getLatestListings(1, 10, "USD");

        // Aszinkron hálózati kérés
        call.enqueue(new Callback<CmcResponse>() {

            // Sikeres válasz esetén
            @Override
            public void onResponse(@NonNull Call<CmcResponse> call,
                                   @NonNull Response<CmcResponse> response) {

                // Ellenőrizzük, hogy az API értelmes adatot adott-e vissza
                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().data != null) {

                    // Formázott szöveg összeállítása
                    SpannableStringBuilder sb = new SpannableStringBuilder();

                    // Ár formázása (ezres tagolás, 2 tizedes)
                    DecimalFormat df =
                            (DecimalFormat) DecimalFormat.getNumberInstance(Locale.US);
                    df.applyPattern("#,##0.00");

                    // Maximum 4 coin megjelenítése
                    int count = Math.min(4, response.body().data.size());

                    for (int i = 0; i < count; i++) {

                        // Egy coin az API válaszból
                        CmcResponse.Coin coin = response.body().data.get(i);

                        // Coin neve és rövidítése
                        String nameLine = coin.name + " (" + coin.symbol + ")";

                        // Ár nullra állítva (biztonság)
                        Double price = null;
                        if (coin.quote != null && coin.quote.usd != null)
                            price = coin.quote.usd.price;

                        // Ár szövegként
                        String priceText =
                                price != null ? df.format(price) + " USD" : "—";

                        // Coin név félkövérrel
                        SpannableString nameSpan =
                                new SpannableString(nameLine + "\n");
                        nameSpan.setSpan(
                                new StyleSpan(Typeface.BOLD),
                                0,
                                nameLine.length(),
                                0
                        );

                        // Ár dőlt betűvel
                        SpannableString priceSpan =
                                new SpannableString("Ár: " + priceText + "\n\n");
                        priceSpan.setSpan(
                                new StyleSpan(Typeface.ITALIC),
                                0,
                                priceSpan.length(),
                                0
                        );

                        // Szöveg hozzáfűzése
                        sb.append(nameSpan);
                        sb.append(priceSpan);
                    }

                    // Formázott szöveg megjelenítése
                    txtCoins.setText(sb, TextView.BufferType.SPANNABLE);

                } else {
                    // API hiba vagy üres válasz
                    txtCoins.setText("Hiba: üres vagy hibás válasz az API-tól");
                }
            }

            // Hálózati hiba (pl. nincs internet)
            @Override
            public void onFailure(@NonNull Call<CmcResponse> call,
                                  @NonNull Throwable t) {
                txtCoins.setText("Hálózati hiba: " + t.getMessage());
            }
        });
    }
}
