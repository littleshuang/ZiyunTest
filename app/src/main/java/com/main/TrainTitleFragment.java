package com.main;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ziyuntest.R;

/**
 * Created by Ziyun on 2016/6/29. A fragment for showing the name of each
 * training part.
 */
public class TrainTitleFragment extends ListFragment implements AdapterView.OnItemLongClickListener {
	public static final String ARG_INTENT_INDEX = "arg_intent_index"; // 开启新的Activity时传递的参数
	private static final String ARG_CUR_INDEX = "arg_cur_index"; // 设置当前位置的参数
	private static boolean mDualPane = false; // 判断是否为横屏模式的标识，true：横屏，false：竖屏
	private int curIndex = 0; // 记录当前位置的变量，默认为0
	private static String[] trainTitles; // 标题数组
	private static Train[] trains;
	private static boolean lockLongPress = false; // 锁定长按事件

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 从资源文件中获取标题数组
//		trainTitles = getResources().getStringArray(R.array.title_fragment);
		trains = Train.getTrains(getActivity());
		trainTitles = Train.getTrainNames(getActivity());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// 设置列表的适配器
		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1,
				trainTitles));
		getListView().setOnItemLongClickListener(this);
		getListView().setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP && lockLongPress) {
					lockLongPress = false;
					Intent intent = new Intent(getActivity(), trains[curIndex].getClz());
					startActivity(intent);
					return true;
				}
				return false;
			}
		});

		// 因为系统会自动根据屏幕模式而选择相应的布局文件，所以可以通过如下方式判断屏幕是否处于横屏模式
		View desView = getActivity().findViewById(R.id.train_content);
		mDualPane = desView != null && desView.getVisibility() == View.VISIBLE;

		if (savedInstanceState != null) {
			curIndex = savedInstanceState.getInt(ARG_CUR_INDEX, 0);
		}

		getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
		if (mDualPane) {
			showDes(curIndex); // 自定义的一个用于显示内容的函数
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(ARG_CUR_INDEX, curIndex);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		showDes(position);
	}

	/**
	 * 显示内容
	 * 
	 * @param index
	 *            ：当前选中的标题位置
	 */
	private void showDes(int index) {
		curIndex = index;

		if (mDualPane) {
			getListView().setItemChecked(curIndex, true);

			TrainDesFragment tdf = (TrainDesFragment) getFragmentManager().findFragmentById(R.id.train_content);
			if (tdf == null || tdf.getCurIndex() != curIndex) {
				tdf = TrainDesFragment.newInstance(curIndex);
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.train_content, tdf);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); // 切换效果
				ft.commit();
			}
		} else {
			// 竖屏模式时开启TrainDesActivity，通过该Activity显示内容
			Intent intent = new Intent(getActivity(), TrainDesActivity.class);
			intent.putExtra(ARG_INTENT_INDEX, curIndex);
			startActivity(intent);
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		curIndex = position;
		lockLongPress = true;
		return false;
	}

}
