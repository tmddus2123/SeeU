package com.example.seeu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class ConcertInfoActivity extends AppCompatActivity {
    FirebaseFirestore db;
    Button weblink;
    TextView concertName, concertLoc, concertCall, concertCnt, concertWEB;
    String Loc, Call, WEB;
    Integer Cnt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concert_info);

        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();

        db = FirebaseFirestore.getInstance();


        weblink = (Button) findViewById(R.id.webBtn);
        concertName = (TextView) findViewById(R.id.concertName);
        concertLoc = (TextView) findViewById(R.id.concertLoc);
        concertCall = (TextView) findViewById(R.id.concertCall);
        concertCnt = (TextView) findViewById(R.id.concertCnt);
        concertWEB = (TextView) findViewById(R.id.concertWEB);

        Intent get = getIntent();
        String Name = get.getStringExtra("concertName");
        concertName.setText(Name);

        DocumentReference docRef = db.collection("Concert List").document(Name);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Loc = (String) document.getString("Loc");
                        concertLoc.setText(document.getString("Loc"));
                        concertCall.setText(document.getString("Call"));
                        concertCnt.setText(String.valueOf(document.get("Cnt")));
                        concertWEB.setText(document.getString("WEB"));
                        WEB=document.getString("WEB");
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });

        weblink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(WEB));
                startActivity(intent);
            }
        });
    }
}


        /*
        * Concert_chair xml에 이거 추가.....
        <Button
        android:id="@+id/button1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="공연장 보기" />

         */

        /*
        * Concert Activity에 이거 추가........
        onCreate 밖에
        Button button; 이거 추가
        onCreate 안에
        button = (Button) findViewById(R.id.button1); 이거랑
        *
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ConcertInfoActivity.class);
                intent.putExtra("concertName", "울산 현대예술관 소공연장");
                startActivity(intent);
            }
        });
        이거 두개 추가..!
         */
