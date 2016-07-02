package com.serialize;

import android.support.v7.app.AppCompatActivity;

public class SerialActivity extends AppCompatActivity{
//	private Button btn1;
//	private Button btn2;
//	private TextView textView;
//	private String filepath;
//	private AndroidDevelop oldDev, newDev;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_serial);
//		filepath = Environment.getExternalStorageDirectory() + "/cache.txt";
//		btn1 = (Button) findViewById(R.id.serial_btn1);
//		btn2 = (Button) findViewById(R.id.serial_btn2);
//		textView = (TextView) findViewById(R.id.serial_tv);
//		textView.setText("Initial state!");
//		btn1.setOnClickListener(this);
//		btn2.setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.serial_btn1:
//			serialize();
//
//			break;
//		case R.id.serial_btn2:
//			serialize();
//
//			break;
//		default:
//			break;
//		}
//	}
//
//	// 序列化
//	private void serialize() {
//		try {
//			File file = new File(filepath);
//			if (!file.getParentFile().exists()) {
//				file.getParentFile().mkdirs();
//			}
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//			oldDev = new AndroidDevelop("android", "xxx", 69.0);
//			textView.setText("Before serialize:\n name:android\n author:xxx\n price:69.0\n unique:" + oldDev.unique
//					+ "\n describe:" + oldDev.describ + "\n staticVar:" + oldDev.staticVar);
//			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
//			out.writeObject(oldDev);
//			out.close();
//
//			FileInputStream input = new FileInputStream(file.toString());
//			ObjectInputStream inputStream = new ObjectInputStream(input);
//			newDev = (AndroidDevelop)inputStream.readObject();
//			textView.setText("After serialize:\n name:" + newDev.name + "\n author:" + newDev.author + "\n price:"
//					+ newDev.price + "\n unique:" + newDev.unique + "\n describe:" + newDev.describ + "\n staticVar:"
//					+ newDev.staticVar);
//			input.close();
//			inputStream.close();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
}
