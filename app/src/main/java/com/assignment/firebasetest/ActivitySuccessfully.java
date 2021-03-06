package com.assignment.firebasetest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ActivitySuccessfully extends AppCompatActivity {

    private ImageView done;
    private Drawable drawable;

    private AnimatedVectorDrawableCompat avd;
    private AnimatedVectorDrawable avd2;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfully);

        done = findViewById(R.id.done);

        //Full Screen Activity
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        Button btn_goto_login;
        btn_goto_login = findViewById(R.id.btn_login_signin);

        btn_goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        drawable = done.getDrawable();
        if(drawable instanceof AnimatedVectorDrawableCompat){
            avd = (AnimatedVectorDrawableCompat) drawable;
            avd.start();
        }
        else if(drawable instanceof  AnimatedVectorDrawable){
            avd2 = (AnimatedVectorDrawable) drawable;
            avd2.start();
        }
    }
}
