package com.example.seeu;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.graphics.Color.TRANSPARENT;

public class ConcertActivity extends AppCompatActivity {

    Button reviewBtn;
    int count = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concert_chair);

        reviewBtn = (Button) findViewById(R.id.btn1);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();


        //Posting 컬렉션안에 SeatID가 USS1인 데이터를 찾는다
        db.collection("Posting")
                .whereEqualTo("SeatID", "uss1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //데이터가 있으면
                        if(task.isSuccessful()){
                            reviewBtn.setBackgroundColor(Color.parseColor("#A0DFDF"));
                            for(QueryDocumentSnapshot document : task.getResult()){
                                count += 1;
                            }
                        } else{
                                // 데이터가 존재하지 않습니다. uss2로 해놨는데도 버튼 색깔이 바뀌는거 보면
                                // if문이 일단 데이터가 있으면 일지두,,,
                        }
                    }
                });

        reviewBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder oDialog = new AlertDialog.Builder(ConcertActivity.this);
                         oDialog.setTitle(" ").setMessage(count + "개의 후기가 존재합니다.").setPositiveButton("후기보기", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getBaseContext(), ReadReviewActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                                .setNeutralButton("후기작성", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //후기 작성 페이지로 넘어가기
                                        Intent intent = new Intent(getBaseContext(), WriteReviewActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                //.setCancelable(false)
                                .show();

                }

        });


        /*Action Bar(Title bar) 받아와서 없애기*/
                ActionBar ab = getSupportActionBar();
        ab.hide();

    }
}
