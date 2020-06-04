package com.example.seeu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.seeu.ReadReview.Posting;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class WriteReviewActivity extends AppCompatActivity {

    private Button Write;
    private EditText str;
    private String msg;
    private RatingBar ratingbar;
    private float rating;

    private static final String TAG = "DocSnippets";

    private DocumentReference docRef;
    private String nickname;
    private String userid;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private String concertName, concertSeat;

    Button Back;
    ImageButton Add;

    TextView AreaTV;
    TextView ConcertTV;

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

        Add=(ImageButton)findViewById(R.id.addImage);

        Bundle get=getIntent().getExtras();
        concertName=get.getString("concertName");
        concertSeat=get.getString("concertSeat");

        //ConcertName="울산 현대예술관 소공연장";    //어떤 콘서트홀인지 받아오기
        //AreaNum="1구역";    //나중에 어떤 구역 선택했는지 받아오깅
        ConcertTV=(TextView)findViewById(R.id.cccc);
        ConcertTV.setText(concertName.toString());

        AreaTV=(TextView)findViewById(R.id.aaaa);
        AreaTV.setText(concertSeat.toString());

        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        Write.setOnClickListener(new View.OnClickListener() { //작성하기 버튼
            @Override
            public void onClick(View view) {

                msg = str.getText().toString();
                rating=ratingbar.getRating();

                if (str.getText().toString().length() == 0) { //공백이면
                    Toast.makeText(getApplicationContext(),"후기를 작성해주세요!",Toast.LENGTH_SHORT).show();
                }
                else {  //공백 아님
                    Bundle get=getIntent().getExtras();

                    docRef = db.collection(FirebaseID.user).document(mUser.getUid());
                    // 가져온 문서에서 닉네임 받아와서 출력
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    nickname = document.getData().get("UserNickname").toString();
                                    userid = document.getData().get("documentID").toString();
                                    Log.d(TAG, "DocumentSnapshot data : " + document.getData().get("UserNickname"));
                                    Log.d(TAG, "DocumentSnapshot data : " + document.getData().get("documentID"));
                                } else
                                    Log.d(TAG, "No such document");
                            }
                            Posting Post = new Posting(concertName,concertSeat,userid,nickname,R.drawable.logo, msg, rating);
                            db.collection("Posting").document().set(Post); //디비에 저장
                            //이미지만 넣으면,,,<!
                            Intent intent = new Intent(getBaseContext(), ReadReviewActivity.class);
                            intent.putExtra("concertName",concertName);
                            intent.putExtra("concertSeat", concertSeat);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //뒤로가기 누르면 다시 콘서트 창으로
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