package com.android.comehere;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.android.comehere.entity.Info;
import com.android.comehere.util.GeoHash;
import com.android.comehere.util.ImageUtil;

public class UploadActivity extends BaseActivity implements OnClickListener {

	private ImageView imageView;
	private TextView textView;
	private EditText commentText;
	private Button uploadButton;
	private String photoPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bmob.initialize(this, "488226cb84abd3bbe6ccb5f0953c31bc");
		setContentView(R.layout.activity_upload);
		showImage();
		uploadButton = (Button) findViewById(R.id.upload);
		uploadButton.setOnClickListener(this);
		commentText = (EditText)findViewById(R.id.commentText);

	}

	private void showImage() {
		Intent intent = this.getIntent();
		photoPath = intent.getStringExtra("photoPath");
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 从解码器中获得原始图片的宽高，而避免申请内存空间
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(photoPath, options);

		options.inSampleSize = calculateInSampleSize(options, 500, 500);
		options.inJustDecodeBounds = false;
		Bitmap newBitmap = BitmapFactory.decodeFile(photoPath, options);
		Bitmap newBitmap2 = newBitmap.copy(Bitmap.Config.ARGB_8888, true);

		Canvas canvas = new Canvas(newBitmap2);
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setTextSize(50);
		canvas.drawText("hello world", 50, 50, paint);
		imageView.setImageBitmap(newBitmap2);
	}

	/**
	 * 指定输出图片的缩放比例
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// 获得原始图片的宽高
		int imageHeight = options.outHeight;
		int imageWidth = options.outWidth;
		int inSimpleSize = 1;
		if (imageHeight > reqHeight || imageWidth > reqWidth) {

			// 计算压缩的比例：分为宽高比例
			final int heightRatio = Math.round((float) imageHeight
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) imageWidth
					/ (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSimpleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSimpleSize;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.upload:
			upload(photoPath);
			break;

		default:
			break;
		}

	}

	private void upload(String filePath) {
		// dialog = new ProgressDialog(MainActivity.this);
		// dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		// dialog.setTitle("上传中...");
		// dialog.setIndeterminate(false);
		// dialog.setCancelable(false);
		// dialog.setCanceledOnTouchOutside(false);
		// dialog.show();
		Bitmap bitmap = ImageUtil.compressImageFromFile(filePath);
		filePath = ImageUtil.saveToSdCard(bitmap, this);

		final BmobFile bmobFile = new BmobFile(new File(filePath));
		bmobFile.upload(this, new UploadFileListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				afterUploadPhoto(bmobFile);
				Log.i("life",
						"文件上传成功，返回的名称--"
								+ bmobFile.getFileUrl(UploadActivity.this));
			}

			@Override
			public void onProgress(Integer arg0) {
				// TODO Auto-generated method stub
				Log.i("life", "uploadMovoieFile-->onProgress:" + arg0);
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				showToast("-->uploadMovoieFile-->onFailure:" + arg0 + ",msg = "
						+ arg1);
			}

		});

	}

	/**
	 * 插入对象
	 */
	private void afterUploadPhoto(BmobFile file) {
		String url = file.getUrl();
		String latitude;
		String longitude;
		ExifInterface exifInterface2 = null;
		try {
			exifInterface2 = new ExifInterface(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		float[] latlong = new float[2];
		exifInterface2.getLatLong(latlong);
		latitude = String.valueOf(latlong[0]);
		longitude = String.valueOf(latlong[1]);

		final Info photo = new Info();
		photo.setLatitude(Double.parseDouble(latitude));
		photo.setLongitude(Double.parseDouble(longitude));

		photo.setName("我的位置");
		GeoHash geohash = new GeoHash();
		String geoHashStr = geohash.encode(latlong[0], latlong[1]);
		photo.setGeoHash(geoHashStr);
		photo.setCreateTime(new BmobDate(new Date()));
		photo.setComment(commentText.getText().toString());
		photo.setPhoto(file);
		photo.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("创建数据成功：" + photo.getObjectId() + photo.getComment());
				Log.d("bmob", "objectId = " + photo.getObjectId());
				Log.d("bmob", "name =" + photo.getName());
				Log.d("bmob", "latitude =" + photo.getLatitude());
				Log.d("bmob", "longtitude =" + photo.getLongitude());
				Log.d("bmob", "create time =" + photo.getCreateTime());
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("创建数据失败：" + msg);
			}
		});
	}

}
