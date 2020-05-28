package com.example.seeu;

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
import android.widget.Toast;

import com.example.seeu.ReadReview.Posting;
import com.google.firebase.firestore.FirebaseFirestore;


public class WriteReviewActivity extends AppCompatActivity {

    private Button Write;
    private EditText str;
    private String msg;
    private RatingBar ratingbar;
    private float rating;

    Button Back;
    ImageButton Add;

    TextView AreaTV;
    TextView ConcertTV;
    String ConcertName;
    String AreaNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        Write = (Button)findViewById(R.id.WRwrite);
        str = (EditText)findViewById(R.id.strReview);
        ratingbar = (RatingBar)findViewById(R.id.ratingBar);

        //콘서트장 이름과 구역을 어떻게 받아올것
        Back = (Button)findViewById(R.id.WRback);
        AreaTV=(TextView)findViewById(R.id.Area);
        ConcertTV=(TextView)findViewById(R.id.Concert);

        Add=(ImageButton)findViewById(R.id.addImage);

        ConcertName="울산 현대예술관 소공연장";    //어떤 콘서트홀인지 받아오기
        AreaNum="1구역";    //나중에 어떤 구역 선택했는지 받아오깅

        ConcertTV.setText(ConcertName.toString());
        AreaTV.setText(AreaNum.toString());

        Write.setOnClickListener(new View.OnClickListener() { //작성하기 버튼
            @Override
            public void onClick(View view) {

                msg = str.getText().toString(); //리뷰글이랑 별점만 DB저장 가능
                rating=ratingbar.getRating();

                if (str.getText().toString().length() == 0) { //공백이면
                    Toast.makeText(getApplicationContext(),"후기를 작성해주세요!",Toast.LENGTH_SHORT).show();
                }
                else {  //공백 아님
                    Posting Post = new Posting("승여니",R.drawable.logo, msg, rating);
                    db.collection("Posting").document().set(Post); //디비에 저장

                    Intent intent = new Intent(getBaseContext(), ReadReviewActivity.class);
                    startActivity(intent);
                    finish();
                }
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