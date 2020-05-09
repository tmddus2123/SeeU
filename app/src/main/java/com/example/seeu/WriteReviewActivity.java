package com.example.seeu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WriteReviewActivity extends AppCompatActivity {

    Button WRwrite;
    Button WRback;

    TextView AreaTV;
    String AreaNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        WRwrite = (Button) findViewById(R.id.WRwrite);
        WRback = (Button)findViewById(R.id.WRback);
        AreaTV=(TextView)findViewById(R.id.Area);

        AreaNum="1";    //나중에 어떤 구역 선택했는지 받아오깅

        AreaTV.setText(AreaNum.toString());

        WRwrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ReadReviewActivity.class);
                startActivity(intent);
                finish();
            }
        });
        WRback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //뒤로가기 누르면 다시 콘서트 창으로
                Intent intent = new Intent(getBaseContext(), ConcertActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}