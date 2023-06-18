package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {


    ImageView imgIcon;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgIcon=findViewById(R.id.imgIcon);
        tv=findViewById(R.id.tv);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },3000);
        Animation bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        imgIcon.startAnimation(bounceAnimation);
        imgIcon.startAnimation(bounceAnimation);

    }
}