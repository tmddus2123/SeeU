package com.example.seeu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seeu.MyReview.ListViewItem;
import com.example.seeu.MyReview.MyListAdapter;
import com.example.seeu.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.auth.FirebaseUser;

import java.io.*;
import java.util.ArrayList;
import java.util.NavigableMap;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // firestore instance
    FirebaseFirestore db;
    // 이메일 비밀번호 등 로그인 모듈
    private FirebaseAuth mAuth;
    // 현재 로그인 된 유저 정보
    private FirebaseUser mUser;
    private DocumentReference docRef;

    // ListView handle
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    Button myRBtn, loginBtn, logoutBtn;
    ImageButton searchBtn;
    EditText searchStr;
    TextView NameTV;


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
        arrayList.clear();

        //init firestore
        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        // 현재 로그인한 사용자 정보 받아옴
        mUser = mAuth.getCurrentUser();

        Intent getintent = getIntent();

        myRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* 내가 쓴 글 버튼을 누르면 내가 쓴 후기 액티비티로 이동 */
                // main의 값들을 초기화 (액티비티를 종료하지 않기 때문)
                arrayList.clear();
                db.collection("Concert List")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    arrayList.clear();
                                    for(QueryDocumentSnapshot document : task.getResult()) {
                                        String str = document.getId().toString();
                                        arrayList.add(str);
                                        listView.invalidateViews();
                                        Log.d("FireStore READ", document.getId() + " => " + document.getData());
                                    }
                                }
                            }
                        });
                listView.invalidateViews();
                searchStr.setText("");

                Intent intent = new Intent(getBaseContext(), MyReviewActivity.class);
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
                mAuth.getInstance().signOut();
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        db.collection("Concert List")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            arrayList.clear();
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                String str = document.getId().toString();
                                arrayList.add(str);
                                listView.invalidateViews();
                                Log.d("FireStore READ", document.getId() + " => " + document.getData());
                            }
                        }
                    }
                });

       searchBtn.setOnClickListener(new View.OnClickListener() {
            // 검색버튼 누르면 (EditText의 검색어로 )검색
            @Override
            public void onClick(View view) {
                if (searchStr.getText().toString().equals("")) {
                    arrayList.clear();
                    listView.invalidateViews();
                } else {
                    db.collection("Concert List")
                            .whereEqualTo("Name", searchStr.getText().toString().trim())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        arrayList.clear();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String str = document.getId().toString();
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
                }
            }
        });

        // 검색된 리스트 클릭하면 ConcertActivity로 이동
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String concert = arrayList.get(position).toString();
                Log.d("Concert Name Plz", concert);
                // main의 값들을 초기화 (액티비티를 종료하지 않기 때문)
                arrayList.clear();
                db.collection("Concert List")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    arrayList.clear();
                                    for(QueryDocumentSnapshot document : task.getResult()) {
                                        String str = document.getId().toString();
                                        arrayList.add(str);
                                        listView.invalidateViews();
                                        Log.d("FireStore READ", document.getId() + " => " + document.getData());
                                    }
                                }
                            }
                        });
                listView.invalidateViews();
                searchStr.setText("");

                Intent intent = new Intent(getBaseContext(), ConcertActivity.class);
                /* 공연장 이름을 콘서트 액티비티에 넘겨준다. */
                intent.putExtra("concert", concert);
                startActivity(intent);
            }
        });

        if(mUser != null){
            // 로그인 되어 있다면 documentid로 문서 가져오기
            docRef = db.collection(FirebaseID.user).document(mUser.getUid());
            loginBtn.setVisibility(View.GONE);
            // 가져온 문서에서 닉네임 받아와서 출력
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if(document.exists()){
                            String nickname = document.getData().get("UserNickname").toString();
                            NameTV.setText(nickname);
                            //Log.d(TAG, "DocumentSnapshot data : " + document.getData().get("UserNickname"));
                        }
                        else{
                            Log.d(TAG, "No such document");
                        }
                    }
                    else{
                        Log.d(TAG, "get failed with", task.getException());
                    }
                }
            });

        }
        else{
            // 로그인 안되있음 비회원으로 출력
            logoutBtn.setVisibility(View.GONE);
            myRBtn.setVisibility(View.GONE);
            NameTV.setText("비회원");
        }
    }

    private long backKeyPressedTime =0;
    private Toast toast;

    @Override
    public void onBackPressed() {
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast Show
        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            ViewGroup group = (ViewGroup) toast.getView();
            TextView messageTextView = (TextView) group.getChildAt(0);
            messageTextView.setTextSize(15);
            toast.show();
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            //FirebaseAuth.getInstance().signOut();
            //아니 왜 안돼냐고
            ActivityCompat.finishAffinity(this);
            System.runFinalization();
            System.exit(0);
            toast.cancel();
        }
    }

}