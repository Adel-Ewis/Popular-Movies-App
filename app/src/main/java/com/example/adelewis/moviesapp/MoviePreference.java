package com.example.adelewis.moviesapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by AdelEwis on 1/30/2016.
 */
public class MoviePreference {
    public static final String pref_name = "Saved_movies";
    public static final String FAVORITES = "Movies_Favorite";

    public void saveFavorites(Context context, ArrayList<String> favoriteslist) {
        SharedPreferences saved;
        SharedPreferences.Editor editor;
        saved = context.getSharedPreferences(pref_name,
                Context.MODE_PRIVATE);
        editor = saved.edit();
        Gson gson=new Gson();
        String favs=gson.toJson(favoriteslist);
        editor.putString(FAVORITES,favs);
        editor.commit();
    }
    public ArrayList<String> getFavorites(Context context){
            SharedPreferences prefs;
            Gson gson=new Gson();
        ArrayList<String> favorites;
        prefs = context.getSharedPreferences(pref_name,
                Context.MODE_PRIVATE);
        if(prefs.contains(FAVORITES)){
            String data=prefs.getString(FAVORITES,null);
            ArrayList<String> favoritemovs=gson.fromJson(data,ArrayList.class);
            favorites= favoritemovs;
        }else return null;
        return favorites;
        }
    public void addToFavorite(Context context,String mvId){
        ArrayList<String> favs=getFavorites(context);
        if(favs==null){
            favs=new ArrayList<String>();
        }
        if(!(favs.contains(mvId))) {
            favs.add(mvId);
            saveFavorites(context, favs);
        }
    }
    public void removeFavorite(Context context,String mvid){
        ArrayList<String> favs=getFavorites(context);
        if(favs!=null)
        {
            favs.remove(mvid);
            saveFavorites(context,favs);
        }
    }

}
