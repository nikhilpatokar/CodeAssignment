package com.nikhilpatokar.codeassignment.network;


import com.nikhilpatokar.codeassignment.utils.Constants;
import com.nikhilpatokar.codeassignment.utils.LiveDataCallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.nikhilpatokar.codeassignment.utils.Constants.CONNECTION_TIMEOUT;
import static com.nikhilpatokar.codeassignment.utils.Constants.READ_TIMEOUT;
import static com.nikhilpatokar.codeassignment.utils.Constants.WRITE_TIMEOUT;

public class ServiceGenerator {

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(logging)
            .build();


    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static PersonApi personApi = retrofit.create(PersonApi.class);

    public static PersonApi getPersonApi(){
        return personApi;
    }
}
