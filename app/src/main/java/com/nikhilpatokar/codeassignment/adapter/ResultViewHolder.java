package com.nikhilpatokar.codeassignment.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.nikhilpatokar.codeassignment.R;
import com.nikhilpatokar.codeassignment.models.Result;

import de.hdodenhof.circleimageview.CircleImageView;

public class ResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Button accept,reject;
    private TextView name, age, location;
    private CircleImageView image;
    private OnActionTakenListener mOnActionTakenListener;
    private RequestManager requestManager;
    private ViewPreloadSizeProvider viewPreloadSizeProvider;

    public ResultViewHolder(@NonNull View itemView,
                            OnActionTakenListener onRecipeListener,
                            RequestManager requestManager,
                            ViewPreloadSizeProvider preloadSizeProvider) {
        super(itemView);

        this.mOnActionTakenListener = onRecipeListener;
        this.requestManager = requestManager;
        this.viewPreloadSizeProvider = preloadSizeProvider;

        name = itemView.findViewById(R.id.person_name);
        age = itemView.findViewById(R.id.person_age);
        location = itemView.findViewById(R.id.person_location);
        image = itemView.findViewById(R.id.person_image);

        accept = itemView.findViewById(R.id.accept_button);
        reject = itemView.findViewById(R.id.reject_button);

        accept.setOnClickListener(this);
        reject.setOnClickListener(this);
    }

    public void onBind(Result result){
        if (result.getPicture().getLarge() != null) {
            requestManager
                    .load(result.getPicture().getLarge())
                    .into(image);
        }
        if(result.getName().getFirst()!= null && result.getName().getLast() != null){
            name.setText(result.getName().getFirst()+" "+result.getName().getLast());
        }
        if(result.getDob().getAge() != null){
            age.setText(String.valueOf(result.getDob().getAge()));
        }
        if(result.getLocation().getCity()+", "+result.getLocation().getCountry() != null) {
            location.setText(result.getLocation().getCity() + ", " + result.getLocation().getCountry());
        }

        viewPreloadSizeProvider.setView(image);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        case R.id.accept_button:
            mOnActionTakenListener.onAcceptClick(getAdapterPosition(),true);
        break;

        case R.id.reject_button:
            mOnActionTakenListener.onAcceptClick(getAdapterPosition(),false);
        break;

        }
    }
}





