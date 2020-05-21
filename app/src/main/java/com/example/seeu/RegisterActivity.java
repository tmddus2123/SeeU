package com.example.seeu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.example.seeu.ui.login.LoginActivity;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {


    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");
    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;
    // 회원가입에 필요한 친구들
    private EditText mNicknameView;
    private EditText mIDView;
    private EditText mPasswordView;
    private EditText mEmailView;

    private String email="";
    private String password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();
        mNicknameView = (EditText)findViewById(R.id.Nickname);
        mEmailView = findViewById(R.id.Email);
        mPasswordView = findViewById(R.id.PW);


        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();

        Button mCancelButton = (Button) findViewById(R.id.cancel); // 뒤로가기 버튼
        Button mJoinButton = (Button) findViewById(R.id.SignUp); // 가입하기 버튼


         mCancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Login_page(v);
            }
        });

        mJoinButton = (Button)findViewById(R.id.SignUp);
        mJoinButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                SignUp(v);
                if(firebaseAuth.getCurrentUser() != null) {
                    finish();
                    Main_page(v);
                }
            }
        });


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

    public void SignUp(View view){
        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();

        if(isValidEmail() && isValidPW()){
            createUser(email, password);
        }
    }

    public void SignIn(View view){
        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();

        if(isValidEmail() && isValidPW()){
            loginUser(email, password);
        }
    }

    private boolean isValidEmail(){
        if(email.isEmpty()){
            // 이메일이 공백일경우
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            // 이메일 형식이 아닌경우
            return false;
        }
        else{
            // 멀쩡하면 트루 리턴
            return true;
        }
    }

    private boolean isValidPW(){
        if(password.isEmpty()){
            // 비밀번호 공백일 경우
            return false;
        }
        else if(!PASSWORD_PATTERN.matcher(password).matches()){
            // 비밀번호 형식 불일치
            return false;
        }
        else{
            return true;
        }
    }

    private void createUser(String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //회원가입 성공할 경우
                    Toast.makeText(RegisterActivity.this, R.string.success_signup, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RegisterActivity.this, R.string.failed_signup, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginUser(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // 로그인 성공
                    Toast.makeText(RegisterActivity.this, R.string.success_login, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RegisterActivity.this, R.string.failed_login, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}


