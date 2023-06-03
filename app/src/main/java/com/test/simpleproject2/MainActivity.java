package com.test.simpleproject2;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.onesignal.OneSignal;



import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private static final String LAST_LINK_KEY = "last_link";

    private WebView webView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ініціалізувати OneSignal SDK
        OneSignal.initWithContext(this);
        OneSignal.setAppId("ZWE2YjdmNDQtMTI3OC00MmI2LWFkZWQtZGNiN2E4YTU4OWJi");



        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String lastLink = sharedPreferences.getString(LAST_LINK_KEY, null);
        if (lastLink != null) {
            webView.loadUrl(lastLink);
        } else {
            new ApiRequestTask().execute();
        }
    }

    private class ApiRequestTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                URL url = new URL("https://ohmytraff.space/api");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                return responseCode;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return -1;
        }

        @Override
        protected void onPostExecute(Integer responseCode) {
            if (responseCode == 404) {
                // Відкрити гру
                openGame();
            } else {
                // Відкрити посилання у WebView
                String link = "URL";
                openLinkInWebView(link);
            }
        }
    }

    private void openGame() {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }

    private void openLinkInWebView(String link) {
        // Код для відкриття посилання у WebView
        webView.loadUrl(link);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_LINK_KEY, webView.getUrl());
        editor.apply();
    }
}
