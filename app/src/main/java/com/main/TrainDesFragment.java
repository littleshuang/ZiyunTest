package com.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ziyuntest.R;

/**
 * Created by Ziyun on 2016/6/29.
 *
 * A fragment for showing the description of each training part.
 */
public class TrainDesFragment extends Fragment {
    private static final String ARG_CURINDEX = "current_index";
    private int curIndex;   // 记录当前位置
    private static String[] desList; // 保存内容列表

    /**
     * 创建一个TrainDesFragment实例，显示index位置的标题对应的内容
     */
    public static TrainDesFragment newInstance(int index){
        TrainDesFragment tdf = new TrainDesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CURINDEX, index);
        tdf.setArguments(args);

        return tdf;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 从资源文件中加载内容列表
//        desList = getResources().getStringArray(R.array.content_fragment);
        desList = Train.getTrainDescrips(getActivity());
        if (savedInstanceState != null){
            curIndex = savedInstanceState.getInt(ARG_CURINDEX);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_CURINDEX, curIndex);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) return null;     //当container为空时，意味着此时处于竖屏模式，则没必要实例化该Fragment
        View view = inflater.inflate(R.layout.fragment_traindes, container, false);
        TextView des = (TextView)view.findViewById(R.id.train_des);
        des.setText(desList[getCurIndex()]);
        return view;
    }

    public int getCurIndex(){
        return getArguments().getInt(ARG_CURINDEX, 0);
    }

}
