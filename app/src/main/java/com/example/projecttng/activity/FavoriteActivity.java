package com.example.projecttng.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projecttng.R;

public class FavoriteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle("Yêu thích");
    }
}
