package com.touhiDroid.filepicker;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.touhiDroid.filepicker.adapter.FileListAdapter;
import com.touhiDroid.filepicker.model.FileRow;

public class FilePickerActivity extends Activity {

	private final String tag = "FilePickerActivity";

	@SuppressWarnings("unused")
	private static final String[] fileFormats = { "",
			// Reading file formats
			"pdf", "txt", "doc", "docx", "ppt", "pptx", "xls",
			// Programming language file formats
			"c", "cpp", "java", "py", "h", "xml", "php", "css", "js",
			// web-file formats
			"html", "htm", "aspx", "net",
			// Image file formats
			"jpg", "jpeg", "png", "bmp", "psd", "ai", "gif", "blend",
			// Compressed file formats
			"pkpass", "zip", "7z", "apk", "jar", "war", "bz", "tar", "bz2",
			"bzip2", "wim", "xz" };
	private String[] appFileFormats = { "pdf", "jpg", "jpeg", "png", "bmp",
			"psd", "ai", "gif", "pkpass",
			// TODO REMOVE TEST Formats
			"txt", "apk" };

	private ListView lvFileList;
	private ArrayList<FileRow> fileList;
	private ArrayAdapter<FileRow> adapterFileList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_picker);
		if (!isSdCardMounted()) {
			showMediNotMountedAlert();
			return;
		}
		// initialize views
		lvFileList = (ListView) findViewById(R.id.lv_files);
		fileList = new ArrayList<>();

		// List SD card files
		File sdCard = Environment.getExternalStorageDirectory();
		fileList = getAllFileList(sdCard.getAbsolutePath());

		// Set the adapter
		adapterFileList = new FileListAdapter(FilePickerActivity.this,
				R.layout.file_row, fileList);
		lvFileList.setAdapter(adapterFileList);
		lvFileList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO
				// File f = fileList.get(position).getFile();
				// if (f.isDirectory()) {
				// fileList = getAllFileList(f.getAbsolutePath());
				// adapterFileList.notifyDataSetChanged();
				// } else {
				//
				// }
			}

		});
	}

	private ArrayList<FileRow> getAllFileList(String absFilePath) {
		File rootFile = new File(absFilePath);
		File[] rootFiles = rootFile.listFiles();
		Log.d(tag, "getAllFileList : path = " + absFilePath
				+ ", no. of files: " + rootFiles.length);

		ArrayList<FileRow> dirList = new ArrayList<>();
		ArrayList<FileRow> dirFileList = new ArrayList<>();

		for (File f : rootFiles) {
			// TODO
			if (f.isDirectory()) {
				dirList.add(new FileRow(FilePickerActivity.this, f));
				Log.d(tag, "Directory: " + f.getName());
			} else if (f.isFile() && isFileFormatAc(f.getName())) {
				dirFileList.add(new FileRow(FilePickerActivity.this, f));
				Log.d(tag, "File: " + f.getName());
			}
		}
		Collections.sort(dirList, new Comparator<FileRow>() {
			@Override
			public int compare(FileRow fr1, FileRow fr2) {
				return fr1.getFileName().compareToIgnoreCase(fr2.getFileName());
			}
		});
		Collections.sort(dirFileList, new Comparator<FileRow>() {
			@Override
			public int compare(FileRow fr1, FileRow fr2) {
				return fr1.getFileName().compareToIgnoreCase(fr2.getFileName());
			}
		});
		dirList.addAll(dirFileList);
		return dirList;
	}

	private boolean isFileFormatAc(String fileName) {
		String filenameArray[] = fileName.split("\\.");
		String extension = filenameArray[filenameArray.length - 1];
		boolean isAcFormat = false;
		for (int i = 0; i < appFileFormats.length; i++) {
			if (extension.equals(appFileFormats[i])) {
				Log.d(tag, "Ac format: " + extension);
				isAcFormat = true;
				break;
			}
		}
		return isAcFormat;
	}

	private void showMediNotMountedAlert() {
		Builder alertDialog = new Builder(FilePickerActivity.this);
		alertDialog.setTitle("No Media");
		alertDialog.setMessage("The external storage is not mounted.");
		alertDialog.setNegativeButton("Back", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				finish();
			}
		});
		alertDialog.show();
	}

	private boolean isSdCardMounted() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED))
			return true;
		return false;
	}
}
