package com.touhiDroid.filepicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
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

@SuppressLint("DefaultLocale")
public class FilePickerActivity extends Activity {

	public static final int PDF_FILE = 1001;
	public static final int IMAGE_FILE = 1002;
	public static final int PASSBOOK_FILE = 1003;

	public static final String FILE_FORMAT_KEY = "file_format_key_touhiDoid";
	public static final String FILE_BYTE_ARRAY_KEY = "file_byte_array_key_touhiDoid";

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
			"psd", "ai", "gif", "pkpass" };

	private String curFilePath;

	private static ListView lvFileList;
	private static ArrayList<FileRow> fileList;
	private static ArrayAdapter<FileRow> adapterFileList;

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
				File f = ((FileRow) parent.getItemAtPosition(position))
						.getFile();
				Log.d(tag, f.getName() + ", position:" + position);
				if (f.isDirectory()) {
					fileList.clear();
					fileList.addAll(getAllFileList(f.getAbsolutePath()));
					Log.d(tag, "New list loaded with size=" + fileList.size());
					adapterFileList.notifyDataSetChanged();
				} else if (f.isFile()) {
					Intent data = new Intent();
					data.putExtra(FILE_BYTE_ARRAY_KEY, getFileByteArray(f));
					data.putExtra(FILE_FORMAT_KEY,
							getFileFormatKey(f.getName()));
					setResult(RESULT_OK, data);
					finish();
				}
			}

		});
	}

	private byte[] getFileByteArray(File f) {
		byte[] byteArray = null;
		try {
			InputStream inputStream = new FileInputStream(f);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024 * 8];
			int bytesRead = 0;

			while ((bytesRead = inputStream.read(b)) != -1) {
				bos.write(b, 0, bytesRead);
			}
			inputStream.close();
			byteArray = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteArray;
	}

	private int getFileFormatKey(String fileName) {
		String filenameArray[] = fileName.split("\\.");
		String extension = filenameArray[filenameArray.length - 1]
				.toLowerCase();
		if (extension.equals("pdf"))
			return PDF_FILE;
		else if (extension.equals("pkpass"))
			return PASSBOOK_FILE;
		else
			return IMAGE_FILE;
	}

	private ArrayList<FileRow> getAllFileList(String absFilePath) {
		curFilePath = absFilePath;
		File rootFile = new File(absFilePath);
		File[] rootFiles = rootFile.listFiles();
		Log.d(tag, "getAllFileList : path = " + absFilePath
				+ ", no. of files: " + rootFiles.length);

		ArrayList<FileRow> dirList = new ArrayList<>();
		ArrayList<FileRow> dirFileList = new ArrayList<>();

		for (File f : rootFiles) {
			// TODO
			Log.d(tag, "getAllFileList : Verifying: " + f.getName());
			if (f.isDirectory()) {
				dirList.add(new FileRow(FilePickerActivity.this, f));
				Log.d(tag, "getAllFileList : Directory: " + f.getName());
			} else if (f.isFile() && isFileFormatAc(f.getName())) {
				dirFileList.add(new FileRow(FilePickerActivity.this, f));
				Log.d(tag, "getAllFileList : File: " + f.getName());
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
		Log.d(tag,
				"getAllFileList : New list created with size: "
						+ dirList.size());
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

	@Override
	public void onBackPressed() {
		fileList.clear();
		File f = new File(curFilePath);
		String fPath = f.getParent();
		if (fPath.equals(null)
				|| curFilePath.equals(Environment.getExternalStorageDirectory()
						.getAbsolutePath())) {
			super.onBackPressed();
			return;
		} else {
			fileList.addAll(getAllFileList(fPath));
			Log.d(tag, "New list loaded with size=" + fileList.size());
			adapterFileList.notifyDataSetChanged();
		}

	}
}
