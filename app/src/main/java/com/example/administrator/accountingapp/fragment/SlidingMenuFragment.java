package com.example.administrator.accountingapp.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.accountingapp.R;

public class SlidingMenuFragment extends Fragment{

    ImageView imageView;
    AnimationDrawable animationDrawable;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.slidingmenu_page,null);
        imageView = view.findViewById(R.id.slidingmenu_img);

        animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.animation);
        imageView.setBackgroundDrawable(animationDrawable);

        return view;
    }
}
