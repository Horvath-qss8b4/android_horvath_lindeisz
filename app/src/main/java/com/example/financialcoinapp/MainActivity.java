package com.example.financialcoinapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

// Az alkalmazás fő Activity-je
public class MainActivity extends AppCompatActivity {

    // Activity indulásakor fut le
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activity layout betöltése
        setContentView(R.layout.activity_main);

        // Első fragment betöltése a konténerbe
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new NewsFragment())
                .commit();
    }
}
/*
belépési pont az apphoz
csak a fragmenteket kezeli
a fragment_container egy FrameLayout az XML-ben
 */