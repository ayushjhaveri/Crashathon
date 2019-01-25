package com.example.ayush.crashathon;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Calendar;

import me.itangqi.waveloadingview.WaveLoadingView;

import static com.example.ayush.crashathon.Utils.readScore;
import static com.example.ayush.crashathon.Utils.updateScore;

public class SunburnActivity extends AppCompatActivity {

    int count=0;
    int time=0;
    static int millis = 0;
    SharedPreferences sharedPref;
    static CountDownTimer countDownTimer;
    WaveLoadingView wlv;
    Button showExplicitButton;
    Calendar mCalendar = Calendar.getInstance();
    ImageView explicitImage;
    Boolean isImageBlurred = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunburn);

        wlv = (WaveLoadingView) findViewById(R.id.wlv_sunburn);
        explicitImage = (ImageView) findViewById(R.id.explicit_image);
        showExplicitButton = (Button) findViewById(R.id.show_explicit_button);

        //Get a handle to a sharedpref object
        sharedPref=this.getPreferences(Context.MODE_PRIVATE);

        //set the starting time
        time=readTime();
        millis = readTime()*1000;

        Boolean gameInterrupted=sharedPref.getBoolean(getString(R.string.interruption_key),false);
        if(gameInterrupted){
            SharedPreferences.Editor editor=sharedPref.edit();
            editor.putBoolean(getString(R.string.interruption_key),false);
            editor.commit();
            int currentSystemTime=(int) Calendar.getInstance().getTime().getTime();
            time-=(currentSystemTime-sharedPref.getInt(getString(R.string.system_time),currentSystemTime))/1000;
        }

        //Check to see if the game has already ended
        //if yes, then proceed to ScoreActivity autmotically, if no, then stay
        Boolean isGameOver=sharedPref.getBoolean(getString(R.string.game_over_key),false);
        if(isGameOver){
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        }


        showExplicitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showExplicitButton.setVisibility(View.GONE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(SunburnActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        mCalendar.set(Calendar.YEAR, year);
                        mCalendar.set(Calendar.MONTH, monthOfYear);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        if (year > 2001) {
                            //We begin by checking if the feature has been locked previously
                            sharedPref=SunburnActivity.this.getPreferences(Context.MODE_PRIVATE);
                            Boolean isLocked=sharedPref.getBoolean(getString(R.string.underage_bug_lock),false);
                            if(isLocked){
                                Snackbar.make(v, "You've already used this feature", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }else{
                                //First, lock the feature so that it can't be used more than once
                                SharedPreferences.Editor editor=sharedPref.edit();
                                editor.putBoolean(getString(R.string.underage_bug_lock), true);
                                editor.apply();
                                //Then, we crash the app
                                crash();
                                explicitImage.setVisibility(View.GONE);
                            }
                        } else {
                            isImageBlurred = false;
                            explicitImage.setImageResource(R.drawable.rohit);
                        }
                    }
                }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        explicitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isImageBlurred) {
                    return;
                } else {
                    Intent intent = new Intent(view.getContext(), FullScreenImage.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(SunburnActivity.this, (View) explicitImage, getString(R.string.product_transition));
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                    }
                }
            }
        });



        //fetching the score from the local file
        String scoreString="";
        File file = new File(this.getExternalFilesDir(null), "ScoreData");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            scoreString=br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(scoreString.isEmpty()){
            updateScore(count, this);
        }else{
            count=readScore(this);
        }

        countDownTimer=new CountDownTimer(time*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                wlv.setCenterTitle("" + millisUntilFinished / 60000 + " : " + String.valueOf(millisUntilFinished/1000 - millisUntilFinished / 60000));

                wlv.setProgressValue((int)millisUntilFinished*100/Constant.level2_time);
                millis = (int) millisUntilFinished;

            }

            public void onFinish() {
                //set the game_over sharedpref value as true and direct to the ScoreActivity
                SharedPreferences.Editor editor=sharedPref.edit();
                editor.putBoolean(getString(R.string.game_over_key),true);
                editor.apply();
                Intent intent=new Intent(SunburnActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }.start();

    }

    public void crash(){
        count++;
        updateScore(count, this);
        writeTime();
        Toast.makeText(this, "Crash!!!", Toast.LENGTH_SHORT).show();
    }

    public int readTime(){
        String timeText=null;
        sharedPref=this.getPreferences(Context.MODE_PRIVATE);
        timeText=sharedPref.getString(getString(R.string.timer_text_key),""+60);
        return Integer.parseInt(timeText);
    }

    public void writeTime(){
        sharedPref=this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.putString(getString(R.string.timer_text_key), ""+millis/1000);
        editor.apply();
    }

}
