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
    Button weblink, maplink;
    TextView concertName, concertLoc, concertCall, concertCnt, concertWEB;
    String Loc, WEB, Latitude, Longitude, Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concert_info);

        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();

        db = FirebaseFirestore.getInstance();

        weblink = (Button) findViewById(R.id.webBtn);
        maplink = (Button) findViewById(R.id.MapBtn);
        concertName = (TextView) findViewById(R.id.concertName);
        concertLoc = (TextView) findViewById(R.id.concertLoc);
        concertCall = (TextView) findViewById(R.id.concertCall);
        concertCnt = (TextView) findViewById(R.id.concertCnt);
        concertWEB = (TextView) findViewById(R.id.concertWEB);

        Intent get = getIntent();
        Name = get.getStringExtra("concertName");
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
                        Latitude=document.getString("Latitude");
                        Longitude=document.getString("Longitude");
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

        maplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String geo="geo:"+Latitude+", "+Longitude+"?z=10&q="+Name;
                Uri uri=Uri.parse(geo);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
    }
}

