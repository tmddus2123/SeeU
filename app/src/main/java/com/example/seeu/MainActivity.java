package com.example.seeu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seeu.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //firestore instance
    FirebaseFirestore db;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;


    Button myRBtn, loginBtn, logoutBtn;
    ImageButton searchBtn;
    EditText searchStr;
    TextView NameTV;
    Boolean login;
    String userNickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();

        myRBtn = (Button) findViewById(R.id.myRBtn);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);
        searchStr = (EditText) findViewById(R.id.searchStr);
        NameTV = (TextView) findViewById(R.id.UserName);
        listView = (ListView)findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        //init firestore
        db = FirebaseFirestore.getInstance();

        Intent getintent = getIntent();
        login = getintent.getExtras().getBoolean("login");
        userNickname = getintent.getExtras().getString("userNickname");


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
                intent.putExtra("login", false);
                startActivity(intent);
                finish();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            /* 검색버튼 누르면 (EditText의 검색어로 )검색 */
            @Override
            public void onClick(View view) {
                db.collection("Concert List")
                        .whereEqualTo("Name", searchStr.getText().toString().trim())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String str=document.getId().toString();
                                        arrayList.add(str);
                                        listView.invalidateViews();
                                        Log.d("FireStore READ", document.getId() + " => " + document.getData());
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                /* ((((((임시로)))))) 검색하기 버튼을 누르면 좌석 액티비티로 이동 */

                Intent intent = new Intent(getBaseContext(), ConcertActivity.class);
                startActivity(intent);
                finish();


            }
        });

        /* 로그인 했으면 로그인 하기 버튼(loginBtn)삭제,
           비회원이면 로그아웃(logoutBtn), 내가 쓴 글 보기(myRBtn) 삭제 */
        if (login) {
            loginBtn.setVisibility(View.GONE);
            NameTV.setText(userNickname);
        } else {
            logoutBtn.setVisibility(View.GONE);
            myRBtn.setVisibility(View.GONE);
            NameTV.setText("비회원");
        }
    }
}