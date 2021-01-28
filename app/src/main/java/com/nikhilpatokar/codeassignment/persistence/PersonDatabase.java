package com.nikhilpatokar.codeassignment.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.nikhilpatokar.codeassignment.models.Result;


@Database(entities = {Result.class}, version = 1, exportSchema = true)
@TypeConverters({Converters.class})
public abstract class PersonDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "recipes_db";

    private static PersonDatabase instance;

    public static PersonDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    PersonDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract ResultDao getResultDao();

}






