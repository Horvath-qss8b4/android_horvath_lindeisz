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

public class CoinsFragment extends Fragment {

    private TextView txtCoins;

    public CoinsFragment() {
        super(R.layout.fragment_coins);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtCoins = view.findViewById(R.id.txtCoins);
        Button btnBack = view.findViewById(R.id.btnBackToNews);

        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        loadTopCoins();
    }

    private void loadTopCoins() {
        CoinMarketCapApi api = ApiClient.getClient().create(CoinMarketCapApi.class);
        Call<CmcResponse> call = api.getLatestListings(1, 10, "USD");

        call.enqueue(new Callback<CmcResponse>() {
            @Override
            public void onResponse(@NonNull Call<CmcResponse> call, @NonNull Response<CmcResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().data != null) {
                    SpannableStringBuilder sb = new SpannableStringBuilder();
                    DecimalFormat df = (DecimalFormat) DecimalFormat.getNumberInstance(Locale.US);
                    df.applyPattern("#,##0.00");

                    int count = Math.min(4, response.body().data.size());
                    for (int i = 0; i < count; i++) {
                        CmcResponse.Coin coin = response.body().data.get(i);

                        String nameLine = coin.name + " (" + coin.symbol + ")";
                        Double price = null;
                        if (coin.quote != null && coin.quote.usd != null) price = coin.quote.usd.price;

                        String priceText = price != null ? df.format(price) + " USD" : "—";

                        // Name (bold)
                        SpannableString nameSpan = new SpannableString(nameLine + "\n");
                        nameSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, nameLine.length(), 0);

                        // Price (italic)
                        SpannableString priceSpan = new SpannableString("Ár: " + priceText + "\n\n");
                        priceSpan.setSpan(new StyleSpan(Typeface.ITALIC), 0, priceSpan.length(), 0);

                        sb.append(nameSpan);
                        sb.append(priceSpan);
                    }

                    txtCoins.setText(sb, TextView.BufferType.SPANNABLE);
                } else {
                    txtCoins.setText("Hiba: üres vagy hibás válasz az API-tól");
                }
            }

            @Override
            public void onFailure(@NonNull Call<CmcResponse> call, @NonNull Throwable t) {
                txtCoins.setText("Hálózati hiba: " + t.getMessage());
            }
        });
    }
}