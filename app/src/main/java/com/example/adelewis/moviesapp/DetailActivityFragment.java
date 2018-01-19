package com.example.adelewis.moviesapp;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    ImageButton button;

    MoviePreference sharedPreferences;
    ArrayList<String> favorites=new ArrayList<String>();
    private String title,overview,date,avgVote;
    static final String Movie_ID="MovieID";
    private String id;
    private ImageView backdrop;
    private String imgurl;
    ReviewAdapter rvAdapter;
    TrailerAdapter trailerAdapter;
    private ArrayList<Reviews>reviews=new ArrayList<Reviews>();
    private ArrayList<Trailer>trailers=new ArrayList<Trailer>();
    private String trailersURL;
    private String reviesURL;
    ListView reviewList;
    ListView trailersList;
    public DetailActivityFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        reviewList=(ListView)rootView.findViewById(R.id.reviews_list);
        trailersList=(ListView)rootView.findViewById(R.id.trailer_list);
        button=(ImageButton)rootView.findViewById(R.id.imgButton);
        Bundle bundle = getArguments();
        Log.v("a3rd",getActivity().getIntent().toString());
        if (getActivity().getIntent().getStringExtra("Mtitle")!=null){
            bundle=getActivity().getIntent().getExtras();
        }
        if(bundle!=null){
            bundle.getString("Mtitle");
            bundle.getString("MbackDrop");
            bundle.getString("Mdate");
            bundle.getString("MoverView");
            bundle.getString("Mvote");
           id= bundle.getString("id");
            sharedPreferences=new MoviePreference();
            favorites=sharedPreferences.getFavorites(getActivity());
                    if(favorites==null){
            favorites=new ArrayList<String>();
            favorites.add(" ");}

        rvAdapter=new ReviewAdapter(DetailActivityFragment.this.getActivity(), R.layout.fragment_detail,reviews);
        reviewList.setAdapter(rvAdapter);
        trailerAdapter=new TrailerAdapter(DetailActivityFragment.this.getActivity(),R.layout.fragment_detail, trailers);
        trailersList.setAdapter(trailerAdapter);
        if(favorites!=null){
            if(favorites.contains(id)){
                button.setImageResource(R.drawable.star_solid);
            }
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (favorites.contains(id)) {
                    sharedPreferences.removeFavorite(getActivity(), id);
                    favorites.remove(id);
                    button.setImageResource(R.drawable.starframe);
                    Toast toast = Toast.makeText(getActivity(), "Removed to favourite", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    sharedPreferences.addToFavorite(getActivity(), id);
                    favorites.add(id);
                    button.setImageResource(R.drawable.star_solid);
                    Toast toast = Toast.makeText(getActivity(), "Added to favourite", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        trailersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trailer gtrailer = trailers.get(position);
                getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(gtrailer.getKey())));
            }
        });


            title=bundle.getString("Mtitle");
            backdrop=(ImageView)rootView.findViewById(R.id.Bk_image);
            overview=bundle.getString("MoverView");
            date=bundle.getString("Mdate");
            avgVote=bundle.getString("Mvote");
            imgurl="http://image.tmdb.org/t/p/w342"+bundle.getString("MbackDrop"); //not done
            Picasso.with(getActivity()).load(imgurl).into(backdrop);
            ((TextView)rootView.findViewById(R.id.detail_title)).setText(title);
            ((TextView)rootView.findViewById(R.id.detail_date)).setText(date);
            ((TextView)rootView.findViewById(R.id.detail_vote_average)).setText(avgVote);
            ((TextView)rootView.findViewById(R.id.detail_overview)).setText(overview);

            reviesURL="http://api.themoviedb.org/3/movie/"+id+"/reviews?api_key=993e627613179f9496ed21de467591b8";
            trailersURL="http://api.themoviedb.org/3/movie/"+id+"/videos?api_key=993e627613179f9496ed21de467591b8";
            RvAPI rev=new RvAPI();
            rev.getReviews(reviesURL);
            rev.get_trailers(trailersURL);
        }




        return rootView;
    }
    public class RvAPI{
        public void getReviews(String url){
            RequestQueue queue= Volley.newRequestQueue(DetailActivityFragment.this.getActivity());
            JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray array = response.getJSONArray("results");
                        for (int i = 0; i < array.length(); i++) {
                            Reviews review=new Reviews();
                            JSONObject jsobj = array.getJSONObject(i);
                            review.setContent(jsobj.getString("content"));
                            review.setReviewer(jsobj.getString("author"));
                            reviews.add(review);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                   // Log.v("List size",reviews.get(0));
                    //rvAdapter.set_reviews(reviews);
                    rvAdapter.notifyDataSetChanged();
                }
                },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(request);
        }
        public void get_trailers(String url){
            RequestQueue queue= Volley.newRequestQueue(DetailActivityFragment.this.getActivity());
            JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray array = response.getJSONArray("results");
                        for (int i = 0; i < array.length(); i++) {
                            Trailer atrailer=new Trailer();
                            JSONObject jsobj = array.getJSONObject(i);
                            String path="https://www.youtube.com/watch?v="+jsobj.getString("key");
                            atrailer.setKey(path);
                            atrailer.setName(jsobj.getString("name"));
                            Log.v("el_path", path);
                            trailers.add(atrailer);
                        }
                        trailerAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(request);
        }
    }
}
