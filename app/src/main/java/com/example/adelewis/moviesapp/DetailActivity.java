package com.example.adelewis.moviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putParcelable(DetailActivityFragment.Movie_ID,
                    getIntent().getParcelableExtra(DetailActivityFragment.Movie_ID));
            getFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, new DetailActivityFragment())
                    .commit();
        }

    }
}
