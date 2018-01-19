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
public class TrailerAdapter extends ArrayAdapter<Trailer> {
    private ArrayList<Trailer>TList;
    Context mcontext;
    int resourceLayoutID;
    public TrailerAdapter(Context context, int resource,ArrayList<Trailer>trailers) {
        super(context, resource);
        this.mcontext=context;
        this.resourceLayoutID=resource;
        this.TList=trailers;
    }
    public void set_trailers(ArrayList<Trailer>trailers){this.TList=trailers;notifyDataSetChanged();}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView=convertView;
        viewHolder holder=new viewHolder();
        if(rootView==null){
            LayoutInflater inflater=((Activity)mcontext).getLayoutInflater();
            rootView=inflater.inflate(R.layout.trailerlist_item,null,false);
            holder.name=(TextView)rootView.findViewById(R.id.trilerTitle);
            rootView.setTag(holder);
        }else {
            holder=(viewHolder)rootView.getTag();
        }
        holder.name.setText(TList.get(position).getName());
        return rootView;
    }
    static class viewHolder{
        TextView name;
    }

    @Override
    public int getCount() {
        return TList.size();
    }
}
