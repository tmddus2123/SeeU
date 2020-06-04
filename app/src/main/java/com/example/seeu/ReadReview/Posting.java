package com.example.seeu.ReadReview;

public class Posting {

    private String Name;
    private String Seat;
    private String UserID;
    private String Nickname;
    private int Pic;
    private String Text;
    private float Rating;

    public Posting() {}

    public Posting(String Name, String Seat, String UserID, String nickname, int pic, String text, float rating){
        this.Name=Name;
        this.Seat=Seat;
        this.UserID=UserID;
        this.Nickname=nickname;
        this.Pic=pic;
        this.Text=text;
        this.Rating=rating;
    }

    public void setName(String name){
        Name=name;
    }
    public void setSeat(String seat){
        Seat=seat;
    }
    public void setUserID(String userid){
        UserID=userid;
    }
    public void setNickname(String Nick){
        Nickname=Nick;
    }
    public void setPic(int pic){
        Pic=pic;
    }
    public void setText(String text){
        Text=text;
    }
    public void  setRating(float rating){
        Rating=rating;
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
        return Nickname;
    }

    public int getpic() {
        return Pic;
    }

    public String gettext() {
        return Text;
    }

    public float getrating() {
        return Rating;
    }

}