package com.example.adelewis.moviesapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by AdelEwis on 12/29/2015.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    ArrayList<Movie> movieList=new ArrayList<Movie>();
    Context mContext;
    int resourceLayoutID;
    public MovieAdapter(Context context, int resource, ArrayList<Movie> MovieDataList) {
        super(context, resource, MovieDataList);
        this.movieList=MovieDataList;
        this.mContext=context;
        this.resourceLayoutID=resource;
    }

    public void setMovieData(ArrayList<Movie>mlist){this.movieList=mlist;
    notifyDataSetChanged();}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder=new viewHolder();
        View rootView=convertView;
        if(rootView==null){
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            rootView = inflater.inflate(resourceLayoutID,parent,false);
            holder.titleTextView=(TextView)rootView.findViewById(R.id.detail_title);
            holder.mDate=(TextView)rootView.findViewById(R.id.detail_date);
            holder.mVoteAVG=(TextView)rootView.findViewById(R.id.detail_vote_average);
            holder.moveView=(TextView)rootView.findViewById(R.id.detail_overview);
            holder.mBkImage=(ImageView)rootView.findViewById(R.id.Bk_image);
           holder.mPoster=(ImageView)rootView.findViewById(R.id.movie_poster_image);
            rootView.setTag(holder);//set all data above
        }
        else{
            holder=(viewHolder)rootView.getTag();
        }
        Movie movie=movieList.get(position);
        Picasso.with(mContext).load(movie.getPoster()).into(holder.mPoster);
        return rootView;
    }
    static  class viewHolder{
        TextView titleTextView;
        TextView moveView;
        TextView mDate;
        TextView mVoteAVG;
        ImageView mPoster;
        ImageView mBkImage;
    }
}
