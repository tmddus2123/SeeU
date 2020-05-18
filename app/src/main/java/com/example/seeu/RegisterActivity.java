package com.example.seeu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seeu.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText mNicknameView;
    private EditText mIDView;
    private EditText mPasswordView;
    private EditText mEmailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();

        Button mCancelButton = (Button) findViewById(R.id.cancel); // 뒤로가기 버튼
        Button mJoinButton = (Button) findViewById(R.id.SignUp); // 가입하기 버튼

        mCancelButton = (Button)findViewById(R.id.cancel);
        mCancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Login_page(v);
            }
        });

        mJoinButton = (Button)findViewById(R.id.SignUp);
        mJoinButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Main_page(v);
            }
        });

        mNicknameView = (EditText)findViewById(R.id.Nickname);
    }

    private void Login_page(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void Main_page(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("login", true);
        intent.putExtra("userNickname",mNicknameView.getText().toString());
        startActivity(intent);
        finish();
    }

}
