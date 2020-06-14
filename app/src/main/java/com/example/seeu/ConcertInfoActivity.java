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
import android.widget.ImageView;
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
    ImageView imageView;

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
        imageView = (ImageView) findViewById(R.id.concertView);

        Intent get = getIntent();
        Name = get.getStringExtra("concertName");
        concertName.setText(Name);

        if(Name.equals("울산 현대예술관 소공연장")){
            imageView.setImageResource(R.drawable.ulsan);
        }
        else if(Name.equals("울산 현대예술관 대공연장")){
            imageView.setImageResource(R.drawable.ulsan);
        }
        else if(Name.equals("두산 아트센터 연강홀")){
            imageView.setImageResource(R.drawable.dusan1);
        }
        else if(Name.equals("나루 아트센터")){
            imageView.setImageResource(R.drawable.naru);
        }
        else if(Name.equals("충북대학교 개신문화관")){
            imageView.setImageResource(R.drawable.gaesin);
        }

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

