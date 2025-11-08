package com.example.financialcoinapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsDetailFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_DESC = "desc";
    private static final String ARG_SRC = "src";
    private static final String ARG_LINK = "link";

    public static NewsDetailFragment newInstance(FinnhubNews item) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, item.headline);
        args.putString(ARG_DESC, item.summary);
        args.putString(ARG_SRC, item.source);
        args.putString(ARG_LINK, item.url);
        args.putLong("datetime", item.datetime);
        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtDesc = view.findViewById(R.id.txtDesc);
        TextView txtSource = view.findViewById(R.id.txtSource);
        TextView txtDate = view.findViewById(R.id.txtDate);
        TextView txtLink = view.findViewById(R.id.txtLink);
        Button btnBack = view.findViewById(R.id.btnBackToList);

        Bundle args = getArguments();
        if (args != null) {
            txtTitle.setText(args.getString(ARG_TITLE));
            txtDesc.setText(args.getString(ARG_DESC));
            txtSource.setText(args.getString(ARG_SRC));
            txtLink.setText(args.getString(ARG_LINK));
            long timestamp = args.getLong("datetime", 0);
            if (timestamp > 0) {
                java.text.SimpleDateFormat sdf =
                        new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
                String formatted = sdf.format(new java.util.Date(timestamp * 1000));
                txtDate.setText(formatted);
            } else {
                txtDate.setText("");
            }
        }

        // 🔹 visszalépés a hírek listájához
        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );
    }
}