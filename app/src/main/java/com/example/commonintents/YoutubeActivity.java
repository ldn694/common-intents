package com.example.commonintents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class YoutubeActivity extends AppCompatActivity {
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        searchBar = findViewById(R.id.search_bar);
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                    // Your custom action here
                    searchYouTube();
                    return true;
                }
                return false;
            };
        });
    }


    public void onSearch(View view) {
        searchYouTube();
    }
    public void searchYouTube() {
        String query = searchBar.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEARCH);
        intent.setPackage("com.google.android.youtube");
        intent.putExtra("query", query);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Check if there is a YouTube app installed
        if (intent.resolveActivity(getPackageManager()) == null) {
            // If not, open the search results in a web browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/results?search_query=" + Uri.encode(query)));
        }

        startActivity(intent);
    }
}


