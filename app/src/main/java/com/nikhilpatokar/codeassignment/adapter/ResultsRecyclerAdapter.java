package com.nikhilpatokar.codeassignment.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.nikhilpatokar.codeassignment.R;
import com.nikhilpatokar.codeassignment.models.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        ListPreloader.PreloadModelProvider<String>
{

    private List<Result> mResults;
    private OnActionTakenListener mOnActionTakenListener;
    private RequestManager requestManager;
    private ViewPreloadSizeProvider<String> preloadSizeProvider;

    public ResultsRecyclerAdapter(OnActionTakenListener onActionTakenListener,
                                  RequestManager requestManager,
                                  ViewPreloadSizeProvider<String> viewPreloadSizeProvider) {
        this.mOnActionTakenListener = onActionTakenListener;
        this.requestManager = requestManager;
        this.preloadSizeProvider = viewPreloadSizeProvider;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_person_list_item, viewGroup, false);
                return new ResultViewHolder(view, mOnActionTakenListener, requestManager, preloadSizeProvider);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            ((ResultViewHolder)viewHolder).onBind(mResults.get(i));
    }

    // display loading during search request
    public void displayOnlyLoading(){
        /*clearRecipesList();
        Recipe recipe = new Recipe();
        recipe.setTitle("LOADING...");
        mRecipes.add(recipe);
        notifyDataSetChanged();*/
    }

    private void clearRecipesList(){
        if(mResults == null){
            mResults = new ArrayList<>();
        }
        else{
            mResults.clear();
        }
        notifyDataSetChanged();
    }

    public void setQueryExhausted(){
        /*hideLoading();
        Recipe exhaustedRecipe = new Recipe();
        exhaustedRecipe.setTitle("EXHAUSTED...");
        mRecipes.add(exhaustedRecipe);
        notifyDataSetChanged();*/
    }

    public void hideLoading(){
        /*if(isLoading()){
            if(mRecipes.get(0).getTitle().equals("LOADING...")){
                mRecipes.remove(0);
            }
            else if(mRecipes.get(mRecipes.size() - 1).equals("LOADING...")){
                mRecipes.remove(mRecipes.size() - 1);
            }
            notifyDataSetChanged();
        }*/
    }

    // pagination loading
    public void displayLoading(){
        /*if(mRecipes == null){
            mRecipes = new ArrayList<>();
        }
        if(!isLoading()){
            Recipe recipe = new Recipe();
            recipe.setTitle("LOADING...");
            mRecipes.add(recipe);
            notifyDataSetChanged();
        }*/
    }

    private boolean isLoading(){
        /*if(mRecipes != null){
            if(mRecipes.size() > 0){
                if(mRecipes.get(mRecipes.size() - 1).getTitle().equals("LOADING...")){
                    return true;
                }
            }
        }*/
        return false;
    }

    @Override
    public int getItemCount() {
        if(mResults != null){
            return mResults.size();
        }
        return 0;
    }

    public void setResults(List<Result> results){
        mResults = results;
        notifyDataSetChanged();
    }

    /*public Recipe getSelectedRecipe(int position){
        if(mRecipes != null){
            if(mRecipes.size() > 0){
                return mRecipes.get(position);
            }
        }
        return null;
    }*/

    @NonNull
    @Override
    public List<String> getPreloadItems(int position) {
        String url = mResults.get(position).getPicture().getMedium();
        if(TextUtils.isEmpty(url)){
            return Collections.emptyList();
        }
        return Collections.singletonList(url);
    }

    @Nullable
    @Override
    public RequestBuilder<?> getPreloadRequestBuilder(@NonNull String item) {
        return requestManager.load(item);
    }


}















