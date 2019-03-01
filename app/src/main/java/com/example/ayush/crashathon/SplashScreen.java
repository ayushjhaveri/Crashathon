package com.example.ayush.crashathon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final SharedPreferences sharedPref=getSharedPreferences("Crashathon", MODE_PRIVATE);
        final boolean isLevelOneLoginGoingOn = sharedPref.getBoolean(getString(R.string.level_one_login_current_key), false);
        final boolean isLevelTwoLoginGoingOn = sharedPref.getBoolean(getString(R.string.level_two_login_current_key), false);
        final boolean isLevelOneGoingOn = sharedPref.getBoolean(getString(R.string.level_one_current_key), false);
        final boolean isLevelTwoGoingOn = sharedPref.getBoolean(getString(R.string.level_two_current_key), false);
        final boolean isGameOver = sharedPref.getBoolean(getString(R.string.game_over_key), false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isLevelOneLoginGoingOn) {
                    Log.d("Hello", "Hey1");
                    Intent intent=new Intent(SplashScreen.this,Loginlevel1.class);
                    startActivity(intent);
                    finish();
                } else if (isLevelOneGoingOn) {
                    Log.d("Hello", "Hey2");
                    Intent intent=new Intent(SplashScreen.this,level_one.class);
                    startActivity(intent);
                    finish();
                } else if (isLevelTwoLoginGoingOn) {
                    Log.d("Hello", "Hey3");
                    Intent intent=new Intent(SplashScreen.this,Level2login.class);
                    startActivity(intent);
                    finish();
                } else if (isLevelTwoGoingOn) {
                    Log.d("Hello", "Hey4");
                    Intent intent=new Intent(SplashScreen.this,SunburnActivity.class);
                    startActivity(intent);
                    finish();
                } else if (isGameOver) {
                    Intent intent=new Intent(SplashScreen.this,Leaderboard.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.d("Hello", "Hey5");
                    Intent intent=new Intent(SplashScreen.this,Loginlevel1.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);
    }

}
