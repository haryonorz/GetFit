package com.example.haryono.workout.UI.Exercises;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.haryono.workout.Adapter.ExercisesWorkoutAdapater;
import com.example.haryono.workout.DataBaseHelper;
import com.example.haryono.workout.Font.CustomTextView;
import com.example.haryono.workout.Font.CustomTextView2;
import com.example.haryono.workout.Helper_listview.Helper;
import com.example.haryono.workout.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Haryono on 5/27/2017.
 */

public class Exercises extends AppCompatActivity {

    private DataBaseHelper database;
    private int position;
    private String catgWorkout;
    private ImageView ivExercises;
    private Button Start;
    private CustomTextView2 Metode;
    private CustomTextView Description, Duration, Frequention;
    private ListView ExercisesWorkoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_exercises);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            position = extras.getInt("typeExercises");
            if (position == 0){
                catgWorkout = "FullBody";
            }
            else {
                catgWorkout = "Abs";
            }
        }

        try {
            database = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        findViewById();
        setupActionBar();
         setData();

        ExercisesWorkoutAdapater adapter = new ExercisesWorkoutAdapater(this,
                database.getExerciseWorkoutList(catgWorkout).toArray
                (new String[database.getExerciseWorkoutList(catgWorkout).size()]));
        ExercisesWorkoutList.setAdapter(adapter);
        Helper.getListViewSize(ExercisesWorkoutList);

        startExercises();

    }

    private void startExercises() {
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercises.this, StartExercises.class);
                intent.putExtra("typeExercises1",catgWorkout);
                Log.e("info : ", String.valueOf(catgWorkout));
                startActivity(intent);
                finish();
            }
        });
    }

    private void setData() {
        try {
            // get input stream
            InputStream ims = getAssets().open("exercises_image/" + database.getDescExercises(position).get(1));
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            ivExercises.setImageDrawable(d);

        } catch (IOException ex) {
        }
        Metode.setText(database.getDescExercises(position).get(2));
        Description.setText(database.getDescExercises(position).get(3));
        Duration.setText(database.getDescExercises(position).get(4));
        Frequention.setText(database.getDescExercises(position).get(5));
    }

    private void findViewById() {
        ivExercises = (ImageView) findViewById(R.id.ivExercises);
        Metode = (CustomTextView2) findViewById(R.id.ctMetode);
        Description = (CustomTextView) findViewById(R.id.ctDescription);
        Duration = (CustomTextView) findViewById(R.id.ctDuration);
        Frequention = (CustomTextView) findViewById(R.id.ctFrequention);
        ExercisesWorkoutList = (ListView) findViewById(R.id.exercisesWorkoutList);
        Start = (Button) findViewById(R.id.btnStart);

    }

    private void setupActionBar(){
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.c_titleview, null);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),"Roboto-Medium_0.ttf");
        ((TextView)v.findViewById(R.id.title)).setTypeface(custom_font);
        ((TextView)v.findViewById(R.id.title)).setText(database.getDescExercises(position).get(0).toString());
        getSupportActionBar().setCustomView(v);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }
}
