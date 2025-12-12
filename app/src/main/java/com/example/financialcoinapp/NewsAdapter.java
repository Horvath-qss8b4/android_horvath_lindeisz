package com.example.financialcoinapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// RecyclerView adapter a hírek megjelenítéséhez
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    // Kattintáskezelő interface (fragment / activity implementálja)
    public interface OnItemClickListener {
        void onItemClick(FinnhubNews item);
    }

    // Megjelenítendő hírek listája
    private final List<FinnhubNews> items;

    // Külső kattintáskezelő
    private final OnItemClickListener listener;

    // Adapter konstruktor
    public NewsAdapter(List<FinnhubNews> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    // ViewHolder létrehozása (egy sor kinézete)
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // news_item.xml betöltése egy RecyclerView sorhoz
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        return new NewsViewHolder(view);
    }

    // Adatok hozzárendelése a ViewHolderhez
    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        // Aktuális hír
        FinnhubNews item = items.get(position);

        // Hír címének beállítása
        holder.txtHeadline.setText(item.headline);

        // Kattintás esemény továbbadása
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    // Lista elemszáma
    @Override
    public int getItemCount() {
        return items.size();
    }

    // ViewHolder – egy sor UI elemei
    static class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView txtHeadline;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            // TextView összekötése a layout-tal
            txtHeadline = itemView.findViewById(R.id.txtHeadline);
        }
    }
}
/*
klasszikus RecyclerView.Adapter + ViewHolder minta
kattintás interface-en keresztül megy vissza
adapter nem tud a fragmentről (jó szétválasztás)
 */