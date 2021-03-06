package me.micrusa.amaztimer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pixplicity.easyprefs.library.Prefs;

import me.micrusa.amaztimer.R;
import me.micrusa.amaztimer.TimerActivity;
import me.micrusa.amaztimer.defValues;
import me.micrusa.amaztimer.utils.utils;

public class PrepareActivity extends AppCompatActivity {

    private TextView timer;
    private boolean finished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        startTimer();
    }

    private void init(){
        utils.setupPrefs(this);
        utils.setLang(this, Prefs.getString(defValues.KEY_LANG, "en"));
        setContentView(R.layout.activity_prepare);
        timer = findViewById(R.id.prepareTime);
    }

    private void startTimer(){
        new CountDownTimer(6000, 1000){
            @Override
            public void onTick(long l) {
                int time = (int) l / 1000;
                timer.setText(String.valueOf(time));
                if(time < 4){
                    utils.vibrate(defValues.SHORT_VIBRATION, PrepareActivity.this);
                    if(time == 1)
                        new Handler().postDelayed(() -> {
                            timer.setText("0");
                            utils.vibrate(defValues.LONG_VIBRATION, PrepareActivity.this);
                            }, 950);
                }
            }
            @Override
            public void onFinish() {
                finished = true;
                startActivity(new Intent(PrepareActivity.this, TimerActivity.class));
                finish();
            }
        }.start();
    }

    public void onResume() {
        super.onResume();
        if(finished)
            finish();
    }
}