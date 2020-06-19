package com.example.seeu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.seeu.ReadReview.PostAdapter;
import com.example.seeu.ReadReview.Posting;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class WriteReviewActivity extends AppCompatActivity {

    private Button Write;
    private EditText str;
    private String msg;
    private RatingBar ratingbar;
    private float rating;
    private ImageView addimage;
    private String filename;
    Bitmap img;
    private static final int REQUEST_CODE = 0;
    private Uri filePath;

    private static final String TAG = "DocSnippets";

    private DocumentReference docRef;
    private String nickname;
    private String userid;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private String concertName, concertSeat;

    Button Back;
    TextView AreaTV;
    TextView ConcertTV;
    Integer Cnt;
    ListView Postlist;
    ArrayList<Posting> arrayList = new ArrayList<Posting>();
    PostAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        Write = (Button) findViewById(R.id.WRwrite);
        str = (EditText) findViewById(R.id.strReview);
        ratingbar = (RatingBar) findViewById(R.id.ratingBar);

        Back = (Button) findViewById(R.id.WRback);


        Bundle get = getIntent().getExtras();
        concertName = get.getString("concertName");
        concertSeat = get.getString("concertSeat");

        //ConcertName="울산 현대예술관 소공연장";    //어떤 콘서트홀인지 받아오기
        //AreaNum="1구역";    //나중에 어떤 구역 선택했는지 받아오깅
        ConcertTV = (TextView) findViewById(R.id.cccc);
        ConcertTV.setText(concertName.toString());

        db.collection("Concert List")
                .whereEqualTo("Name", concertName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                Cnt = Integer.parseInt(document.getString("Cnt"));
                            }
                        }
                    }
                });

        AreaTV = (TextView) findViewById(R.id.aaaa);
        AreaTV.setText(concertSeat.toString());

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        arrayAdapter = new PostAdapter();


        addimage = findViewById(R.id.addImage);

        Write.setOnClickListener(new View.OnClickListener() { //작성하기 버튼
            @Override
            public void onClick(View view) {

                msg = str.getText().toString();
                rating = ratingbar.getRating();

                if (str.getText().toString().length() == 0) { //공백이면
                    Toast.makeText(getApplicationContext(), "후기를 작성해주세요!", Toast.LENGTH_SHORT).show();
                } else if (filePath == null) {
                    Toast.makeText(getApplicationContext(), "사진을 선택해주세요", Toast.LENGTH_SHORT).show();
                } else {  //공백 아님
                    Bundle get = getIntent().getExtras();

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
                                    Cnt++;
                                    Log.d("this is a count", Cnt.toString());
                                    uploadFile(); //스토리지에 업로드
                                    Posting Post = new Posting(concertName, concertSeat, userid, nickname, filename, msg, rating);
                                    db.collection("Posting").document().set(Post); //디비에 저장
                                    db.collection("Concert List").document(concertName).update("Cnt", Cnt.toString());

                                    Log.d(TAG, "DocumentSnapshot data : " + document.getData().get("UserNickname"));
                                    Log.d(TAG, "DocumentSnapshot data : " + document.getData().get("documentID"));
                                } else
                                    Log.d(TAG, "No such document");
                            }
                            //이미지만 넣으면,,,<!
                            Intent intent = new Intent(getBaseContext(), ReadReviewActivity.class);
                            intent.putExtra("concertName", concertName);
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
        addimage.setOnClickListener(new View.OnClickListener() { //이미지 넣
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                filePath = data.getData();
                Log.d(TAG, "uri:" + String.valueOf(filePath));

                try { //Uri파일을 비트맵으로 만들어서 이미지뷰에 집어넣음
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    addimage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void uploadFile() {//업로드하는 함수
        if (filePath != null) {
            //스토리지
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //파일명만들기
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH__mmss");
            Date now = new Date();
            filename = formatter.format(now) + ".png";
            //스토리지 주소와 폴더 파일명 지정
            StorageReference storageRef = storage.getReferenceFromUrl("gs://seeu-70a59.appspot.com").child("images/" + filename);
            storageRef.putFile(filePath)//성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                        }
                    })//실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}