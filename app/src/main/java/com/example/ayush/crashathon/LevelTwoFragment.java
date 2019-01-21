package com.example.ayush.crashathon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class LevelTwoFragment extends Fragment {


    Button btn_login;
    EditText edit_pw;
    SharedPreferences sharedPref;

    public LevelTwoFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_level_two, container, false);

        edit_pw = (EditText)v.findViewById(R.id.edit_pw);
        btn_login=(Button)v.findViewById(R.id.button);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_pw.getText().toString().equals("1234")){
                    SharedPreferences.Editor editor=sharedPref.edit();
                    editor.putBoolean(getString(R.string.log_in_key),true);
                    editor.apply();
                    startActivity(new Intent(getContext(), SunburnActivity.class));

                }
            }
        });

        return v;
    }





}
