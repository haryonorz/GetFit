package com.example.haryono.workout;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Haryono on 6/6/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private Context mycontext;
    private static String DB_NAME = "getfit.db";
    private static String DB_PATH = "/data/data/" + BuildConfig.APPLICATION_ID + "/databases/";
    public SQLiteDatabase myDataBase;

    public DataBaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, 1);
        this.mycontext = context;
        boolean dbexist = checkdatabase();
        if (dbexist) {
            System.out.println("Database exists");
            opendatabase();
        } else {
            System.out.println("Database doesn't exist");
            createdatabase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if (dbexist) {
            System.out.println(" Database exists.");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkdatabase() {

        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        Log.v("TEST", "0");
        InputStream myinput = mycontext.getAssets().open(DB_NAME);
        Log.v("TEST", "1");
        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;
        Log.v("TEST", "2");
        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);
        Log.v("TEST", "3");
        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }
        Log.v("TEST", "4");
        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    public List<String> getAllExercises() { // Untuk Mengambil semua judul dalam note
        ArrayList<String> allExercises = new ArrayList<String>();
        String selectQuery = "SELECT * FROM  exercises";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                allExercises.add(cursor.getString(1));// exercises_name
            } while (cursor.moveToNext());
        }
        return allExercises;
    }

    public List<String> getAllExercisesImage() { // Untuk Mengambil semua judul dalam note
        ArrayList<String> allExercisesImage = new ArrayList<String>();
        String selectQuery = "SELECT * FROM  exercises";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                allExercisesImage.add(cursor.getString(2));// exercises_image
            } while (cursor.moveToNext());
        }
        return allExercisesImage;
    }

    public List<String> getAllExercisesMetode() { // Untuk Mengambil semua judul dalam note
        ArrayList<String> allExercises = new ArrayList<String>();
        String selectQuery = "SELECT * FROM  exercises";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                allExercises.add(cursor.getString(3));// metode
            } while (cursor.moveToNext());
        }
        return allExercises;
    }


    public List<String> getDescExercises(int position) { // Untuk Mengambil semua judul dalam note
        ArrayList<String> allWorkout = new ArrayList<String>();
        String selectQuery = "SELECT * FROM  exercises where no_exercises = " + position;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                allWorkout.add(cursor.getString(1));// exercises_name
                allWorkout.add(cursor.getString(2));// exercises_image
                allWorkout.add(cursor.getString(3));// metode
                allWorkout.add(cursor.getString(4));// description
                allWorkout.add(cursor.getString(5));// exercises_duration
                allWorkout.add(cursor.getString(6));// exercises_frequention
            } while (cursor.moveToNext());
        }
        return allWorkout;
    }

    public List<String> getExerciseWorkoutList(String catgExercises) { // Untuk Mengambil semua judul dalam note
        ArrayList<String> allWorkout = new ArrayList<String>();
        String selectQuery = "SELECT * FROM  workout where category_exercises = '" + catgExercises +"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                allWorkout.add(cursor.getString(1)); // workout_name
            } while (cursor.moveToNext());
        }
        return allWorkout;
    }

    public List<String> getExerciseWorkoutAnimation(int position, String catgExercises) { // Untuk Mengambil semua judul dalam note
        ArrayList<String> allWorkout = new ArrayList<String>();
        String selectQuery = "SELECT * FROM  workout where no_workout = "+position+" and category_exercises = '" + catgExercises +"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                allWorkout.add(cursor.getString(1));// workout_name
                allWorkout.add(cursor.getString(7));// image_animation1
                allWorkout.add(cursor.getString(8));// image_animation2
                allWorkout.add(cursor.getString(9));// image_animation3
                allWorkout.add(cursor.getString(10));// image_animation4
                allWorkout.add(cursor.getString(11));// image_animation5
                allWorkout.add(cursor.getString(12));// image_animation6
                allWorkout.add(cursor.getString(13));// image_animation7
                allWorkout.add(cursor.getString(14));// image_animation8
                allWorkout.add(cursor.getString(15));// image_animation9
                allWorkout.add(cursor.getString(16));// image_animation10
                allWorkout.add(cursor.getString(17));// image_animation11
                allWorkout.add(cursor.getString(18));// image_animation12
            } while (cursor.moveToNext());
        }
        return allWorkout;
    }


    // WORKOUT PART !!

    public List<String> getAllWorkout() { // Untuk Mengambil semua judul dalam note
        ArrayList<String> allWorkout = new ArrayList<String>();
        String selectQuery = "SELECT * FROM  workout";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                allWorkout.add(cursor.getString(1)); // workout_name
            } while (cursor.moveToNext());
        }
        return allWorkout;
    }

    public List<String> getAllWorkoutImage() { // Untuk Mengambil semua judul dalam note
        ArrayList<String> allWorkout = new ArrayList<String>();
        String selectQuery = "SELECT * FROM  workout";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                allWorkout.add(cursor.getString(2));// workout_image
            } while (cursor.moveToNext());
        }
        return allWorkout;
    }

    public List<String> getDescWorkout(int position) { // Untuk Mengambil semua judul dalam note
        ArrayList<String> allWorkout = new ArrayList<String>();
        String selectQuery = "SELECT * FROM  workout where no_workout = " + position;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                allWorkout.add(cursor.getString(1));// workout_name
                allWorkout.add(cursor.getString(3));// muscle
                allWorkout.add(cursor.getString(4));// technique
                allWorkout.add(cursor.getString(5));// description
                allWorkout.add(cursor.getString(6));// link
                allWorkout.add(cursor.getString(7));// image_animation1
                allWorkout.add(cursor.getString(8));// image_animation2
                allWorkout.add(cursor.getString(9));// image_animation3
                allWorkout.add(cursor.getString(10));// image_animation4
                allWorkout.add(cursor.getString(11));// image_animation5
                allWorkout.add(cursor.getString(12));// image_animation6
                allWorkout.add(cursor.getString(13));// image_animation7
                allWorkout.add(cursor.getString(14));// image_animation8
                allWorkout.add(cursor.getString(15));// image_animation9
                allWorkout.add(cursor.getString(16));// image_animation10
                allWorkout.add(cursor.getString(17));// image_animation11
                allWorkout.add(cursor.getString(18));// image_animation12
            } while (cursor.moveToNext());
        }
        return allWorkout;
    }
}