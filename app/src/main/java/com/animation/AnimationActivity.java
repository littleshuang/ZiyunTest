package com.animation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ziyuntest.R;

import java.util.ArrayList;
import java.util.List;

public class AnimationActivity extends FragmentActivity {
	private static String TAG = "Ziyun";	//For log
	private static List<Anims> animations;	// A sample instances of animations
	private Context mContext;
	private ListView list;

	private class Anims {
		private String title;
		private Fragment fragment;

		public Anims(int titleRes, Fragment fragment) {
			this.title = getResources().getString(titleRes);
			this.fragment = fragment;
		}

		@Override
		public String toString() {
			return this.title;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_animation);
		mContext = this;
		// Create a list of instances of animation fragment
		animations = new ArrayList<>();
		animations.add(new Anims(R.string.title_fragment_frame, new FrameFragment()));
		animations.add(new Anims(R.string.title_fragment_view_anim, ViewAnimFragment.newInstance(mContext)));
		list = (ListView) findViewById(R.id.list);
		list.setAdapter(new MyAdapter<Anims>(mContext, animations));

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.content, animations.get(position).fragment);
				ft.addToBackStack(null);
				ft.commit();
			}
		});

		// 初始时添加第一个Fragment
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.content, new FrameFragment());
        ft.addToBackStack("The first fragment");
        ft.commit();
	}

	private class MyAdapter<T> extends BaseAdapter{
		private List<T> data;
		private LayoutInflater mInflater;

		public MyAdapter(Context context, List<T> data){
			this.data = data;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null){
				convertView = mInflater.inflate(R.layout.list_item_main, null);
			}
			TextView textView = (TextView)convertView.findViewById(R.id.list_item_tv);
			textView.setText(data.get(position).toString());
			return convertView;
		}
	}

}
