package com.example.seeu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class MyReview extends AppCompatActivity {

    ListView myRList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);

        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();

        myRList = (ListView)findViewById(R.id.myRList);

        /*
        * DB에서 현재 Login한 유저의 글 정보를 불러와서 List 항목 만들기
        * */
    }
}
