package com.example.seeu.ReadReview;

public class Posting {

    private String Name;
    private String Seat;
    private String UserID;
    private String nickname;
    private int pic;
    private String text;
    private float rating;

    public Posting() {}

    public Posting(String Name,String Seat, String UserID,String nickname, int pic, String text, float rating) {
        this.Name=Name;
        this.Seat=Seat;
        this.UserID=UserID;
        this.nickname=nickname;
        this.pic=pic;
        this.text=text;
        this.rating=rating;
    }
    public String getName(){
        return Name;
    }

    public String getSeat() {
        return Seat;
    }

    public  String getUserID() {
        return UserID;
    }

    public String getNickname() {
        return nickname;
    }

    public int getpic() {
        return pic;
    }

    public String gettext() {
        return text;
    }

    public float getrating() {
        return rating;
    }

}