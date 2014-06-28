package com.touhiDroid.filepicker;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;

public class FilePickerActivity extends ActionBarActivity {

	@SuppressWarnings("unused")
	private static final String[] fileFormats = { "",
			// Reading file formats
			"pdf", "txt", "doc", "docx", "ppt", "pptx", "xls",
			// Programming language file formats
			"c", "cpp", "java", "py", "h", "xml", "php", "css", "js",
			// web-file formats
			"html","htm", "aspx", "net",
			// Image file formats
			"jpg", "jpeg", "png", "bmp", "psd", "ai", "gif", "blend",
			// Compressed file formats
			"pkpass", "zip", "7z", "apk", "jar", "war", "bz", "tar", "bz2",
			"bzip2", "wim", "xz" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_picker);
		if(!isSdCardMounted()){
			showMediNotMountedDialog();
			return;
		}
	}
	
	private void showMediNotMountedDialog() {
	}

	private boolean isSdCardMounted(){
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED))
			return true;
		return false;
	}
}
