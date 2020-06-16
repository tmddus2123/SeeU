package com.example.seeu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ReadReviewActivity extends AppCompatActivity {

    //받아야 하는 것 사진, 이름, 후기글

    FirebaseFirestore db;
    ListView Postlist;
    ArrayList<Posting> arrayList = new ArrayList<Posting>();
    PostAdapter arrayAdapter;

    private TextView area;
    private String Name, Num, Nick, ID;
    private String userid, nickname, review, filename;
    private double rating;
    private ImageView img;
    private static final int REQUEST_CODE = 0;
    private Uri filePath;

    private Button deleteBtn;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    StorageReference storageRef = storage.getReference();
    StorageReference pathReference = storageRef.child("images/" + filename);

    private DocumentReference docRef;

    private static final String TAG = "DocSnippets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_review);

        deleteBtn = (Button) findViewById(R.id.delReview);

        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();

        Bundle get = getIntent().getExtras();
        Name = get.getString("concertName");
        Num = get.getString("concertSeat");

        db = FirebaseFirestore.getInstance();

        area = (TextView) findViewById(R.id.areaNum);    //DB 어떤 구역 선택했눈지 받아오기
        area.setText(Num.toString());

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        Intent getintent = getIntent();

        img = (ImageView) findViewById(R.id.picture);

        Postlist = (ListView) findViewById(R.id.listview3);
        arrayAdapter = new PostAdapter();
        Postlist.setAdapter(arrayAdapter);

        final FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        //데이터 읽기 Posting컬렉션 에서 내가 누른 seatID와 seatID가 동일한 것들만 출력
        db.collection("Posting")
                .whereEqualTo("name", Name)
                .whereEqualTo("seat", Num)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                userid = document.getString("userID");
                                nickname = document.getString("nickname");
                                review = document.getString("text");
                                filename = document.getString("picName");
                                rating = document.getDouble("rating");

                                arrayAdapter.addItem(Name, Num, userid, nickname, filename, review, (float) rating);
                                Postlist.invalidateViews();

                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
     /*   try{
            File path = new File("Folder path");

            final File file=new File(path,filename);

            file.createNewFile();

            final FileDownloadTask fileDownloadTask = pathReference.getFile(file);
            fileDownloadTask.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        /*deleteBtn.setOnClickListener(new View.OnClickListener(){ //삭제버튼
            @Override
            public void onClick(View v) {
                db.collection("Posting").document(userid)//
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        });
            }
        });*/

        //현재 로그인 되어있는 UserID랑 리뷰 적힌 UserID와 비교 동일하면 수정과 삭제 가능
        docRef = db.collection(FirebaseID.user).document(mUser.getUid());
        Log.d(TAG, docRef + "=>" + userid);
        if (docRef.equals(userid)) {//같으면
            deleteBtn.setVisibility(View.VISIBLE);//버튼 보이ㅣㄱ?
        }
    }
}