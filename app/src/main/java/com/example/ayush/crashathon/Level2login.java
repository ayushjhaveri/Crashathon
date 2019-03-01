package com.example.ayush.crashathon;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class Level2login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2login);
        TextView textView = findViewById(R.id.congrats);
        TextView score = findViewById(R.id.score);


        Animation animation, animation1;
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.textview);
        textView.startAnimation(animation);
        animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        score.startAnimation(animation1);
        Button button=findViewById(R.id.login2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Level2login.this,Instructionslevel2.class));
                finish();
            }
        });

    }
}
