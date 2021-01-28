package com.nikhilpatokar.codeassignment.network;

import androidx.lifecycle.LiveData;

import com.nikhilpatokar.codeassignment.models.Result;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PersonApi {

    @GET("api")
    LiveData<ApiResponse<PersonResponse>> fetchPersonResult(@Query("results") int results);

}
