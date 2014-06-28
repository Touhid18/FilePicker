/**
 * 
 */
package com.touhiDroid.filepicker.model;

import com.touhiDroid.filepicker.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @author Touhid
 * 
 */
public class FileRow {

	private String fileName, fileDescription;
	private Bitmap filePic;

	/**
	 * File name & file description field will be set to "<Not Found>", while
	 * file picture will be null when this constructor is called.
	 */
	public FileRow() {
		fileName = fileDescription = "<Not Found>";
		filePic = null;
	}

	/**
	 * The explicit constructor for a file-row taking its 3 parameters.
	 * 
	 * @param fileName
	 *            Main file name
	 * @param fileDescription
	 *            If file, then its size in KB or if folder, then the number of
	 *            files inside it
	 * @param filePic
	 *            File image according to its type.
	 * */
	public FileRow(String fileName, String fileDescription, Bitmap filePic) {
		this.fileName = fileName;
		this.fileDescription = fileDescription;
		this.filePic = filePic;
	}

	/**
	 * The constructor retaining the folder image as the file row image.
	 * 
	 * @param fileName
	 *            Main file name
	 * @param fileDescription
	 *            If file, then its size in KB or if folder, then the number of
	 *            files inside it
	 * */
	public FileRow(String fileName, String fileDescription) {
		this.fileName = fileName;
		this.fileDescription = fileDescription;
	}

	/**
	 * Default folder picture will be set as the file row image, so the context
	 * is required to create the bitmap.
	 * 
	 * @param context
	 *            Working context to setup the file row image as a folder
	 *            picture.
	 * @param fileName
	 *            Main file name
	 * @param fileDescription
	 *            If file, then its size in KB or if folder, then the number of
	 *            files inside it
	 */
	public FileRow(Context context, String fileName, String fileDescription) {
		this.fileName = fileName;
		this.fileDescription = fileDescription;
		this.filePic = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_launcher);
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileDescription
	 */
	public String getFileDescription() {
		return fileDescription;
	}

	/**
	 * @param fileDescription
	 *            the fileDescription to set
	 */
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}

	/**
	 * @return the filePic
	 */
	public Bitmap getFilePic() {
		return filePic;
	}

	/**
	 * @param filePic
	 *            the filePic to set
	 */
	public void setFilePic(Bitmap filePic) {
		this.filePic = filePic;
	}

	@Override
	public String toString() {
		return "File Name: " + fileName + ", File Description: "
				+ fileDescription + ", Bitmap: "
				+ (filePic == null ? "Doesn't exist." : "Exists.");
	}

}
