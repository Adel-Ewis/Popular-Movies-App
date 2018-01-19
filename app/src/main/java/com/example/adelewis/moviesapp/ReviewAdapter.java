package com.example.adelewis.moviesapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AdelEwis on 1/29/2016.
 */
public class ReviewAdapter extends ArrayAdapter<Reviews> {
    private ArrayList<Reviews>revs;
    Context rcontext;
    int resourceLayoutID;
    public ReviewAdapter(Context context, int resource,ArrayList<Reviews> reviews) {
        super(context, resource,reviews);
        this.resourceLayoutID=resource;
        this.rcontext=context;
        revs = reviews;
    }

    public void set_reviews(ArrayList<Reviews>reviews){
        this.revs=reviews;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View rootView=convertView;
        viewHolder holder=new viewHolder();
        if(rootView==null){
            LayoutInflater inflater=((Activity)rcontext).getLayoutInflater();
            rootView=inflater.inflate(R.layout.reviews_layout,null,false);
            holder.reviewer=(TextView)rootView.findViewById(R.id.reviewer);
            holder.review=(TextView)rootView.findViewById(R.id.detail_review);
            rootView.setTag(holder);
        }
        else {
            holder=(viewHolder)rootView.getTag();
        }
        holder.reviewer.setText(revs.get(position).getContent());
        holder.review.setText(revs.get(position).getReviewer());
        return rootView;
    }
    static class viewHolder{
        TextView review;
        TextView reviewer;
    }
}
