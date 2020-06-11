package com.example.seeu.ReadReview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seeu.R;

import org.w3c.dom.Text;

import java.lang.ref.Reference;
import java.util.ArrayList;

public class PostAdapter extends BaseAdapter {

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
    public View getView(int position, View convertView, ViewGroup parent) {
       final int pos=position;
       final Context context = parent.getContext();

       if(convertView == null){
           LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView=inflater.inflate(R.layout.custom_listview,parent,false);
       }

        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;//리스트뷰 터치 안되게
            }
        });

        ImageView image=(ImageView) convertView.findViewById(R.id.picture);
        TextView review=(TextView)convertView.findViewById(R.id.review);
        TextView nickName=(TextView)convertView.findViewById(R.id.Nickname);
        RatingBar rating=(RatingBar)convertView.findViewById(R.id.ratingBar);

        //image.set(Post.get(position).getPicName());
        review.setText(Post.get(position).gettext());
        nickName.setText(Post.get(position).getNickname());
        rating.setRating(Post.get(position).getrating());

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