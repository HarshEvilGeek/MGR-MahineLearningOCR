package com.example.mgr_machinelearningocr;

import com.example.mgr_machinelearningocr.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class ImageProcessingActivity extends Activity {
	
	ImageView imageView;
	String imagePath;
	Bitmap imageBitmap;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view_layout);
    	imagePath = getIntent().getStringExtra(Constants.IMAGE_PATH);
    	imageView = (ImageView) findViewById(R.id.imageForProcessing);
        if (imagePath != null) {
        	loadImageViewAndProcess(imagePath);
        }
    }

	private void loadImageViewAndProcess(String imagePath) {
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
		imageView.setImageBitmap(bitmap);
	}
	
	@Override
	public void onBackPressed () {
		Intent intent = new Intent( this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}
