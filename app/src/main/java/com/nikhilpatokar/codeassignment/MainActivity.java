package com.nikhilpatokar.codeassignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.nikhilpatokar.codeassignment.adapter.OnActionTakenListener;
import com.nikhilpatokar.codeassignment.adapter.ResultsRecyclerAdapter;
import com.nikhilpatokar.codeassignment.models.Result;
import com.nikhilpatokar.codeassignment.network.Resource;
import com.nikhilpatokar.codeassignment.viewmodels.PersonListViewModel;

import java.util.List;

public class MainActivity extends BaseActivity implements OnActionTakenListener {

    private static final String TAG = "MainActivity";
    private PersonListViewModel mPersonListViewModel;
    private RecyclerView mRecyclerView;
    private ResultsRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPersonListViewModel = ViewModelProviders.of(this).get(PersonListViewModel.class);
        initRecyclerView();
        subscribeObservers();
        mPersonListViewModel.fetchPersonData();
    }

    private void subscribeObservers() {
        mPersonListViewModel.getPersonResults().observe(this, new Observer<Resource<List<Result>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Result>> listResource) {
                if (listResource != null) {
                    Log.d(TAG, "onChanged: status: " + listResource.status);

                    if (listResource.data != null) {
                        switch (listResource.status) {
                            case LOADING: {
                                showProgressBar(true);
                                break;
                            }

                            case ERROR: {
                                Log.e(TAG, "onChanged: cannot refresh the cache.");
                                Log.e(TAG, "onChanged: ERROR message: " + listResource.message);
                                Log.e(TAG, "onChanged: status: ERROR, #recipes: " + listResource.data.size());
                                showProgressBar(false);
                                break;
                            }

                            case SUCCESS: {
                                Log.d(TAG, "onChanged: cache has been refreshed.");
                                Log.d(TAG, "onChanged: status: SUCCESS, #Recipes: " + listResource.data.size());
                                mAdapter.setResults(listResource.data);
                                showProgressBar(false);
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    private void initRecyclerView(){
        mRecyclerView = findViewById(R.id.person_list);
        ViewPreloadSizeProvider<String> viewPreloader = new ViewPreloadSizeProvider<>();
        mAdapter = new ResultsRecyclerAdapter(this, initGlide(), viewPreloader);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerViewPreloader<String> preloader = new RecyclerViewPreloader<String>(
                Glide.with(this),
                mAdapter,
                viewPreloader,
                30);

        mRecyclerView.addOnScrollListener(preloader);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(!mRecyclerView.canScrollVertically(1)){
            //        mRecipeListViewModel.searchNextPage();
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    private RequestManager initGlide(){

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground);

        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }

    @Override
    public void onAcceptClick(int position, boolean flag) {
        Log.d(TAG, "onAcceptClick() called with: position = [" + position + "], flag = [" + flag + "]");
    }
}