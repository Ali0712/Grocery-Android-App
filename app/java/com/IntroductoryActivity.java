package com.example.yourmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import android.view.animation.AnimationUtils;


public class IntroductoryActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 4000;

    ImageView startBg, appName;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_introductory);

//        startBg = findViewById(R.id.start_img);
        appName = findViewById(R.id.app_name);
        lottieAnimationView = findViewById(R.id.lottieAnimationView);

//        startBg.animate().translationX(-1600).setDuration(1000).setStartDelay(4000);
        appName.animate().translationX(-1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationX(-1400).setDuration(1000).setStartDelay(4000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroductoryActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}