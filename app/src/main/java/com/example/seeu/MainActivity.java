package com.example.seeu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.seeu.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    Button myRBtn,loginBtn,logoutBtn,searchBtn;
    TextView NameTV;
    Boolean login;
    String User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();

        myRBtn = (Button)findViewById(R.id.myRBtn);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        logoutBtn = (Button)findViewById(R.id.logoutBtn);
        searchBtn = (Button)findViewById(R.id.searchBtn);
        NameTV = (TextView)findViewById(R.id.UserName);

        login = true; // 나중에 LoginActivity에서 받아오기!!!!!!
        User = "See U"; // 나중에 Login하면 User name 받아오기!!!!!

        myRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* 내가 쓴 글 버튼을 누르면 내가 쓴 후기 액티비티로 이동 */
                Intent intent = new Intent(getBaseContext(), MyReview.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* 로그인 하기 버튼을 누르면 로그인 액티비티로 이동 */
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* 로그아웃 버튼을 누르면 로그아웃 후 로그인 액티비티로 이동 */

                /* 로그아웃 하는 코드 추가! */
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* 검색하기 버튼을 누르면 좌석 액티비티로 이동 */
                /*
                Intent intent = new Intent(getBaseContext(), ConcertActivity.class);
                startActivity(intent);
                finish();
                */
            }
        });

        /* 로그인 했으면 로그인 하기 버튼(loginBtn)삭제,
           비회원이면 로그아웃(logoutBtn), 내가 쓴 글 보기(myRBtn) 삭제 */
        if(login) {
            loginBtn.setVisibility(View.GONE);
            NameTV.setText(User.toString());
        }
        else {
            logoutBtn.setVisibility(View.GONE);
            myRBtn.setVisibility(View.GONE);
            NameTV.setText("비회원");
        }
    }

}
