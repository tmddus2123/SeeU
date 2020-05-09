package com.example.seeu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ConcertActivity extends AppCompatActivity {
    Button reviewBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reviewBtn = (Button) findViewById(R.id.btn1);

       /* reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ReadReviewActivity.class);
                startActivity(intent);
            }
        });*/

    }
}
