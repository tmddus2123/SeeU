package com.example.seeu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class WriteReviewActivity extends AppCompatActivity {

    private Button Write;
    private EditText str;
    public String msg;

    Button Back;
    ImageButton Add;
    TextView AreaTV;
    TextView ConcertTV;
    String ConcertName;
    String AreaNum;
    RatingBar star;

    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();

        Write = (Button)findViewById(R.id.WRwrite);
        str = (EditText)findViewById(R.id.strReview);

        //받아야하는 것 콘서트 이름, 구역,
        //넣어야 하는거 사진, 후기글
        Back = (Button)findViewById(R.id.WRback);
        AreaTV=(TextView)findViewById(R.id.Area);
        ConcertTV=(TextView)findViewById(R.id.Concert);

        Add=(ImageButton)findViewById(R.id.addImage);
        star=(RatingBar)findViewById(R.id.ratingBar); //ReadReviewActivity레이팅바에 저장할 수 있도록

        ConcertName="울산 현대예술관 소공연장";    //어떤 콘서트홀인지 받아오기
        AreaNum="1";    //나중에 어떤 구역 선택했는지 받아오깅

        ConcertTV.setText(ConcertName.toString());
        AreaTV.setText(AreaNum.toString());

        Write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ReadReviewActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //뒤로가기 누르면 다시 콘서트 창으로
                Intent intent = new Intent(getBaseContext(), ConcertActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이미지 버튼 누르면 갤러리로 이동해서 사진 추가할 수 있도록
            }
        });
    }
}