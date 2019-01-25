package com.example.ayush.crashathon;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;


import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static com.example.ayush.crashathon.Utils.updateScore;

public class FullScreenImage extends AppCompatActivity {

    int count=0;
    int time=0;
    static int millis = 0;
    SharedPreferences sharedPref;
    PhotoView mPhotoView;
    PhotoViewAttacher mAttacher;
    Boolean pinchCrash = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_screen_image);
        mPhotoView = (PhotoView) findViewById(R.id.imageViewFullScreen);
        //Get a handle to a sharedpref object
        sharedPref=this.getPreferences(Context.MODE_PRIVATE);

        mAttacher = new PhotoViewAttacher(mPhotoView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //delaying the transition until the view has been laid out
            postponeEnterTransition();
        }
        Picasso.with(this)
                .load(R.drawable.rohit)
                .into(mPhotoView, new Callback() {
                    @Override
                    public void onSuccess() {
                        mAttacher.update();
                        scheduleStartPostponedTransition(mPhotoView);
                    }

                    @Override
                    public void onError() {

                    }
                });
        final ScaleGestureDetector mScaleDetector = new ScaleGestureDetector(this, new MyPinchListener());
        mPhotoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mScaleDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    /*For scheduling a postponed transition after the proper measures of the view are done
    and the view has been properly laid out in the View hierarchy*/
    private void scheduleStartPostponedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startPostponedEnterTransition();
                        }
                        return true;
                    }
                });
    }
    class MyPinchListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (pinchCrash) {
                //We begin by checking if the feature has been locked previously
                sharedPref=FullScreenImage.this.getPreferences(Context.MODE_PRIVATE);
                Boolean isLocked=sharedPref.getBoolean(getString(R.string.zoom_bug_lock),false);
                if(isLocked){
                    Toast.makeText(FullScreenImage.this, "You've already used this feature", Toast.LENGTH_SHORT).show();
                }else{
                    //First, lock the feature so that it can't be used more than once
                    SharedPreferences.Editor editor=sharedPref.edit();
                    editor.putBoolean(getString(R.string.zoom_bug_lock), true);
                    editor.apply();
                    //Then, we crash the app
                    crash();
                }
                mPhotoView.setOnTouchListener(null);
                pinchCrash = false;
            }
            return true;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //We begin by checking if the feature has been locked previously
            sharedPref=FullScreenImage.this.getPreferences(Context.MODE_PRIVATE);
            Boolean isLocked=sharedPref.getBoolean(getString(R.string.landscape_bug_lock),false);
            if(isLocked){
                Toast.makeText(FullScreenImage.this, "You've already used this feature", Toast.LENGTH_SHORT).show();
            }else{
                //First, lock the feature so that it can't be used more than once
                SharedPreferences.Editor editor=sharedPref.edit();
                editor.putBoolean(getString(R.string.landscape_bug_lock), true);
                editor.apply();
                //Then, we crash the app
                crash();
            }
        }
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
