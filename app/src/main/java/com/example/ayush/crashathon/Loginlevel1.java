package com.example.ayush.crashathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Loginlevel1 extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginlevel1);

        sharedPreferences = getSharedPreferences("Crashathon", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("level_one_login_current_key", true);
        editor.apply();

        Button button1=findViewById(R.id.buttonlogin);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.level_one_login_current_key), false);
                editor.putBoolean(getString(R.string.level_one_current_key), true);
                Log.d("Hello", "HeyLoginLevel1");
                editor.commit();

                startActivity(new Intent(Loginlevel1.this,Instructions.class));
                finish();
            }
        });
    }
}
