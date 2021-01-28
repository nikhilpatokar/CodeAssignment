package com.nikhilpatokar.codeassignment.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.nikhilpatokar.codeassignment.models.Result;
import com.nikhilpatokar.codeassignment.network.ApiResponse;
import com.nikhilpatokar.codeassignment.network.NetworkBoundResource;
import com.nikhilpatokar.codeassignment.network.PersonResponse;
import com.nikhilpatokar.codeassignment.network.Resource;
import com.nikhilpatokar.codeassignment.network.ServiceGenerator;
import com.nikhilpatokar.codeassignment.persistence.PersonDatabase;
import com.nikhilpatokar.codeassignment.persistence.ResultDao;
import com.nikhilpatokar.codeassignment.utils.AppExecutors;
import com.nikhilpatokar.codeassignment.utils.Constants;

import java.util.List;

public class ResultsRepository {

    private static final String TAG = "ResultsRepository";

    private static ResultsRepository instance;
    private ResultDao resultDao;

    public static ResultsRepository getInstance(Context context){
        if(instance == null){
            instance = new ResultsRepository(context);
        }
        return instance;
    }


    private ResultsRepository(Context context) {
        resultDao = PersonDatabase.getInstance(context).getResultDao();
    }


    public LiveData<Resource<List<Result>>> searchPersonApi(final int noOfResults){
        return new NetworkBoundResource<List<Result>, PersonResponse>(AppExecutors.getInstance()){
            @Override
            protected void saveCallResult(@NonNull PersonResponse item) {

                if(item.getInfo().getResults() != 0){

                    Result[] results = new Result[item.getResults().size()];

                    int index = 0;
                    for(long rowid: resultDao.insertResults((Result[]) (item.getResults().toArray(results)))){
                        if(rowid == -1){
                            Log.d(TAG, "saveCallResult: CONFLICT... This recipe is already in the cache");
                        }
                        index++;
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Result> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Result>> loadFromDb() {
                return resultDao.searchResults(noOfResults);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PersonResponse>> createCall() {
                return ServiceGenerator.getPersonApi().fetchPersonResult(noOfResults);
            }
        }.getAsLiveData();
    }
}












