package com.example.seeu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seeu.ui.login.LoginActivity;


public class SubActivity extends AppCompatActivity {

    public ImageView graView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        TextView text3 = (TextView)findViewById(R.id.textView4);
        TextView text4 = (TextView)findViewById(R.id.textView4);
        TextView text5 = (TextView)findViewById(R.id.textView5);
        text3.setTextColor(Color.BLACK);
        text4.setTextColor(Color.BLACK);
        text5.setTextColor(Color.BLACK);

        graView = (ImageView)findViewById(R.id.imageView4);
        graView.setImageResource(R.drawable.graphic3);

        Button regbtn = (Button) findViewById(R.id.button2);
        regbtn.setBackgroundColor(Color.rgb(160, 223, 223));
        Button lobtn = (Button) findViewById(R.id.button3);


        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 버튼 누르면 회원가입 액티비티로 */
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


        lobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 버튼 누르면 로그인 액티비티로 */
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
