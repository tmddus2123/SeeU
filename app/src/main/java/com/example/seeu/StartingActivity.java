package com.example.seeu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.ActionBar;

import com.example.seeu.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartingActivity extends Activity {

    private FirebaseAuth mAuth;
    // 현재 로그인 된 유저 정보
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        mAuth = FirebaseAuth.getInstance();
        // 현재 로그인한 사용자 정보 받아옴
        mUser = mAuth.getCurrentUser();

        if(mUser != null){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                /* LoginActivity를 새로운 intent로 불러와서 시작함.
                   현재 intent는 종료 */
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 300);
        }
        else {
            /* 핸들러로 화면 전환에 딜레이를 걸었음 (300밀리세크) */
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                /* LoginActivity를 새로운 intent로 불러와서 시작함.
                   현재 intent는 종료 */
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 300);
        }
    }
}
