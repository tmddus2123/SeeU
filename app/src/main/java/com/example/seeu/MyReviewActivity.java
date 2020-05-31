package com.example.seeu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.seeu.MyReview.ListViewItem;
import com.example.seeu.MyReview.MyListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyReviewActivity extends AppCompatActivity {

    ListView myRList;
    ArrayList<ListViewItem> arrayList = new ArrayList<ListViewItem>();
    MyListAdapter arrayAdapter;
    FirebaseFirestore db;
    String concertName, concertSeat, concertText;

    private FirebaseAuth mAuth;
    // 현재 로그인 된 유저 정보
    private FirebaseUser mUser;
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);

        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();

        myRList = (ListView)findViewById(R.id.myRList);
        arrayAdapter = new MyListAdapter();
        myRList.setAdapter(arrayAdapter);

        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        // 현재 로그인한 사용자 정보 받아옴
        mUser = mAuth.getCurrentUser();
        docRef = db.collection(FirebaseID.user).document(mUser.getUid());
        // 가져온 문서에서 닉네임 받아와서 출력

        db.collection("Posting")
                .whereEqualTo("UserID", docRef.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                concertName = document.getString("Name");
                                concertSeat = document.getString("Seat") + " 구역";
                                concertText = document.getString("text");
                                arrayAdapter.addItem(concertName, concertSeat, concertText);
                                myRList.invalidateViews();
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

        // 검색된 리스트 클릭하면 ConcertActivity로 이동
        myRList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), ReadReviewActivity.class);
                /* 공연장 이름을 콘서트 액티비티에 넘겨준다. */
                intent.putExtra("concertName", concertName);
                intent.putExtra("concertSeat", concertSeat);
                startActivity(intent);
            }
        });
    }
}
