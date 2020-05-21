package com.example.seeu.ReadReview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seeu.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<ListVO> listVO = new ArrayList<ListVO>();
    public ListViewAdapter() {

    }

    @Override
    public int getCount() {
        return listVO.size();
    }
    //리스트뷰에 데이터를 넣는 부분
    @Override
    public  View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context=parent.getContext();

        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.custom_listview,parent,false);
        }

        ImageView image=(ImageView)convertView.findViewById(R.id.picture);
        TextView review=(TextView)convertView.findViewById(R.id.review);
        TextView name=(TextView)convertView.findViewById(R.id.UserName);

        ListVO listViewItem=listVO.get(position);

        image.setImageDrawable(listViewItem.getPic());
        review.setText(listViewItem.getReview());
        name.setText(listViewItem.getName());


        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(context, (pos+1)+"번째 후기",Toast.LENGTH_SHORT).show();

            }
        });
        return convertView;
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public Object getItem(int position){
        return listVO.get(position);
    }

    public void addVO(Drawable icon, String review, String name){
        ListVO item = new ListVO();

        item.setPic(icon);
        item.setReview(review);
        item.setName(name);

        listVO.add(item);
    }


}