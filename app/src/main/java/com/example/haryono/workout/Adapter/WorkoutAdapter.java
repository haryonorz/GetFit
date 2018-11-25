package com.example.haryono.workout.Adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haryono.workout.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Haryono on 6/1/2017.
 */

public class WorkoutAdapter extends ArrayAdapter<String> {

    private final AppCompatActivity context;
    private final String[] nameWorkout;
    private final String[] imageWorkout;

    public WorkoutAdapter(AppCompatActivity context, String[] nameWorkout, String[] imageWorkout) {
        super(context, R.layout.list_workout, nameWorkout);

        this.context = context;
        this.nameWorkout = nameWorkout;
        this.imageWorkout = imageWorkout;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_workout, null,true);

        TextView tvWorkoutName = (TextView) rowView.findViewById(R.id.tvWorkoutName);
        ImageView ivWorkout = (ImageView) rowView.findViewById(R.id.ivWorkout);


        tvWorkoutName.setText(nameWorkout[position]);

        try {
            // get input stream
            InputStream ims = context.getAssets().open("workout_image/" + imageWorkout[position]);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            ivWorkout.setImageDrawable(d);

        } catch (IOException ex) {
        }
        return rowView;
    };
}
