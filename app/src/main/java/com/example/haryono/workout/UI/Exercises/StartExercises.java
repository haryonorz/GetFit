package com.example.haryono.workout.UI.Exercises;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.haryono.workout.DataBaseHelper;
import com.example.haryono.workout.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * Created by Haryono on 6/10/2017.
 */

public class StartExercises extends AppCompatActivity implements View.OnClickListener {

    private long timeCountInMilliSeconds = 18000;
    private long timeRemaining = 0;
    private int jumlahUlang = 0;
    private int position=0;
    private int jumlah;
    private int timeAnimation = 250;
    private int workoutPart = 1;
    private boolean restCheck = true;
    private boolean soundOn = true;
    private boolean pause = false;
    private boolean firstTime = true;
    private String type;
    private int[] layouts;

    private ImageView WorkoutAnim, SoundOnOff, PausePlay;
    private TextView StatusWorkout, WorkoutName, Time, NotifWorkout;
    private CountDownTimer countDownTimer;
    Handler handler = new Handler();
    private ProgressBar progressBarCircle;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    LinearLayout help1, help2;

    MediaPlayer mp;
    DataBaseHelper database;

    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    private TimerStatus timerStatus = TimerStatus.STOPPED;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_start_exercieses2);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            type = extras.getString("typeExercises1");
            if(type.equals("Abs")) position+=13;
        }

        try {
            database = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        findViewById();
        initListeners();
        setupActionBar();
        start();
        animationWorkout();

        layouts = new int[]{R.layout.vp_help1,R.layout.vp_help2};

    }

    private void animationWorkout(){
        jumlah = 1;

        if(database.getExerciseWorkoutAnimation(position, type).get(2) != null) jumlah=2 ;
        if(database.getExerciseWorkoutAnimation(position, type).get(3) != null) jumlah=3 ;
        if(database.getExerciseWorkoutAnimation(position, type).get(4) != null) jumlah=4 ;
        if(database.getExerciseWorkoutAnimation(position, type).get(5) != null) jumlah=5 ;
        if(database.getExerciseWorkoutAnimation(position, type).get(6) != null) jumlah=6 ;
        if(database.getExerciseWorkoutAnimation(position, type).get(7) != null) jumlah=7 ;
        if(database.getExerciseWorkoutAnimation(position, type).get(8) != null) jumlah=8 ;
        if(database.getExerciseWorkoutAnimation(position, type).get(9) != null) jumlah=9 ;
        if(database.getExerciseWorkoutAnimation(position, type).get(10) != null) jumlah=10 ;
        if(database.getExerciseWorkoutAnimation(position, type).get(11) != null) jumlah=11 ;
        if(database.getExerciseWorkoutAnimation(position, type).get(12) != null) jumlah=12 ;

        if(jumlah<=5) timeAnimation = 500;
        if(jumlah<=12) timeAnimation = 500;

        final String[] animImage = new String[jumlah];
        for (int j = 0; j < jumlah; j++){
            animImage[j] = database.getExerciseWorkoutAnimation(position, type).get(1+j);
        }
        runnable = new Runnable() {
            int i = 0;
            public void run() {
                try {
                    // get input stream
                    InputStream ims = getAssets().open("workout_image/" + animImage[i]);
                    // load image as Drawable
                    Drawable d = Drawable.createFromStream(ims, null);
                    // set image to ImageView
                    WorkoutAnim.setImageDrawable(d);

                } catch (IOException ex) {
                }
                i++;
                if (i > animImage.length - 1) {
                    i = 0;
                }
                handler.postDelayed(this, timeAnimation);
            }
        };
        handler.postDelayed(runnable, timeAnimation);
    }

    private void start() {
        setTimerValues();
        setProgressBarValues();
        PausePlay.setImageResource(R.drawable.pause2);
        timerStatus = TimerStatus.STARTED;

        mp = MediaPlayer.create(StartExercises.this,R.raw.whistle_long);
        mp.start();

        startCountDownTimer(timeCountInMilliSeconds);
    }

    private void findViewById() {
        WorkoutAnim = (ImageView) findViewById(R.id.ivWorkoutAnimation);
        SoundOnOff = (ImageView) findViewById(R.id.ivSoundOnOff);
        PausePlay = (ImageView) findViewById(R.id.ivPausePlay);
        StatusWorkout = (TextView) findViewById(R.id.tvStatusWorkout);
        WorkoutName = (TextView) findViewById(R.id.tvWorkoutName);
        Time = (TextView) findViewById(R.id.tvTime);
        NotifWorkout = (TextView) findViewById(R.id.tvNotifWorkout);
        progressBarCircle = (ProgressBar) findViewById(R.id.progressBarCircle);

        Time.setText(hmsTimeFormatter(timeCountInMilliSeconds));
        WorkoutName.setText(database.getExerciseWorkoutAnimation(position, type).get(0));
        StatusWorkout.setText(workoutPart+"/"+database.getExerciseWorkoutAnimation(position, type).size());
    }

    private void initListeners() {
        PausePlay.setOnClickListener(this);
        SoundOnOff.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivPausePlay:
                startPause();
                break;
            case R.id.ivSoundOnOff:
                soundOnOff();
                break;
        }
    }

    private void soundOnOff() {
        if (soundOn){
            SoundOnOff.setImageResource(R.drawable.mute2);
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
            soundOn = false;
        }
        else {
            SoundOnOff.setImageResource(R.drawable.speaker2);
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            soundOn = true;
        }

    }

    private void startPause() {
        if (timerStatus == TimerStatus.STOPPED) {
            if(pause){
                setTimerValues();
                PausePlay.setImageResource(R.drawable.pause2);
                timerStatus = TimerStatus.STARTED;
                startCountDownTimer(timeRemaining);
                setProgressBarPauseValues();
                pause = false;
                animationWorkout();
                if (restCheck){
                    NotifWorkout.setText("Istirahat & Bersiap untuk memulai");
                }
                else {
                    NotifWorkout.setText("Babak 1/1");
                }
            }
            else {
                setTimerValues();
                setProgressBarValues();
                PausePlay.setImageResource(R.drawable.pause2);
                timerStatus = TimerStatus.STARTED;
                startCountDownTimer(timeCountInMilliSeconds);
            }
        } else {
            PausePlay.setImageResource(R.drawable.play2);
            timerStatus = TimerStatus.STOPPED;
            pause = true;
            NotifWorkout.setText("Berhenti Sejenak");
            stopCountDownTimer();
            handler.removeCallbacks(runnable);
        }
    }

    private final void startCountDownTimer(long time) {
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                Time.setText(hmsTimeFormatter(millisUntilFinished));
                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));
                timeRemaining = millisUntilFinished;
                long timeLeft = millisUntilFinished / 1000;

                if (timeLeft==10 && firstTime==true){
                    mp = MediaPlayer.create(StartExercises.this,R.raw.time10);
                    mp.start();
                    firstTime=false;
                }
                else{
                    if (timeLeft==3){
                        mp = MediaPlayer.create(StartExercises.this,R.raw.time3);
                        mp.start();
                    }
                    if (timeLeft==2){
                        mp = MediaPlayer.create(StartExercises.this,R.raw.time2);
                        mp.start();
                    }
                    if (timeLeft==1){
                        mp = MediaPlayer.create(StartExercises.this,R.raw.time1);
                        mp.start();
                    }
                }

            }

            @Override
            public void onFinish() {
                if(jumlahUlang == database.getExerciseWorkoutAnimation(position, type).size() ){

                    mp = MediaPlayer.create(StartExercises.this,R.raw.finish);
                    mp.start();

                    DialogForm();

                    Time.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                    setProgressBarValues();
                    PausePlay.setImageResource(R.drawable.play2);
                    timerStatus = TimerStatus.STOPPED;
                    return;
                }
                if (restCheck){
                    timeCountInMilliSeconds = 31000;
                    stopCountDownTimer();

                    mp = MediaPlayer.create(StartExercises.this,R.raw.whistle_long);
                    mp.start();
                    if (mp.isPlaying()){
                        mp = MediaPlayer.create(StartExercises.this,R.raw.go);
                        mp.start();
                    }

                    startCountDownTimer(timeCountInMilliSeconds);

                    Time.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                    NotifWorkout.setText("Babak 1/1");
                    setProgressBarValues();
                    restCheck = false;
                    workoutPart++;
                    jumlahUlang++;
                }
                else{
                    timeCountInMilliSeconds = 11000;
                    stopCountDownTimer();
                    //change image workout
                    position++;
                    handler.removeCallbacks(runnable);
                    animationWorkout();
                    StatusWorkout.setText(workoutPart+"/"+database.getExerciseWorkoutAnimation(position, type).size());
                    WorkoutName.setText(database.getExerciseWorkoutAnimation(position, type).get(0));
                    //start time rest

                    mp = MediaPlayer.create(StartExercises.this,R.raw.rest);
                    mp.start();

                    startCountDownTimer(timeCountInMilliSeconds);

                    Time.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                    NotifWorkout.setText("Istirahat & Bersiap untuk memulai");
                    setProgressBarValues();
                    restCheck = true;
                }
            }

        }.start();
        countDownTimer.start();
    }

    private void DialogForm() {
        dialog = new AlertDialog.Builder(StartExercises.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.alert_complete, null);
        dialog.setView(dialogView);

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intructionIntent = new Intent(StartExercises.this, ListExercises.class);
                startActivity(intructionIntent);
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void setTimerValues() {
        int time = 15;
        timeCountInMilliSeconds = time * 1000;
    }

    private void stopCountDownTimer() {
        countDownTimer.cancel();
    }

    private void setProgressBarValues() {

        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);

    }

    private void setProgressBarPauseValues() {

        progressBarCircle.setMax((int) timeRemaining / 1000);
        progressBarCircle.setProgress((int) timeRemaining / 1000);

    }

    private String hmsTimeFormatter(long milliSeconds) {
        String hms = String.format("%02d''",
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds));

        return hms;
    }

    public void onBackPressed(){
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(StartExercises.this);
        alert.setMessage("Apakah anda yakin ingin berhenti latihan?");
        alert.setCancelable(true);
        pause = false;
        timerStatus = TimerStatus.STARTED;
        startPause();
        alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopCountDownTimer();
                mp.release();
                mp.stop();
                Intent intructionIntent = new Intent(StartExercises.this, ListExercises.class);
                startActivity(intructionIntent);
                finish();
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                pause = true;
                timerStatus = TimerStatus.STOPPED;
                startPause();
            }
        });
        android.support.v7.app.AlertDialog al = alert.create();
        al.show();
    }

    private void setupActionBar(){
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.c_titleview, null);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),"Roboto-Medium_0.ttf");
        ((TextView)v.findViewById(R.id.title)).setTypeface(custom_font);
        ((TextView)v.findViewById(R.id.title)).setText("Exercises");
        getSupportActionBar().setCustomView(v);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_help, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mhelp:
                dialog = new AlertDialog.Builder(StartExercises.this);
                inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.vp_main, null);
                dialog.setView(dialogView);
                pause = false;
                timerStatus = TimerStatus.STARTED;
                startPause();

                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pause = true;
                        timerStatus = TimerStatus.STOPPED;
                        startPause();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}