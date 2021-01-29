package com.nikhilpatokar.codeassignment.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.nikhilpatokar.codeassignment.models.Result;
import com.nikhilpatokar.codeassignment.network.Resource;
import com.nikhilpatokar.codeassignment.repository.ResultsRepository;

import java.util.List;

public class PersonListViewModel extends AndroidViewModel {

    private static final String TAG = "PersonListViewModel";

    private int noOfPersons = 10;
    public static final String QUERY_EXHAUSTED = "No more results.";

    private MediatorLiveData<Resource<List<Result>>> results = new MediatorLiveData<>();
    private ResultsRepository resultRepository;

    // query extras
    private boolean isQueryExhausted;
    private boolean isPerformingQuery;
    private String query;
    private boolean cancelRequest;
    private long requestStartTime;

    public PersonListViewModel(@NonNull Application application) {
        super(application);
        resultRepository = ResultsRepository.getInstance(application);

    }

    public LiveData<Resource<List<Result>>> getPersonResults(){
        return results;
    }

    public void fetchPersonData(){
        Log.d(TAG, "fetchPersonData() called for "+noOfPersons);
        executeSearch();
    }

    public void updatePersonData(String status,int id){
        resultRepository.updatePersonData(status,id);
    }

    private void executeSearch(){
        requestStartTime = System.currentTimeMillis();
        cancelRequest = false;
        isPerformingQuery = true;
        final LiveData<Resource<List<Result>>> repositorySource = resultRepository.searchPersonApi(noOfPersons);
        results.addSource(repositorySource, new Observer<Resource<List<Result>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Result>> listResource) {
                if(!cancelRequest){
                    if(listResource != null){
                        if(listResource.status == Resource.Status.SUCCESS){
                            Log.d(TAG, "onChanged: REQUEST TIME: " + (System.currentTimeMillis() - requestStartTime) / 1000 + " seconds.");
                            Log.d(TAG, "onChanged: " + listResource.data);

                            isPerformingQuery = false;
                            if(listResource.data != null){
                                if(listResource.data.size() == 0 ){
                                    Log.d(TAG, "onChanged: query is exhausted...");
                                    results.setValue(
                                            new Resource<List<Result>>(
                                                    Resource.Status.ERROR,
                                                    listResource.data,
                                                    QUERY_EXHAUSTED
                                            )
                                    );
                                    isQueryExhausted = true;
                                }
                            }
                            results.removeSource(repositorySource);
                        }
                        else if(listResource.status == Resource.Status.ERROR){
                            Log.d(TAG, "onChanged: REQUEST TIME: " + (System.currentTimeMillis() - requestStartTime) / 1000 + " seconds.");
                            isPerformingQuery = false;
                            if(listResource.message.equals(QUERY_EXHAUSTED)){
                                isQueryExhausted = true;
                            }
                            results.removeSource(repositorySource);
                        }
                        results.setValue(listResource);
                    }
                    else{
                        results.removeSource(repositorySource);
                    }
                }
                else{
                    results.removeSource(repositorySource);
                }
            }
        });
    }

    public void searchNextPerson() {
        Log.d(TAG, "searchNextPerson() called");
        noOfPersons = noOfPersons + 10;
        executeSearch();
    }
}
