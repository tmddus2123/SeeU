package com.example.seeu.ReadReview;

import android.graphics.drawable.Drawable;


public class ListVO{
        private Drawable picture;
        private String Review;
        private String name;

    public Drawable getPic(){
        return picture;
    }
    public void setPic(Drawable picture){
        this.picture=picture;
    }

    public String getReview(){
        return Review;
    }

    public void setReview(String review){
        Review=review;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
}
