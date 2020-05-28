package com.example.seeu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private EditText mNicknameView;
    private EditText mPasswordView;
    private EditText mCheckPasswordView;
    private EditText mEmailView;

    private String email="";
    private String password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNicknameView = (EditText)findViewById(R.id.Nickname);
        mEmailView = findViewById(R.id.EMAIL);
        mPasswordView = findViewById(R.id.PW);
        mCheckPasswordView = findViewById(R.id.PW2);


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
        mJoinButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mEmailView.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "email을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mNicknameView.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "nickname을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mPasswordView.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "password를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mCheckPasswordView.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "password 확인을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!mPasswordView.getText().toString().equals(mCheckPasswordView.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "password가 다릅니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(mEmailView.getText().toString(), mPasswordView.getText().toString())
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) { // 회원가입 형식에 맞지 않을 경우, 경고문 출력
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        Toast.makeText(RegisterActivity.this, "email 형식에 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        Toast.makeText(RegisterActivity.this, "이미 존재하는 email 입니다.", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        Toast.makeText(RegisterActivity.this, "다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // 형식에 맞다면, 새로운 계정 생성
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {

                                        HashMap<Object, String> userMap = new HashMap<>();
                                        //키값
                                        userMap.put(FirebaseID.documentId, user.getUid());
                                        userMap.put(FirebaseID.email, mEmailView.getText().toString());
                                        userMap.put(FirebaseID.password, mPasswordView.getText().toString());
                                        userMap.put(FirebaseID.nickname, mNicknameView.getText().toString());
                                        mStore.collection(FirebaseID.user).document(user.getUid()).set(userMap, SetOptions.merge());
                                        Toast.makeText(RegisterActivity.this, R.string.success_signup, Toast.LENGTH_SHORT).show();

                                        if (mAuth.getCurrentUser() != null) {
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            /*
                                            intent.putExtra("login", true);
                                            intent.putExtra("userNickname", mNicknameView.getText().toString());

                                             */
                                            startActivity(intent);
                                            finish();
                                        }
                                    }

                                    // ...
                                }
                            }
                        });
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





}


