package com.example.seeu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyReview extends AppCompatActivity {

    ListView myRList;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);

        FirebaseFirestore db;

        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();

        myRList = (ListView)findViewById(R.id.myRList);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        myRList.setAdapter(arrayAdapter);
        db = FirebaseFirestore.getInstance();

        db.collection("Posting")
                .whereEqualTo("UserID", "lHntXjWawWvDAfc1hRpU")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String str=document.get("text").toString();
                                arrayList.add(str);
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
        /*
        * DB에서 현재 Login한 유저의 글 정보를 불러와서 List 항목 만들기
        * */
    }
}
