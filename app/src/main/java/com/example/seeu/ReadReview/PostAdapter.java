package com.example.seeu.ReadReview;

import android.content.Context;
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

import com.example.seeu.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PostAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<Posting> Post;

    public PostAdapter(Context context, ArrayList<Posting> post) {
        mContext = context;
        Post = post;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

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
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.custom_listview,null);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;//리스트뷰 터치 안되게
            }
        });

        ImageView imageView=(ImageView)view.findViewById(R.id.picture);
        TextView reView=(TextView)view.findViewById(R.id.review);
        TextView UserId=(TextView)view.findViewById(R.id.UserName);
        RatingBar rating=(RatingBar)view.findViewById(R.id.ratingBar);

        imageView.setImageResource(Post.get(position).getpic());
        reView.setText(Post.get(position).gettext());
        UserId.setText(Post.get(position).getUserID());
        rating.setRating(Post.get(position).getrating());

        return view;
    }
}