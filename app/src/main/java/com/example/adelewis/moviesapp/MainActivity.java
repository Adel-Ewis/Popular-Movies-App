package com.example.adelewis.moviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.movie_detail_container)!=null){
            if (savedInstanceState==null){
            MainFragment.mtowpane=true;
            getFragmentManager().beginTransaction().replace(R.id.movie_detail_container,new DetailActivityFragment())
                    .commit();}
        }
        else
            MainFragment.mtowpane = false;
    }
}