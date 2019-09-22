package com.example.popularmovies.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.utils.Constants;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private int id;
    private String title;
    private String posterUrl;
    private String overview;
    private double voteAverage;
    private String releaseDate;

    private ImageView imageView_detail;
    private TextView textView_detail_title, textView_detail_overview, textView_detail_avgRate, textView_detail_release;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        title = intent.getStringExtra("original_title");
        posterUrl = intent.getStringExtra("poster_path");
        overview = intent.getStringExtra("overview");
        voteAverage = intent.getDoubleExtra("vote_average", 0);
        releaseDate = intent.getStringExtra("release_date");

        imageView_detail = findViewById(R.id.imageView_detail);
        textView_detail_title = findViewById(R.id.textView_detail_title);
        textView_detail_overview = findViewById(R.id.textView_detail_overview);
        textView_detail_avgRate = findViewById(R.id.textView_detail_avgRate);
        textView_detail_release = findViewById(R.id.textView_detail_release);

        Picasso.get().load(Constants.IMAGE_URL + posterUrl).into(imageView_detail);
        textView_detail_title.setText(title);
        textView_detail_overview.setText(overview);
        textView_detail_avgRate.setText(voteAverage + "   of   10");
        textView_detail_release.setText(releaseDate);

    }
}
