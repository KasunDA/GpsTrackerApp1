package com.example.admin.gpstrackerapp;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AlarmActivity extends AppCompatActivity {
    Button alarm;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        alarm=(Button)findViewById(R.id.alarm_btn);
        mp= MediaPlayer.create(this,R.raw.alarm);



    }

    public void play(View v)
    {
        mp.start();
    }
}
