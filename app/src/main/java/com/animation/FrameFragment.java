package com.animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ziyuntest.R;

public class FrameFragment extends Fragment {

    private static String TAG = "Ziyun";

    public FrameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frame_anim, container, false);
        ImageView image = (ImageView)view.findViewById(R.id.frame_iv);
        image.setBackgroundResource(R.drawable.anim_frame);
        AnimationDrawable drawable = (AnimationDrawable)image.getBackground();
        drawable.start();
        return view;
    }

}
