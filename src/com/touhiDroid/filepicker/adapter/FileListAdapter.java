/**
 * 
 */
package com.touhiDroid.filepicker.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.touhiDroid.filepicker.R;
import com.touhiDroid.filepicker.model.FileRow;

/**
 * @author Touhid
 * 
 */
public class FileListAdapter extends ArrayAdapter<FileRow> {

	private static final String tag = "FileListAdapter";
	private static Context tContext;

	public FileListAdapter(Context context, int textViewResourceId,
			List<FileRow> objects) {
		super(context, textViewResourceId, objects);
		Log.d(tag, "constructed.");
		tContext = context;
	}

	private class ViewHolder {
		TextView tvFileName, tvFileDesc;
		ImageView ivFileImg;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d(tag, "getView() : position=" + position);
		ViewHolder vHolder = null;

		LayoutInflater lInflater = (LayoutInflater) tContext
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = lInflater.inflate(R.layout.file_row, null);
			vHolder = new ViewHolder();
			vHolder.tvFileName = (TextView) convertView
					.findViewById(R.id.tv_file_name);
			vHolder.tvFileDesc = (TextView) convertView
					.findViewById(R.id.tv_file_desc);
			vHolder.ivFileImg = (ImageView) convertView
					.findViewById(R.id.iv_file_pic);

			FileRow fRow = getItem(position);
			vHolder.tvFileName.setText(fRow.getFileName());
			vHolder.tvFileDesc.setText(fRow.getFileDescription());
			Bitmap bmp = fRow.getFilePic();
			if (bmp != null)
				vHolder.ivFileImg.setImageBitmap(bmp);
			Log.d(tag, fRow.toString());
		} else
			vHolder = (ViewHolder) convertView.getTag();

		return convertView;
	}

}
