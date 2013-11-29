package com.example.mgr_mahinelearningocr;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageProcessingActivity extends Activity {
	
	ImageView image;
	String imagePath;
	Bitmap imageBitmap;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view_layout);
    	imagePath = getIntent().getStringExtra(Constants.IMAGE_PATH);
        if (imagePath != null) {
        	
        }
    }
	
	

}
