package com.example.mgr_mahinelearningocr;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	Button captureNewImageButton;
	Button processExistingImageButton;
	
	String imagePath;
	
	String logTitle = "MGR : ";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_layout);
        captureNewImageButton = (Button) findViewById(R.id.captureNewImageButton);
        processExistingImageButton = (Button) findViewById(R.id.processExistingImageButton);
        
        imagePath = Environment.getExternalStorageDirectory() + "/MGR/";
        captureNewImageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				captureNewImage();
				
			}
		});
        
        processExistingImageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectImageFile();
				
			}
		});
        
    }



	private void captureNewImage() {
		File imageFile = new File(imagePath);
		Uri outputFileUri = Uri.fromFile(imageFile);
		
		Intent intent = new Intent (android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		
		startActivityForResult( intent, 0);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{ 
	    Log.i( logTitle, "resultCode: " + resultCode );
	    switch( resultCode )
	    {
	     case 0:
	      Log.i( logTitle, "User cancelled" );
	      break;
	       
	     case -1:
	      onPhotoTaken();
	      break;
	    }
	}
	
	protected void onPhotoTaken()
	{
	    Intent intent = new Intent(this, ImageProcessingActivity.class);
	    intent.putExtra(Constants.IMAGE_PATH, imagePath);
	    startActivity(intent);
	}
	
	private void selectImageFile() {
		
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
