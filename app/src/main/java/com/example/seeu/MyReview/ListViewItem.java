package com.example.seeu.MyReview;

public class ListViewItem {
    private String ConcertTV, SeatTV, TextTV;

    public void setConcertTV(String concertTV) {
        ConcertTV = concertTV;
    }
    public void setTextTV(String textTV) {
        TextTV = textTV;
    }
    public void setSeatTV(String seatTV) {
        SeatTV = seatTV;
    }

    public String getConcertTV() {
        return ConcertTV;
    }
    public String getSeatTV() {
        return SeatTV;
    }
    public String getTextTV() {
        return TextTV;
    }
}