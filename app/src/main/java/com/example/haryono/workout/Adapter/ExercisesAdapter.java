package com.example.haryono.workout.Adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.haryono.workout.R;
import com.example.haryono.workout.Font.CustomTextView3;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Haryono on 6/2/2017.
 */

public class ExercisesAdapter extends ArrayAdapter<String> {

    private final AppCompatActivity context;
    private final String[] nameExercises;
    private final String[] levelExercises;
    private final String[] imageExercises;

    public ExercisesAdapter(AppCompatActivity context, String[] nameExercises, String[] levelExercises, String[] imageExercises) {
        super(context, R.layout.list_exercises, nameExercises);

        this.context = context;
        this.nameExercises = nameExercises;
        this.levelExercises = levelExercises;
        this.imageExercises = imageExercises;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_exercises, null,true);

        CustomTextView3 ctvExercisesName = (CustomTextView3) rowView.findViewById(R.id.tvExercisesName);
        CustomTextView3 ctvExercisesLevel = (CustomTextView3) rowView.findViewById(R.id.tvExercisesLevel);
        ImageView ivExercises = (ImageView) rowView.findViewById(R.id.ivExercises);

        ctvExercisesName.setText(nameExercises[position]);
        ctvExercisesLevel.setText(levelExercises[position]);

        try {
            // get input stream
            InputStream ims = context.getAssets().open("exercises_image/" + imageExercises[position]);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            ivExercises.setImageDrawable(d);

        } catch (IOException ex) {
        }

        return rowView;

    };
}
