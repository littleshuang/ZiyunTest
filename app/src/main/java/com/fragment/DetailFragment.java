package com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ziyuntest.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ziyun on 2016/6/29.
 */
public class DetailFragment extends Fragment {
    private static final String TAG = "Ziyun";
    private String curTitle;
    private static final String ARG_TITLE = "curTitle";
    private Map<String, String> fragment;

    public DetailFragment(){}

    public static DetailFragment newInstance(String title){
        DetailFragment df = new DetailFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        df.setArguments(args);

        return df;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 获取模拟的数据
        String[] titles = getResources().getStringArray(R.array.title_fragment);
        String[] detail = getResources().getStringArray(R.array.content_fragment);
        int len;
        fragment = new HashMap<>();
        len = titles.length > detail.length ? detail.length : titles.length;
        for (int i = 0; i < len; ++i){
            fragment.put(titles[i], detail[i]);
        }

        if (savedInstanceState != null){
            curTitle = savedInstanceState.getString(ARG_TITLE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
            outState.putString(ARG_TITLE, curTitle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null){
            return null;
        }
        View view =  inflater.inflate(R.layout.fragment_detail, container, false);
        TextView textView = (TextView)view.findViewById(R.id.fragment_detail_tv);
        textView.setText(fragment.get(getCurTitle()));
        return view;
    }

    public String getCurTitle(){
        return getArguments().getString(ARG_TITLE, "null");
    }
}
