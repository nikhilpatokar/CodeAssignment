package com.nikhilpatokar.codeassignment.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nikhilpatokar.codeassignment.models.Result;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ResultDao {

    @Insert(onConflict = IGNORE)
    long[] insertResults(Result... results);

    @Insert(onConflict = REPLACE)
    void insertResult(Result result);

    @Query("SELECT * FROM RESULTS ORDER BY _id DESC LIMIT :noOfResults ")
    LiveData<List<Result>> searchResults(int noOfResults);

}









