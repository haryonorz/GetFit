package com.example.haryono.workout.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.haryono.workout.R;
import com.example.haryono.workout.UI.Exercises.Exercises;
import com.example.haryono.workout.UI.Exercises.ListExercises;
import com.example.haryono.workout.UI.Workout.ListWorkout;

public class Menu extends AppCompatActivity {

    private LinearLayout llIntruction, llWorkout, llHelp, llAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_menu);

        llIntruction = (LinearLayout) findViewById(R.id.llInstruction);
        llWorkout = (LinearLayout) findViewById(R.id.llExercises);
        llHelp = (LinearLayout) findViewById(R.id.llHelp);
        llAbout = (LinearLayout) findViewById(R.id.llAbout);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),"Roboto-Medium_0.ttf");
        ((TextView)findViewById(R.id.tvInstruction)).setTypeface(custom_font);
        ((TextView)findViewById(R.id.tvExercises)).setTypeface(custom_font);
        ((TextView)findViewById(R.id.tvAbout)).setTypeface(custom_font);

        llIntruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intructionIntent = new Intent(Menu.this, ListWorkout.class);
                startActivity(intructionIntent);
            }
        });

        llWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intructionIntent = new Intent(Menu.this, ListExercises.class);
                startActivity(intructionIntent);
            }
        });

        llHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intructionIntent = new Intent(Menu.this, Help.class);
                startActivity(intructionIntent);
            }
        });

        llAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intructionIntent = new Intent(Menu.this, About.class);
                startActivity(intructionIntent);
            }
        });

    }

    public void onBackPressed(){
        AlertDialog.Builder alert = new AlertDialog.Builder(Menu.this);
        alert.setMessage("Apakah anda yakin ingin keluar?");
        alert.setCancelable(true);
        alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog al = alert.create();
        al.show();
    }
}
