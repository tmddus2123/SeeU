package com.example.seeu.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.example.seeu.MainActivity;
import com.example.seeu.R;
import com.example.seeu.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends Activity {

    private ImageView logoView;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private EditText mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // 로그인 폼 생성
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);

        logoView = (ImageView) findViewById(R.id.imageView);
        logoView.setImageResource(R.drawable.small_logo);


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

        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // 빈 폼 있는지 확인
                if(mEmailView.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "email을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mPasswordView.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "password를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(mEmailView.getText().toString(), mPasswordView.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) { // 로그인 형식 체크
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthInvalidUserException e) {
                                        Toast.makeText(LoginActivity.this, "존재하지 않는 email 입니다.", Toast.LENGTH_SHORT).show();
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        Toast.makeText(LoginActivity.this, "email 형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                                    } catch (FirebaseNetworkException e) {
                                        Toast.makeText(LoginActivity.this, "firebase와의 네트워크 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                                    }catch (Exception e) {
                                        Toast.makeText(LoginActivity.this, "다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                                    }
                                } else { // 예외 없다면 로그인성공
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        Toast.makeText(LoginActivity.this, "로그인 성공!" , Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        /*
                                        intent.putExtra("login", true);
                                        intent.putExtra("userNickname","SeeU"); //디비에서 로그인한 아이디의 짝인 닉네임불러오기

                                         */
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }
                        });
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
