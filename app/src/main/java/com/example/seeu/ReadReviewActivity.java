package com.example.seeu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seeu.R;

import com.example.seeu.ReadReview.PostAdapter;
import com.example.seeu.ReadReview.Posting;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReadReviewActivity extends AppCompatActivity {

    //받아야 하는 것 사진, 이름, 후기글

    private EditText area;
    private String Num;

    // private ListViewAdapter adapter;

    ArrayList<Posting> reviewDataList;

    private static final String TAG = "DocSnippets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_review);

        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        this.InitializeReviewData();

        area = (EditText) findViewById(R.id.areaNum);    //DB 어떤 구역 선택했눈지 받아오기

        Num = "7 구역";
        area.setText(Num.toString());

        ListView listView = (ListView) findViewById(R.id.listview3);
        final PostAdapter postAdapter = new PostAdapter(this, reviewDataList);
        listView.setAdapter(postAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(), postAdapter.getItem(position).getUserID(), Toast.LENGTH_LONG).show();
            }
        });

        //데이터 읽기 Posting컬렉션 에서 내가 누른 seatID와 seatID가 동일한 것들만 출력
        db.collection("Posting")
                .whereEqualTo("SeatID", "7구역")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
    public void InitializeReviewData() {
        reviewDataList=new ArrayList<Posting>();

        reviewDataList.add(new Posting("Asdf",R.drawable.logo,"review",3));//DB에서 값 받아오기(임시)
    }

}



