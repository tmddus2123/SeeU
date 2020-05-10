package com.example.seeu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReadReviewActivity extends AppCompatActivity {

    TextView NameTV;
    TextView AreaTV;
    String User;
    //String Area;
    Button GoReview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_review);

        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();

        User = "See U";
        //Area = "3";
        GoReview = (Button)findViewById(R.id.goreview);

        NameTV = (TextView)findViewById(R.id.UserName);
        //NameTV = (TextView)findViewById(R.id.Area);

        NameTV.setText(User.toString());
       // AreaTV.setText(Area.toString());

        GoReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),WriteReviewActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
