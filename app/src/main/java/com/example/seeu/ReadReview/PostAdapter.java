package com.example.seeu.ReadReview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.seeu.FirebaseID;
import com.example.seeu.R;
import com.example.seeu.ReadReviewActivity;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.lang.ref.Reference;
import java.util.ArrayList;

public class PostAdapter extends BaseAdapter {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private DocumentReference docRef;


    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final String TAG = "DocSnippets";


    private ArrayList<Posting> Post=new ArrayList<Posting>();

    @Override
    public int getCount() {
        return Post.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Posting getItem(int position) {
        return Post.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       final int pos=position;
       final Context context = parent.getContext();

       if(convertView == null){
           LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView=inflater.inflate(R.layout.custom_listview,parent,false);
       }

        TextView review=(TextView)convertView.findViewById(R.id.review);
        TextView nickName=(TextView)convertView.findViewById(R.id.Nickname);
        RatingBar rating=(RatingBar)convertView.findViewById(R.id.ratingBar);
        Button btn=(Button)convertView.findViewById(R.id.delete);//삭제버튼

        ImageView image=(ImageView) convertView.findViewById(R.id.picture);//사진 보여주는거

        String id=Post.get(position).getUserID();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if(mUser != null) {
            String uid = mUser.getUid();
            if (uid.equals(id)) {//같으면
                btn.setVisibility(View.VISIBLE);//버튼 보이기?
            } else {
                btn.setVisibility(View.GONE);
                convertView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;//리스트뷰 터치 안되게
                    }
                });
            }
        } else{
            btn.setVisibility(View.GONE);
            convertView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;//리스트뷰 터치 안되게
                }
            });
        }
        btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder oDialog = new AlertDialog.Builder(context);
                oDialog.setTitle(" ").setMessage("후기를 삭제하시겠습니까?").setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.collection("Posting")
                                .whereEqualTo("picName", Post.get(position).getPicName())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for(QueryDocumentSnapshot document : task.getResult()) {
                                                String str = document.getId().toString();
                                                Log.d("문서 번호=>", document.getId());
                                                db.collection("Posting").document(str).delete();
                                                storage.getReference().child("images").child(Post.get(position).getPicName()).delete();
                                                Toast.makeText(context, "삭제 완료!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                });
                    }
                })
                        .setNeutralButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, "취소");
                            }
                        }).show();
            }
        });

        review.setText(Post.get(position).gettext());
        nickName.setText(Post.get(position).getNickname());
        rating.setRating(Post.get(position).getrating());

        StorageReference storageRef = storage.getReferenceFromUrl("gs://seeu-70a59.appspot.com").child("images/" + Post.get(position).getPicName());

        Glide.with(context)
                .load(storageRef)
                .apply(new RequestOptions().placeholder(R.drawable.loading))
                .into(image);

        return convertView;
    }

    public void addItem(String Name, String Seat, String UserID, String Nickname, String PicName, String Review, float rating){
        Posting post = new Posting();

        post.setName(Name);
        post.setSeat(Seat);
        post.setUserID(UserID);
        post.setNickname(Nickname);
        post.setText(Review);
        post.setPicName(PicName);
        post.setRating(rating);

        Post.add(post);
    }
}