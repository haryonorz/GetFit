package com.example.haryono.workout.UI.Workout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haryono.workout.DataBaseHelper;
import com.example.haryono.workout.Font.CustomTextView;
import com.example.haryono.workout.R;

import java.io.IOException;
import java.io.InputStream;

public class Workout extends AppCompatActivity {
    private ImageView WorkoutAnim, Video;
    private CustomTextView Muscles, Technique, Description;
    private int position;
    private int timeAnimation = 250;
    Handler handler = new Handler();
    private DataBaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_workout);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            position = extras.getInt("typeWorkout");
            Log.e("info sudah pindah : ", String.valueOf(position));
        }

        try {
            database = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        findViewById();
        setupActionBar();
        animationWorkout();
        setData();

        Video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(database.getDescWorkout(position).get(4).toString())));
            }
        });

    }


    private void findViewById(){
        WorkoutAnim = (ImageView) findViewById(R.id.ivWorkoutAnimation);
        Muscles = (CustomTextView) findViewById(R.id.tvMuscles);
        Video = (ImageView) findViewById(R.id.ivVideo);
        Technique = (CustomTextView) findViewById(R.id.ctTechnique);
        Description = (CustomTextView) findViewById(R.id.ctDescription);
    }

    private void setData (){
        Muscles.setText(database.getDescWorkout(position).get(1));
        Technique.setText(database.getDescWorkout(position).get(2));
        Description.setText(database.getDescWorkout(position).get(3));
    }

    private void animationWorkout(){
        int jumlah = 1;
        if(database.getDescWorkout(position).get(6) != null) jumlah=2 ;
        if(database.getDescWorkout(position).get(7) != null) jumlah=3 ;
        if(database.getDescWorkout(position).get(8) != null) jumlah=4 ;
        if(database.getDescWorkout(position).get(9) != null) jumlah=5 ;
        if(database.getDescWorkout(position).get(10) != null) jumlah=6 ;
        if(database.getDescWorkout(position).get(11) != null) jumlah=7 ;
        if(database.getDescWorkout(position).get(12) != null) jumlah=8 ;
        if(database.getDescWorkout(position).get(13) != null) jumlah=9 ;
        if(database.getDescWorkout(position).get(14) != null) jumlah=10 ;
        if(database.getDescWorkout(position).get(15) != null) jumlah=11 ;
        if(database.getDescWorkout(position).get(16) != null) jumlah=12 ;

        if(jumlah<=5) timeAnimation = 500;
        if(jumlah<=12) timeAnimation = 500;


        final String[] animImage = new String[jumlah];
        for (int j = 0; j < jumlah; j++){
            animImage[j] = database.getDescWorkout(position).get(5+j);
        }
        Runnable runnable = new Runnable() {
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
                handler.postDelayed(this, 250);
            }
        };
        handler.postDelayed(runnable, 250);
    }

    private void setupActionBar(){
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.c_titleview, null);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),"Roboto-Medium_0.ttf");
        ((TextView)v.findViewById(R.id.title)).setTypeface(custom_font);
        ((TextView)v.findViewById(R.id.title)).setText(database.getDescWorkout(position).get(0).toString());
        getSupportActionBar().setCustomView(v);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }
}
