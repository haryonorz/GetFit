package com.example.haryono.workout.Adapter;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.haryono.workout.R;

/**
 * Created by Haryono on 6/10/2017.
 */

public class ExercisesWorkoutAdapater extends ArrayAdapter<String> {

    private final AppCompatActivity context;
    private final String[] nameWorkout;

    public ExercisesWorkoutAdapater(AppCompatActivity context, String[] nameWorkout) {
        super(context, R.layout.list_exercises_workout, nameWorkout);

        this.context = context;
        this.nameWorkout = nameWorkout;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_exercises_workout, null,true);

        TextView tvWorkoutName = (TextView) rowView.findViewById(R.id.tvWorkoutName);


        tvWorkoutName.setText(nameWorkout[position]);

        return rowView;

    };


}

