package com.example.haryono.workout.UI.Workout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.haryono.workout.Adapter.WorkoutAdapter;
import com.example.haryono.workout.DataBaseHelper;
import com.example.haryono.workout.R;

import java.io.IOException;

public class ListWorkout extends AppCompatActivity {

    ListView list;
    DataBaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_list_workout);

        try {
            database = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setupActionBar();

        WorkoutAdapter adapter = new WorkoutAdapter(this, database.getAllWorkout().toArray
                (new String[database.getAllWorkout().size()]),
                database.getAllWorkoutImage().toArray(new String[database.getAllWorkoutImage().size()]));
        list = (ListView)findViewById(R.id.list_workout);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String Pilihitem = nameWorkout[+position];
//                Toast.makeText(getApplicationContext(), Pilihitem, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ListWorkout.this, Workout.class);
                intent.putExtra("typeWorkout",position);
                Log.e("info : ", String.valueOf(position));
                startActivity(intent);

            }
        });

    }

    private void setupActionBar(){;
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.c_titleview, null);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),"Roboto-Medium_0.ttf");
        ((TextView)v.findViewById(R.id.title)).setTypeface(custom_font);

        ((TextView)v.findViewById(R.id.title)).setText("Intruction");
        getSupportActionBar().setCustomView(v);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }
}

