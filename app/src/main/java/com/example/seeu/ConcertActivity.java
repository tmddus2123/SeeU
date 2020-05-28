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

import static android.graphics.Color.TRANSPARENT;

public class ConcertActivity extends AppCompatActivity {

    Button[] btnArray = null;
    int[] count = { 0 };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concert_chair);
        btnArray = new Button[16];
        count = new int[16];
        int[] btnID = { R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10,
                        R.id.btn11, R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15, R.id.btn16};

        for(int i=0;i<16;i++){
            btnArray[i] = (Button)findViewById(btnID[i]);
        }
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Intent getintent = getIntent();
        String concert = getintent.getStringExtra("concert"); // 메인에서 선택한 공연장 이름

        //Posting 컬렉션안에 SeatID가 "a구역"인 데이터를 찾아서 갯수를 count 배열에 넣고, 버튼색을 변경한다.
        for(Integer a=1;a<17;a++){
            final Integer b = a;
            db.collection("Concert List").document(concert)
                    .collection(a.toString())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            //데이터가 있으면
                            //Btn1.setBackgroundColor(Color.parseColor("#A0DFDF"));
                            if(task.isSuccessful()){

                                for(QueryDocumentSnapshot document : task.getResult()){
                                    count[b-1] += 1;
                                }
                                if(count[b-1] > 0){
                                    btnArray[b-1].setBackgroundColor(Color.parseColor("#A0DFDF"));
                                }
                            }
                        }
                    });
        }

        //btn배열마다 setonCLick을 부여해준 후, alert에 count배열을 출력해 몇 개의 후기가 있는지 알려주고 후기보기/후기작성을 선택한다.
        for(int i=0;i<16;i++) {
            final int j = i;
            btnArray[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder oDialog = new AlertDialog.Builder(ConcertActivity.this);
                    oDialog.setTitle(" ").setMessage(count[j] + "개의 후기가 존재합니다.").setPositiveButton("후기보기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getBaseContext(), ReadReviewActivity.class);
                            startActivity(intent);
                        }
                    })
                            .setNeutralButton("후기작성", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //후기 작성 페이지로 넘어가기
                                    Intent intent = new Intent(getBaseContext(), WriteReviewActivity.class);
                                    startActivity(intent);
                                }
                            })
                            //.setCancelable(false)
                            .show();

                }

            });
        }

        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();

    }
}
