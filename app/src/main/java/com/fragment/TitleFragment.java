package com.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ziyuntest.R;

public class TitleFragment extends Fragment {
	private static final String TAG = "Ziyun";
	private static final String ARG_INDEX = "index";
	public static final String  CUR_TITLE = "curTitle";
	private boolean mDualPane;
	private int curIndex = 0;
	private String[] titles;
	private ListView listView;

	public TitleFragment() {
	}

	public static TitleFragment newInstance(int index) {
		TitleFragment fragment = new TitleFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_INDEX, index);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_title, container, false);

		titles = getResources().getStringArray(R.array.title_fragment);

		// Set the adapter
		if (view instanceof ListView) {
			Context context = view.getContext();
			listView = (ListView) view;
			listView.setAdapter(new MyListAdapter(titles, context));
			listView.setOnItemClickListener(new MyListener());
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View detailView = null;
		if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
			detailView = getActivity().findViewById(R.id.fragment_content);
		}
		mDualPane = detailView != null && detailView.getVisibility() == View.VISIBLE;

		if (savedInstanceState != null) {
			curIndex = savedInstanceState.getInt(ARG_INDEX, 0);
		}
		if (mDualPane) {
			listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
			showDetail(curIndex);
		}
	}



	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(ARG_INDEX, curIndex);
	}

	private class MyListAdapter extends BaseAdapter {
		private String[] titles;
		private LayoutInflater inflater;

		public MyListAdapter(String[] titles, Context context) {
			this.titles = titles;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Object getItem(int position) {
			return titles[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.fragment_list_title, parent, false);
			}
			TextView textView = (TextView) convertView.findViewById(R.id.fragment_title_tv);
			textView.setText(titles[position]);
			return convertView;
		}
	}

	private class MyListener implements AdapterView.OnItemClickListener {

		public MyListener() {
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			showDetail(position);
		}
	}

	private void showDetail(int index) {
		curIndex = index;
		if (mDualPane){
			listView.setItemChecked(curIndex, true);
			DetailFragment detailFragment = (DetailFragment)getFragmentManager().findFragmentById(R.id.fragment_content);

			if (detailFragment == null || !detailFragment.getCurTitle().equals(titles[curIndex])){
				detailFragment = DetailFragment.newInstance(titles[curIndex]);
				android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.fragment_content, detailFragment);
				ft.commit();
			}
		}else {
			Intent intent = new Intent(this.getContext(), DetailActivity.class);
			intent.putExtra(CUR_TITLE, titles[curIndex]);
			startActivity(intent);
		}
	}

}
