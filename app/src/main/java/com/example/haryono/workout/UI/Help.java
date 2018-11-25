package com.example.haryono.workout.UI;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.haryono.workout.R;

/**
 * Created by Haryono on 7/21/2017.
 */

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_help);
        setupActionBar();
    }

    private void setupActionBar() {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.c_titleview, null);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),"Roboto-Medium_0.ttf");
        ((TextView)v.findViewById(R.id.title)).setTypeface(custom_font);

        ((TextView)v.findViewById(R.id.title)).setText("Help");
        getSupportActionBar().setCustomView(v);
//        setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }
}