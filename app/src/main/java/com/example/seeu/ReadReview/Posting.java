package com.example.seeu.ReadReview;

public class Posting {

    //private String SeatID;
    private String UserID;
    private int Picture;
    private String Text;
    private float Ratingbar;

    public Posting() {}

    public Posting(String UserID, int Picture, String Text, float Ratingbar) {
        this.UserID=UserID;
        this.Picture=Picture;
        this.Text=Text;
        this.Ratingbar=Ratingbar;
    }

  /*public String getSeatID() {
        return SeatID;
    }*/

    public  String getUserID() {
        return UserID;
    }

    public int getPicture() {
        return Picture;
    }

    public String getText() {
        return Text;
    }

    public float getRatingbar() {
        return Ratingbar;
    }

}
