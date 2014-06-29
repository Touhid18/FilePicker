package com.touhiDroid.filepickertest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.touhiDroid.filepicker.FilePickerActivity;

public class MainActivity extends Activity {

	private final int FILE_CHOOSER = 10001;

	private TextView tv1;
	private ImageView iv1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv1 = (TextView) findViewById(R.id.tv_file_type);
		iv1 = (ImageView) findViewById(R.id.iv_1);

		Intent intent = new Intent(MainActivity.this, FilePickerActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivityForResult(intent, FILE_CHOOSER);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == FILE_CHOOSER) {
			byte[] baFile = data
					.getByteArrayExtra(FilePickerActivity.FILE_BYTE_ARRAY_KEY);
			int fileFormatKey = data.getIntExtra(
					FilePickerActivity.FILE_FORMAT_KEY, 0);
			if (fileFormatKey == FilePickerActivity.PDF_FILE)
				tv1.setText("A pdf file is chosen.");
			else if (fileFormatKey == FilePickerActivity.PASSBOOK_FILE)
				tv1.setText("A passbook file is chosen");
			else if (fileFormatKey == FilePickerActivity.IMAGE_FILE) {
				handleImageFile(baFile);
			}
		}
	}

	private void handleImageFile(byte[] baFile) {
		tv1.setText("Image file is chosen.");
		Bitmap bmp = BitmapFactory.decodeByteArray(baFile, 0, baFile.length);
		if (bmp != null)
			iv1.setImageBitmap(bmp);
	}

}
