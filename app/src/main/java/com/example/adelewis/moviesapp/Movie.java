package com.example.adelewis.moviesapp;

/**
 * Created by AdelEwis on 12/29/2015.
 */
public class Movie {
    private String title;
    private String overView;
    private String Date;
    private String VoteAVg;
    private String poster;
    private String backDrop;
    private String ID;

    public Movie(){}
    public Movie(String mtitle,String moverView,String mDate,String mVoteAVG,String mPoster,String mbackDrop,String id){
        this.title=mtitle;
        this.overView=moverView;
        this.Date=mDate;
        this.VoteAVg=mVoteAVG;
        this.poster=mPoster;
        this.backDrop=mbackDrop;
        this.ID=id;
    }
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getVoteAVg() {
        return VoteAVg;
    }

    public void setVoteAVg(String voteAVg) {
        VoteAVg = voteAVg;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackDrop() {
        return backDrop;
    }

    public void setBackDrop(String backDrop) {
        this.backDrop = backDrop;
    }
}
