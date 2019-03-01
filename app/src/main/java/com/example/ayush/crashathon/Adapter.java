package com.example.ayush.crashathon;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter<Data> {
private Context mcontext;
private List<Data> mlist;
    public Adapter(@NonNull Context context, ArrayList<Data> list) {
        super(context,0,list);
        mcontext=context;
        mlist=list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
View listitem= convertView;
if (listitem==null)
listitem= LayoutInflater.from(mcontext).inflate(R.layout.listview,parent,false);
Data currentuser=mlist.get(position);
    TextView rank=(TextView)listitem.findViewById(R.id.position);
    rank.setText(currentuser.getPosition());
    TextView nickname=(TextView)listitem.findViewById(R.id.nickname);
    nickname.setText(currentuser.getNickname());
    TextView points=(TextView)listitem.findViewById(R.id.points);
    points.setText(currentuser.getScore());
return listitem;


}
    }

