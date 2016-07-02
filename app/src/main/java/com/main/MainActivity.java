package com.main;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.animation.AnimationActivity;
import com.animation.ImageMoveActivity;
import com.fragment.FragmentActivity;
import com.serialize.FirstActivity;
import com.serialize.SecondActivity;
import com.serialize.SerialActivity;
import com.ziyuntest.R;

//
public class MainActivity extends ListActivity {

    /**
     * This inner class describes a sample of a class in this app
     * Each sample represents an individual test
     */
    private class Sample{
        private CharSequence title;
        private Class<? extends Activity> activityClz;

        public Sample(int titleResId, Class<? extends Activity> clz){
            this.title = getResources().getString(titleResId);
            this.activityClz = clz;
        }

        public Sample(String title, Class<? extends Activity> clz){
            this.title = title;
            this.activityClz = clz;
        }

        @Override
        public String toString() {
            return title == null ? null : title.toString();
        }
    }

    private static Sample[] mSamples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSamples = new Sample[]{
                new Sample(R.string.title_activity_image_move, ImageMoveActivity.class),
                new Sample(R.string.title_activity_first, FirstActivity.class),
                new Sample(R.string.title_activity_second, SecondActivity.class),
                new Sample(R.string.title_activity_serial, SerialActivity.class),
                new Sample("Animation Activity", AnimationActivity.class),
                new Sample(R.string.title_activity_fragment, FragmentActivity.class),
                new Sample(R.string.title_activity_train, TrainActivity.class),
        };

        setListAdapter(new ArrayAdapter<Sample>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, mSamples));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // Launch the sample activity associated with the list in position
        startActivity(new Intent(MainActivity.this, mSamples[position].activityClz));
    }
}
