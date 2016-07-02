package com.draw;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.ziyuntest.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;

public class HandDrawActivity extends AppCompatActivity {

	EmbossMaskFilter emboss;
	BlurMaskFilter blur;
	Menu myMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hand_draw);

		emboss = new EmbossMaskFilter(new float[] { 1.5f, 1.5f, 1.5f }, 0.6f, 6, 4.2f);
		blur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = new MenuInflater(this);
		myMenu = menu;
		inflater.inflate(R.menu.draw_view_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		HandDrawView hdv = (HandDrawView) findViewById(R.id.hand_draw_view);
		switch (item.getItemId()) {
		// 清空画布
		case R.id.drawview_reset:
			hdv.clearCanvas();
			break;

		case R.id.drawview_save:
			saveToFile(hdv);
			break;

		case R.id.color_accent:
			resetItem();
			item.setChecked(true);
			hdv.paint.setColor(0xffff4081);
			break;

		case R.id.color_green:
			resetItem();
			item.setChecked(true);
			hdv.paint.setColor(Color.GREEN);
			break;

		case R.id.color_blue:
			resetItem();
			item.setChecked(true);
			hdv.paint.setColor(Color.BLUE);
			break;

		case R.id.color_yellow:
			resetItem();
			item.setChecked(true);
			hdv.paint.setColor(Color.YELLOW);
			break;

		case R.id.width_1:
			resetItem();
			item.setChecked(true);
			hdv.paint.setStrokeWidth(1);
			break;

		case R.id.width_3:
			resetItem();
			item.setChecked(true);
			hdv.paint.setStrokeWidth(3);
			break;

		case R.id.width_5:
			resetItem();
			item.setChecked(true);
			hdv.paint.setStrokeWidth(5);
			break;

		case R.id.menu_emboss:
			hdv.paint.setMaskFilter(emboss);
			item.setChecked(true);
			break;

		case R.id.menu_blur:
			hdv.paint.setMaskFilter(blur);
			item.setChecked(true);
			break;
		}
		return true;
	}

	private void saveToFile(HandDrawView view) {
		try {
			String sdState = Environment.getExternalStorageState(); // 判读SD卡是否存在
			if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
				Toast.makeText(this, "SD card is not ready!", Toast.LENGTH_SHORT).show();
				return;
			}

			// Get the system picture directory
			File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			// Make sure the path is exist
			path.mkdirs();

			// Gen the picture file name by current time
			Calendar calendar = Calendar.getInstance();
			String fileName = " " + calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH)
					+ calendar.get(Calendar.DAY_OF_MONTH) + calendar.get(Calendar.HOUR_OF_DAY)
					+ calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND) + ".png";
            String name = path.getPath() + "/" + fileName;
            view.saveToFile(name);
			Toast.makeText(this, "保存成功！\n文件保存在：" + name, Toast.LENGTH_LONG).show();
		} catch (FileNotFoundException e) {
			Toast.makeText(this, "保存失败！\n" + e, Toast.LENGTH_LONG).show();
		}
	}

	// 重置菜单项，即清除现有选择项，实现单选功能
	private void resetItem() {
		myMenu.findItem(R.id.color_accent).setChecked(false);
		myMenu.findItem(R.id.color_green).setChecked(false);
		myMenu.findItem(R.id.color_blue).setChecked(false);
		myMenu.findItem(R.id.color_yellow).setChecked(false);
		myMenu.findItem(R.id.width_1).setChecked(false);
		myMenu.findItem(R.id.width_3).setChecked(false);
		myMenu.findItem(R.id.width_5).setChecked(false);
	}
}
