package com.example.seeu;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

@RequiresApi(api = Build.VERSION_CODES.O)
public class ConcertActivity extends AppCompatActivity {

    Button[] btnArray = null;
    int[] count = { 0 };
    Button Concert;
    ImageView imageView;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concert_chair);
        Concert = (Button)findViewById(R.id.concert);
        btnArray = new Button[16];
        count = new int[16];
        imageView = (ImageView) findViewById(R.id.imageView1);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        int[] btnID = { R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10,
                        R.id.btn11, R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15, R.id.btn16};

        for(int i=0;i<16;i++){
            btnArray[i] = (Button)findViewById(btnID[i]);
            btnArray[i].setBackgroundColor(Color.parseColor("#D3D3D3"));
        }
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Intent getintent = getIntent();
        final String concert = getintent.getStringExtra("concert"); // 메인에서 선택한 공연장 이름

        if(concert.equals("울산 현대예술관 소공연장")){
            imageView.setImageResource(R.drawable.hallsmall);
        }
        else if(concert.equals("울산 현대예술관 대공연장")){
            imageView.setImageResource(R.drawable.ulsanbig);
        }
        else if(concert.equals("충북대학교 개신문화관")){
            imageView.setImageResource(R.drawable.gaesinhall);
        }
        else if(concert.equals("두산 아트센터 연강홀")){
            imageView.setImageResource(R.drawable.hall2);
        }
        else if(concert.equals("나루 아트센터")){
            imageView.setImageResource(R.drawable.halll);
        }

        //Posting 컬렉션안에 SeatID가 "a구역"인 데이터를 찾아서 갯수를 count 배열에 넣고, 버튼색을 변경한다.
        for(Integer a=1;a<17;a++){
            final Integer b = a;
            db.collection("Posting")
                    .whereEqualTo("name", concert)
                    .whereEqualTo("seat",a.toString())
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
            final String s = String.valueOf(j+1);
            btnArray[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder oDialog = new AlertDialog.Builder(ConcertActivity.this);
                    if (count[j] != 0) {
                        oDialog.setTitle(" ").setMessage(count[j] + "개의 후기가 존재합니다.").setPositiveButton("후기보기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getBaseContext(), ReadReviewActivity.class);
                                intent.putExtra("concertName", concert);
                                intent.putExtra("concertSeat", s);
                                startActivity(intent);
                            }
                        })
                                .setNeutralButton("후기작성", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //후기 작성 페이지로 넘어가기
                                        if(mUser == null){
                                            Toast.makeText(getApplicationContext(),"로그인이 필요합니다.",Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            Intent intent = new Intent(getBaseContext(), WriteReviewActivity.class);
                                            intent.putExtra("concertName", concert);
                                            intent.putExtra("concertSeat", s);
                                            startActivity(intent);
                                        }
                                    }
                                })
                                //.setCancelable(false)
                                .show();

                    }
                    else if(count[j]==0){
                        oDialog.setTitle(" ").setMessage(count[j] + "개의 후기가 존재합니다.").setPositiveButton("후기보기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"해당 좌석의 후기가 존재하지 않습니다.",Toast.LENGTH_LONG).show();
                            }
                        })
                                .setNeutralButton("후기작성", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //후기 작성 페이지로 넘어가기
                                        if(mUser == null){
                                            Toast.makeText(getApplicationContext(),"로그인이 필요합니다.",Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            Intent intent = new Intent(getBaseContext(), WriteReviewActivity.class);
                                            intent.putExtra("concertName", concert);
                                            intent.putExtra("concertSeat", s);
                                            startActivity(intent);
                                        }
                                    }
                                })
                                //.setCancelable(false)
                                .show();
                    }
                }
            });
        }

        Concert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ConcertInfoActivity.class);
                intent.putExtra("concertName", concert);
                startActivity(intent);
            }
        });

        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();

    }
}
