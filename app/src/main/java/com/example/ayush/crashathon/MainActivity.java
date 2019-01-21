package com.example.ayush.crashathon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    FrameLayout frame;
    FragmentManager fragmentManager;
    SharedPreferences sharedPref=null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText("Level 1");
                    LevelOneFragment fragment1 = new LevelOneFragment();
                    fragmentTransaction.replace(R.id.frame, fragment1);
                    fragmentTransaction.commit();

                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText("Level 2");
                    LevelTwoFragment fragment2 = new LevelTwoFragment();
                    fragmentTransaction.replace(R.id.frame, fragment2);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_notifications:
//                    mTextMessage.setText("Final Score");
                    FinalFragment fragment3 = new FinalFragment();
                    fragmentTransaction.replace(R.id.frame, fragment3);
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref=this.getPreferences(Context.MODE_PRIVATE);
        Boolean isLoggedIn=sharedPref.getBoolean(getString(R.string.log_in_key),false);
        if(isLoggedIn){
            Intent intent=new Intent(this, SunburnActivity.class);
            startActivity(intent);
        }

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        frame = (FrameLayout)findViewById(R.id.frame);


        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LevelOneFragment fragment1 = new LevelOneFragment();
        fragmentTransaction.add(R.id.frame, fragment1);
        fragmentTransaction.commit();
    }

}
