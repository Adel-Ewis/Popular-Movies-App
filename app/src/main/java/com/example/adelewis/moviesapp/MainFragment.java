package com.example.adelewis.moviesapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by AdelEwis on 12/29/2015.
 */
public class MainFragment extends Fragment {
    private String Movie_Pop = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=993e627613179f9496ed21de467591b8";
    private String MOVIE_Vote = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=993e627613179f9496ed21de467591b8";
    private enum sort_options{popularity,avg_vote,favs}
    private sort_options current_sort=sort_options.popularity;
    private ArrayList<Movie>MoviesList=new ArrayList<Movie>();
    private ImageView imgView;
    GridView MoviesGridView;
    MovieAdapter mvAdapter;
    Context mcontext;
    public static boolean mtowpane=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        parseJason pj=new parseJason();
        pj.getData(Movie_Pop);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.optionsmenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.pop&&current_sort!=sort_options.popularity){
            current_sort=sort_options.popularity;
                MoviesList.clear();
                parseJason parsej = new parseJason();
                parsej.getData(Movie_Pop);
                CharSequence text = "Popularity sort is activatied";
                Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
                toast.show();
                return true;
        }
        else if (id==R.id.ratingSort&&current_sort!=sort_options.avg_vote)
        {
            current_sort=sort_options.avg_vote;
            MoviesList.clear();
            parseJason parsej=new parseJason();
            parsej.getData(MOVIE_Vote);
            CharSequence text="Rating sort is activatied";
            Toast toast=Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT);
            toast.show();

            return true;
        }
        else if(id==R.id.favorites&&current_sort!=current_sort.favs){
            current_sort=current_sort.favs;
            MoviesList.clear();
            parseJason parsej=new parseJason();
            MoviePreference preference=new MoviePreference();
            ArrayList<String> favlist=preference.getFavorites(mcontext);
            String url="";
            for(int i=0;i<favlist.size();i++){
                if(!favlist.isEmpty()) {
                     url = "http://api.themoviedb.org/3/movie/" + favlist.get(i) + "?api_key=993e627613179f9496ed21de467591b8";
                    parsej.getsavedmovies(url);
                }
            }
            Toast toast=Toast.makeText(getActivity(),"favorites",Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mcontext=getActivity();
        View rootView=inflater.inflate(R.layout.fragment_main,container,false);
        MoviesGridView=(GridView)rootView.findViewById(R.id.mainGridView);
        mvAdapter=new MovieAdapter(MainFragment.this.getActivity(),R.layout.movielist_item,MoviesList);
        MoviesGridView.setAdapter(mvAdapter);
        MoviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMv = mvAdapter.getItem(position);
                if(mtowpane){
                    DetailActivityFragment detailActivityFragment=new DetailActivityFragment();
                    Bundle bundle=new Bundle();
                   bundle.putString("Mtitle", selectedMv.getTitle());
                    bundle.putString("MbackDrop", selectedMv.getBackDrop());
                    bundle.putString("Mdate", selectedMv.getDate());
                    bundle.putString("MoverView", selectedMv.getOverView());
                    bundle.putString("Mvote", selectedMv.getVoteAVg());
                    bundle.putString("id", selectedMv.getID());
                    detailActivityFragment.setArguments(bundle);
                   // getFragmentManager().beginTransaction().replace(R.id.movie_detail_container,detailActivityFragment).commit();
                    getFragmentManager().beginTransaction().replace(R.id.movie_detail_container,detailActivityFragment).commit();

                }else{
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra("Mtitle", selectedMv.getTitle())
                        .putExtra("MbackDrop", selectedMv.getBackDrop())
                        .putExtra("Mdate", selectedMv.getDate())
                        .putExtra("MoverView", selectedMv.getOverView())
                        .putExtra("Mvote", selectedMv.getVoteAVg())
                        .putExtra("id", selectedMv.getID());
                startActivity(intent);}
            }
        });
        return rootView;
    }
    public  class parseJason{
        public void getData(String url){
            RequestQueue queue= Volley.newRequestQueue(MainFragment.this.getActivity());
            JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray array = response.getJSONArray("results");

                        for (int i = 0; i < array.length(); i++) {
                            Movie movie=new Movie();
                            JSONObject jsobj=array.getJSONObject(i);
                            movie.setDate(jsobj.getString("release_date"));
                            movie.setOverView(jsobj.getString("overview"));
                            final String poster=jsobj.getString("poster_path");
                            movie.setBackDrop(jsobj.getString("backdrop_path"));
                            movie.setTitle(jsobj.getString("original_title"));
                            movie.setVoteAVg(jsobj.getString("vote_average"));
                            movie.setID(jsobj.getString("id"));
                            String ImageUrl="https://image.tmdb.org/t/p/w500"+poster;
                            movie.setPoster(ImageUrl);
                            MoviesList.add(movie);
                            Log.v("a5er 7aga ",movie.getTitle());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mvAdapter.setMovieData(MoviesList);
                   // mvAdapter.notifyDataSetChanged();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("errror","parse jason");

                }
            });
            queue.add(request);
        }
        public void getsavedmovies(String url){

            RequestQueue queue= Volley.newRequestQueue(MainFragment.this.getActivity());
            JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                            Movie movie=new Movie();

                            movie.setDate(response.getString("release_date"));
                            movie.setOverView(response.getString("overview"));
                            final String poster=response.getString("poster_path");
                            movie.setBackDrop(response.getString("backdrop_path"));
                            movie.setTitle(response.getString("original_title"));
                            movie.setVoteAVg(response.getString("vote_average"));
                            movie.setID(response.getString("id"));
                            String ImageUrl="https://image.tmdb.org/t/p/w500"+poster;
                            movie.setPoster(ImageUrl);
                            MoviesList.add(movie);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mvAdapter.setMovieData(MoviesList);
                     mvAdapter.notifyDataSetChanged();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("errror","parse jason");

                }
            });
            queue.add(request);
        }
    }

}

