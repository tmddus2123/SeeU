package com.example.seeu.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.example.seeu.MainActivity;
import com.example.seeu.R;
import com.example.seeu.RegisterActivity;

public class LoginActivity extends Activity {


    private EditText mIDView;
    private EditText mPasswordView;
    private ImageView logoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 로그인 폼 생성

        mIDView = (EditText) findViewById(R.id.ID);
        mPasswordView = (EditText) findViewById(R.id.password);
        logoView = (ImageView)findViewById(R.id.imageView);

        logoView.setImageResource(R.drawable.small_logo);

        // 서버,디비 연동 이후 기능 추가예정. 이벤트 핸들러만 추가
        mIDView.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if(id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL){
                    return true;
                }
                return false;
            }

        });
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    //attemptLogin();

                    return true;
                }
                return false;
            }
        });

        // 버튼 생성

        Button mJoinButton = (Button) findViewById(R.id.join); // 회원가입 버튼
        Button mLoginButton = (Button) findViewById(R.id.login); // 로그인 버튼
        Button mNoneMemberButton = (Button) findViewById(R.id.NonMember);


        // 이벤트핸들러

        mJoinButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                /* 회원가입 버튼 누르면 회원가입 페이지로 */
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mLoginButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                /*디비 연동 전 - 로그인 버튼 누르면 메인액티비티로 */
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                // 로그인 성공했으면 main의 login 값 true로 넘겨주기
                intent.putExtra("login", true);
                intent.putExtra("userNickname","SeeU"); //디비에서 로그인한 아이디의 짝인 닉네임불러오기
                startActivity(intent);
                finish();
            }
        });

        mNoneMemberButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 비회원 버튼 누르면 메인 액티비티로 */
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("login", false);
                startActivity(intent);
                finish();
            }
        });

    }

}
