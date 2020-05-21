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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seeu.R;
import com.example.seeu.ReadReview.ListVO;
import com.example.seeu.ReadReview.ListViewAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReadReviewActivity extends AppCompatActivity {

    //받아야 하는 것 사진, 이름, 후기글

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ListView listview;
    private ListViewAdapter adapter;
    private int[] pic={R.drawable.logo,R.drawable.small_logo,R.drawable.logo};
    private String[] review={"좋아요","안좋아요","개별로"};
    private String[] name={"이","승","연"};

    ArrayList<ListVO> reviewDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_review);

        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();


        adapter=new ListViewAdapter();
        listview=(ListView)findViewById(R.id.listview3);

        listview.setAdapter(adapter);

        for(int i=0;i<pic.length;i++){
            adapter.addVO(ContextCompat.getDrawable(this,pic[i]),review[i],name[i]);

        }

    }
}


