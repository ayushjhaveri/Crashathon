package com.example.ayush.crashathon;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.RadioButton;
import me.itangqi.waveloadingview.WaveLoadingView;
import android.widget.Toast;
import static com.example.ayush.crashathon.Utils.readScore;
import static com.example.ayush.crashathon.Utils.updateScore;

public class level_one extends AppCompatActivity {

    boolean[] bugfound=new boolean[8];
    int count=0;
    int time=0;
    static int millis = 0;
    SharedPreferences sharedPref;
    static CountDownTimer countDownTimer;
    WaveLoadingView wlvv;

    String s1;
    String s2;
    String s3;
    String s4;
    String s5;
    String s6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_one);
        Button v1 = findViewById(R.id.v1);
        Button v2 = findViewById(R.id.v2);
        Button v3 = findViewById(R.id.v3);
        Button v4 = findViewById(R.id.v4);
        Button v5 = findViewById(R.id.v5);
        Button v6=findViewById(R.id.v6);
        Button v7=findViewById(R.id.v7);
        final RadioButton r1 = findViewById(R.id.r1);
        final RadioButton r2 = findViewById(R.id.r2);
        final EditText et1 = findViewById(R.id.et1);
        final EditText et2 = findViewById(R.id.et2);
        final EditText day = findViewById(R.id.dday);
        final EditText month=findViewById(R.id.dmonth);
        final EditText et4 = findViewById(R.id.et4);
        final EditText et5 = findViewById(R.id.et5);
        final EditText et6= findViewById(R.id.et6);
        wlvv = (WaveLoadingView) findViewById(R.id.wlv_sunburn);
        sharedPref=this.getPreferences(Context.MODE_PRIVATE);

        //set the starting time
        time=readTime();
        millis = readTime()*1000;
        FloatingActionButton fab=findViewById(R.id.instructions);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(level_one.this,Instruction.class));
            }
        });




        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bugfound[0]=!bugfound[0];

                r1.setChecked(bugfound[0]);
            }
        });

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bugfound[1]=!bugfound[1];

                r2.setChecked(bugfound[1]);
            }
        });



        v1.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {



                                      s1=et1.getText().toString();

                                      InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                      mgr.hideSoftInputFromWindow(et1.getWindowToken(), 0);
                                      et1.setText(s1);






                                  }


                              }

        );





        v2.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {


                                      sharedPref=level_one.this.getPreferences(Context.MODE_PRIVATE);
                                      Boolean isLocked=sharedPref.getBoolean("gay_bug_unlock",false); //copy

                                      if(bugfound[0]&&bugfound[1]&&!bugfound[2]&&!isLocked)
                                      {

                                          bugfound[2]=true;
                                          SharedPreferences.Editor editor=sharedPref.edit();
                                          editor.putBoolean("gay_bug_unlock", true);
                                          editor.apply();
                                          //Then, we crash the app
                                          crash();
                                      }

                                      else if(bugfound[2]&&isLocked)
                                      {
                                          Toast.makeText(getApplicationContext(),"already found",Toast.LENGTH_SHORT).show();
                                      }


                                  }
                              }

        );


        v3.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {

                                      sharedPref=level_one.this.getPreferences(Context.MODE_PRIVATE);
                                      Boolean isLocked=sharedPref.getBoolean("sachin_bug_unlock",false); //copy paste in all the bugs

                                      s2=et2.getText().toString();
                                      InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                      mgr.hideSoftInputFromWindow(et2.getWindowToken(), 0);
                                      et2.setText(s2);


                                      if(Integer.parseInt(s2)==100&&!bugfound[3]&&!isLocked)
                                      {
                                          SharedPreferences.Editor editor=sharedPref.edit();
                                          editor.putBoolean("sachin_bug_unlock", true);
                                          editor.apply();
                                          //Then, we crash the app
                                          crash();
                                          bugfound[3]=true;
                                      }

                                      else if(bugfound[3]==true&&isLocked)
                                      {
                                          Toast.makeText(getApplicationContext(),"already found",Toast.LENGTH_SHORT).show();
                                      }





                                  }
                              }

        );

        v4.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {

                                      Boolean isLocked=sharedPref.getBoolean("calendar_bug_unlock",false); //copy paste in all the bugs

                                      s3=day.getText().toString()+month.getText().toString();
                                      InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                      mgr.hideSoftInputFromWindow(day.getWindowToken(), 0);
                                      day.setText(day.getText().toString());

                                      mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                      mgr.hideSoftInputFromWindow(day.getWindowToken(), 0);
                                      month.setText(month.getText().toString());


                                      if((s3.equals("3002")||s3.equals("3102"))&&!bugfound[4]&&!isLocked)
                                      {

                                          bugfound[4]=true;
                                          SharedPreferences.Editor editor=sharedPref.edit();
                                          editor.putBoolean("calendar_bug_unlock", true);
                                          editor.apply();
                                          //Then, we crash the app
                                          crash();
                                      }

                                      else if(bugfound[4]==true)
                                      {
                                          Toast.makeText(getApplicationContext(),"already found",Toast.LENGTH_SHORT).show();
                                      }



                                  }
                              }

        );


        v5.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {


                                      Boolean isLocked=sharedPref.getBoolean("number_unlock",false); //copy paste in all the bugs

                                      s4=et4.getText().toString();
                                      InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                      mgr.hideSoftInputFromWindow(et4.getWindowToken(), 0);
                                      et4.setText(s4);


                                      if( !s4.matches("[0-9]+")&&!bugfound[5]&&!isLocked)
                                      {

                                          bugfound[5]=true;
                                          SharedPreferences.Editor editor=sharedPref.edit();
                                          editor.putBoolean("number_unlock", true);
                                          editor.apply();
                                          //Then, we crash the app
                                          crash();
                                      }

                                      else if(bugfound[5]==true&&isLocked)
                                      {
                                          Toast.makeText(getApplicationContext(),"already found",Toast.LENGTH_SHORT).show();
                                      }



                                  }
                              }

        );



        v6.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      Boolean isLocked=sharedPref.getBoolean("pin_bug_unlock",false); //copy paste in all the bugs

                                      s5=et5.getText().toString();
                                      InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                      mgr.hideSoftInputFromWindow(et5.getWindowToken(), 0);
                                      et5.setText(s5);


                                      if(Integer.parseInt(s5)/10000!=0 &&!bugfound[6]&&!isLocked)
                                      {

                                          bugfound[6]=true;
                                          SharedPreferences.Editor editor=sharedPref.edit();
                                          editor.putBoolean("pin_bug_unlocked", true);
                                          editor.apply();
                                          //Then, we crash the app
                                          crash();


                                      }

                                      else if(bugfound[6]==true&&isLocked)
                                      {
                                          Toast.makeText(getApplicationContext(),"already found",Toast.LENGTH_SHORT).show();
                                      }



                                  }
                              }

        );



        v7.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {

                                      sharedPref=level_one.this.getPreferences(Context.MODE_PRIVATE);
                                      Boolean isLocked=sharedPref.getBoolean("line_bug_unlock",false); //copy paste in all the bugs

                                      s6=et6.getText().toString();
                                      InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                      mgr.hideSoftInputFromWindow(et6.getWindowToken(), 0);
                                      et6.setText(s6);


                                      if(s6.contains("\n")&&!bugfound[7]&&!isLocked)
                                      {

                                          bugfound[7]=true;
                                          SharedPreferences.Editor editor=sharedPref.edit();
                                          editor.putBoolean("line_bug_unlock", true);
                                          editor.apply();
                                          //Then, we crash the app
                                          crash();

                                      }

                                      else if(bugfound[7]==true&&isLocked)
                                      {
                                          Toast.makeText(getApplicationContext(),"already found",Toast.LENGTH_SHORT).show();
                                      }





                                  }
                              }

        );




        countDownTimer=new CountDownTimer(time*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                wlvv.setCenterTitle("" + millisUntilFinished / 60000 + " : " + String.valueOf(millisUntilFinished/1000 - millisUntilFinished / 60000));

                wlvv.setProgressValue((int)millisUntilFinished*100/Constant.level2_time/5);
                millis = (int) millisUntilFinished;

            }

            public void onFinish() {
                //set the game_over sharedpref value as true and direct to the ScoreActivity
                SharedPreferences.Editor editor=sharedPref.edit();
                editor.putBoolean("game_over_key",true);
                editor.apply();
                Intent intent=new Intent(level_one.this, Level2login.class);
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
        timeText=sharedPref.getString("timer_text_key",""+60);
        return Integer.parseInt(timeText);
    }

    public void writeTime(){
        sharedPref=this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.putString("timer_text_key", ""+millis/1000);
        editor.apply();
    }




}

