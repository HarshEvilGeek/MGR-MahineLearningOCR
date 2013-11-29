package com.example.mgr_machinelearningocr;

import java.io.File;

import com.example.mgr_machinelearningocr.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
	
	String imageFilePath;
	String imageDirPath;
	
	private boolean photoTaken;
	
	String logTitle = "MGR : ";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_layout);
        captureNewImageButton = (Button) findViewById(R.id.captureNewImageButton);
        processExistingImageButton = (Button) findViewById(R.id.processExistingImageButton);

        imageDirPath = this.getApplicationContext().getFilesDir().getAbsolutePath() + "/MGR/";
		
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
		
		File MGRDir = new File (imageDirPath);
		MGRDir.mkdirs();
		
		imageFilePath = imageDirPath + System.currentTimeMillis() + "_img.jpg";
		
		//File file = new File( imageDirPath, System.currentTimeMillis() + "_img.jpg" );
		ContentValues contentValues = new ContentValues();
		contentValues.put(MediaStore.Images.Media.TITLE , imageFilePath);
		// Uri outputFileUri = Uri.fromFile( file );
	    Uri capturedImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI , contentValues);	
	    imageFilePath = getRealPathFromURI(capturedImageUri);
	    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
	    intent.putExtra( MediaStore.EXTRA_OUTPUT, capturedImageUri );
	    	
	    startActivityForResult( intent, 2 );
		
	}
	
	public String getRealPathFromURI(Uri contentUri) {
	    String[] proj = { MediaStore.Images.Media.DATA };
	    Cursor cursor = managedQuery(contentUri, proj, null, null, null);
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    return cursor.getString(column_index);
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
	    intent.putExtra(Constants.IMAGE_PATH, imageFilePath);
	    startActivity(intent);
	    finish();
	}
	
	private void selectImageFile() {
		
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override 
    protected void onSaveInstanceState (Bundle outState) {
    	outState.putBoolean( Constants.PHOTO_TAKEN, photoTaken );
    }
    
    @Override 
    protected void onRestoreInstanceState( Bundle savedInstanceState)
    {
        Log.i( "MakeMachine", "onRestoreInstanceState()");
        if( savedInstanceState.getBoolean( Constants.PHOTO_TAKEN ) ) {
         onPhotoTaken();
        }
    }
}

