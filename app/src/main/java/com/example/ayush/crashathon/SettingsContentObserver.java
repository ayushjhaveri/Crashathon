package com.example.ayush.crashathon;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class SettingsContentObserver extends ContentObserver {
    private AudioManager audioManager;
    private Context mContext;
    boolean isVolume=false;

    public SettingsContentObserver(Context context, Handler handler) {
        super(handler);
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        this.mContext=context;
    }

    @Override
    public boolean deliverSelfNotifications() {
        return false;
    }

    @Override
    public void onChange(boolean selfChange) {
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);


        if(currentVolume == audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)){
            if(isVolume) {
                Toast.makeText(mContext, "You have already found this bug", Toast.LENGTH_SHORT).show();
                //the bug has already been found

            }
            else{
                Toast.makeText(mContext, "Bug 2 Found", Toast.LENGTH_SHORT).show();
                isVolume = true;
                //crash()
            }
        }

//        Log.d("vol", "Volume now " + currentVolume);
    }


}