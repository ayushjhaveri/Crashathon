package com.example.ayush.crashathon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class SunburnActivity extends AppCompatActivity {

    int count=0;
    int time=0;
    static int millis = 0;
    SharedPreferences sharedPref;
    static CountDownTimer countDownTimer;
    WaveLoadingView wlv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunburn);

        wlv = (WaveLoadingView) findViewById(R.id.wlv_sunburn);

        //Get a handle to a sharedpref object
        sharedPref=this.getPreferences(Context.MODE_PRIVATE);

        //set the starting time
        time=readTime();

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
            updateScore(count);
        }else{
            count=readScore();
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

    public boolean updateScore(int count){
        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.w("FileUtils", "Storage not available or read only");
            return false;
        }

        // Create a path where we will place our List of objects on external storage
        File file = new File(this.getExternalFilesDir(null), "ScoreData");
        PrintStream p = null; // declare a print stream object
        boolean success = false;

        try {
            OutputStream os = new FileOutputStream(file);
            // Connect print stream to the output stream
            p = new PrintStream(os);
            p.println(""+count);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != p)
                    p.close();
            } catch (Exception ex) {
            }
        }
        return success;
    }

    public int readScore(){

        int score=0;

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly())
        {
            Log.w("FileUtils", "Storage not available or read only");
            return 13;
        }

        FileInputStream fis = null;

        try
        {
            File file = new File(this.getExternalFilesDir(null), "ScoreData");
            fis = new FileInputStream(file);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                Log.w("FileUtils", "File data: " + strLine);
                score=Integer.parseInt(""+strLine);
            }
            in.close();
        }
        catch (Exception ex) {
            Log.e("FileUtils", "failed to load file", ex);
        }
        finally {
            try {if (null != fis) fis.close();} catch (IOException ex) {}
        }

        return score;

    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public void crash(){
        count++;
        updateScore(count);
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
