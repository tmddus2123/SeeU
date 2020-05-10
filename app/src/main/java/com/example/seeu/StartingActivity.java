package com.example.seeu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.ActionBar;

import com.example.seeu.ui.login.LoginActivity;

public class StartingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        /* 핸들러로 화면 전환에 딜레이를 걸었음 (300밀리세크) */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                /* LoginActivity를 새로운 intent로 불러와서 시작함.
                   현재 intent는 종료 */
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 300);
    }
}
