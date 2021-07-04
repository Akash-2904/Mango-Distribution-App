package com.ajit.mangodistributionsystem.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import com.ajit.mangodistributionsystem.R;

public class SplashActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        findViewById(R.id.splash_image).setAnimation(AnimationUtils.loadAnimation(this, R.anim.animation_top));
        findViewById(R.id.splash_text).setAnimation(AnimationUtils.loadAnimation(this, R.anim.animation_bottom));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loggedIn()) {
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, RegisterActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 3000);

    }

    private boolean loggedIn() {

        SharedPreferences sharedPreferences = this.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean("logged_in", false)) {
            return true;
        } else {
            return false;
        }
    }
}