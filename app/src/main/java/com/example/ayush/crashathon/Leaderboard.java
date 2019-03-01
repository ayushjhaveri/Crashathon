package com.example.ayush.crashathon;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;

import java.util.ArrayList;

public class Leaderboard extends AppCompatActivity {
    private ListView listView;
    private Adapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        listView = findViewById(R.id.listview);
        FloatingActionButton floatingActionButton = findViewById(R.id.developers);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Leaderboard.this, Developers.class));
                finish();
            }
        });
        TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.developers), "Check out the developers here", "Well,obviously we'd give credits to ourselves").outerCircleColor(R.color.black).targetCircleColor(R.color.white).titleTextSize(20).icon(getDrawable(R.drawable.ic_developer_mode_black_24dp)).textColor(R.color.white));
        ArrayList<Data> data = new ArrayList<>();
        data.add(new Data("1", "ayyush", "40"));
        data.add(new Data("1", "ayyush", "40"));
        data.add(new Data("1", "ayyush", "40"));
        data.add(new Data("1", "ayyush", "40"));
        data.add(new Data("1", "ayyush", "40"));
        data.add(new Data("1", "ayyush", "40"));
        data.add(new Data("1", "ayyush", "40"));
        data.add(new Data("1", "ayyush", "40"));
        data.add(new Data("1", "ayyush", "40"));
        data.add(new Data("1", "ayyush", "40"));
        data.add(new Data("1", "ayyush", "40"));
        data.add(new Data("1", "ayyush", "40"));
        data.add(new Data("1", "ayyush", "40"));
        data.add(new Data("1", "ayyush", "40"));
        data.add(new Data("1", "ayyush", "40"));
        data.add(new Data("1", "ayyush", "40"));
        adapter = new Adapter(Leaderboard.this, data);
        listView.setAdapter(adapter);

    }
}
