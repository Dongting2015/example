package com.android.comehere;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

public class WelcomeActivity extends Activity {

	private static final int GOTO_MAIN_ACTIVITY = 0;
	// 意图回传值的结果码
	private final int IMAGE_RESULT_CODE = 1;
	private Uri fileUri;
	public static final int MEDIA_TYPE_IMAGE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, 500);// 3秒跳转

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	private static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyCameraApp");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}
		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else {
			return null;
		}
		return mediaFile;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case GOTO_MAIN_ACTIVITY:
				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				// create a file to save the image
				fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); 
				 // set the image file name
				intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
				intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, IMAGE_RESULT_CODE);// 打开照相机
				// finish();//表示欢迎页面不再显示，回退就会退出程序
				int VERSION = android.os.Build.VERSION.SDK_INT;
				if (VERSION >= 5) {
					overridePendingTransition(R.anim.fadein, R.anim.fadeout);
				}
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == IMAGE_RESULT_CODE && resultCode == RESULT_OK) {
			Uri uri = null;

			if (data != null && data.getData() != null) {
				uri = data.getData();
			}

			// 一些机型无法从getData中获取uri，则需手动指定拍照后存储照片的Uri
			if (uri == null) {
				if (fileUri != null) {
					uri = fileUri;
				}
			}

			Intent intent = new Intent(WelcomeActivity.this, UploadActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("photoPath", fileUri.getPath());
			intent.putExtras(bundle);
			startActivity(intent);
			finish();
		}

	}

}
