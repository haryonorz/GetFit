package com.example.haryono.workout.UI.Exercises;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.haryono.workout.Adapter.ExercisesAdapter;
import com.example.haryono.workout.DataBaseHelper;
import com.example.haryono.workout.R;

import java.io.IOException;

/**
 * Created by Haryono on 6/2/2017.
 */

public class ListExercises extends AppCompatActivity {

    ListView list;
    DataBaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_list_exercises);

        try {
            database = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setupActionBar();

        ExercisesAdapter adapter = new ExercisesAdapter(this, database.getAllExercises().toArray
                (new String[database.getAllExercises().size()]), database.getAllExercisesMetode().toArray
                (new String[database.getAllExercisesMetode().size()]), database.getAllExercisesImage().toArray
                (new String[database.getAllExercisesImage().size()]));
        list = (ListView)findViewById(R.id.list_exercises);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListExercises.this, Exercises.class);
                intent.putExtra("typeExercises",position);
                Log.e("info : ", String.valueOf(position));
                startActivity(intent);
            }
        });

    }

    public void onBackPressed(){
        NavUtils.navigateUpFromSameTask(this);
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
}