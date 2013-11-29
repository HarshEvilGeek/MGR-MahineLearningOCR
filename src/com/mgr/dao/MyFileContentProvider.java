/*package com.mgr.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mgr_machinelearningocr.R;

public class MyFileContentProvider extends ContentProvider {

	 public static final Uri CONTENT_URI = Uri.parse("content://com.mgr.image_OCR/");

	 private static final HashMap<String, String> MIME_TYPES = new HashMap<String, String>();

	 static {

	  MIME_TYPES.put(".jpg", "image/jpeg");

	  MIME_TYPES.put(".jpeg", "image/jpeg");

	 }

	 @Override

	 public boolean onCreate() {

	  try {

	   File mFile = new File(getContext().getFilesDir(), System.currentTimeMillis() + "_image.jpg");

	   if(!mFile.exists()) {

	    mFile.createNewFile();

	   }

	   getContext().getContentResolver().notifyChange(CONTENT_URI, null);

	   return (true);

	  } catch (Exception e) {

	   e.printStackTrace();

	   return false;

	  }

	 }

	 @Override

	 public String getType(Uri uri) {

	  String path = uri.toString();

	  for (String extension : MIME_TYPES.keySet()) {

	   if (path.endsWith(extension)) {

	    return (MIME_TYPES.get(extension));

	   }

	  }

	  return (null);

	 }

	 @Override

	 public ParcelFileDescriptor openFile(Uri uri, String mode)

	 throws FileNotFoundException {

	  File f = new File(getContext().getFilesDir(), "newImage.jpg");

	  if (f.exists()) {

	   return (ParcelFileDescriptor.open(f,

	     ParcelFileDescriptor.MODE_READ_WRITE));

	  }

	  throw new FileNotFoundException(uri.getPath());

	 }

	 @Override

	 public Cursor query(Uri url, String[] projection, String selection,

	   String[] selectionArgs, String sort) {

	  throw new RuntimeException("Operation not supported");

	 }

	 @Override

	 public Uri insert(Uri uri, ContentValues initialValues) {

	  throw new RuntimeException("Operation not supported");

	 }

	 @Override

	 public int update(Uri uri, ContentValues values, String where,

	   String[] whereArgs) {

	  throw new RuntimeException("Operation not supported");

	 }

	 @Override

	 public int delete(Uri uri, String where, String[] whereArgs) {

	  throw new RuntimeException("Operation not supported");

	 }

	} 


	Activity class Home.java

	 
	public class Home extends Activity implements OnClickListener{

	    /** Called when the activity is first created. *//*

	 private final int CAMERA_RESULT = 1;

	 private final String Tag = getClass().getName();

	  Button button1;

	 ImageView imageView1;

	     @Override

	    public void onCreate(Bundle savedInstanceState) {

	        super.onCreate(savedInstanceState);

	        setContentView(R.layout.main);

	      

	        button1 = (Button)findViewById(R.id.button1);

	        imageView1 = (ImageView)findViewById(R.id.imageView1);

	        button1.setOnClickListener(this);

	    }

	 public void onClick(View v) {

	  PackageManager pm = getPackageManager();

	    if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

	   Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

	   i.putExtra(MediaStore.EXTRA_OUTPUT, MyFileContentProvider.CONTENT_URI);

	   startActivityForResult(i, CAMERA_RESULT);

	  } else {

	   Toast.makeText(getBaseContext(), "Camera is not available", Toast.LENGTH_LONG).show();

	  }   }

	  

	 @Override

	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	  super.onActivityResult(requestCode, resultCode, data);

	  Log.i(Tag, "Receive the camera result");

	    if (resultCode == RESULT_OK && requestCode == CAMERA_RESULT) {

	   File out = new File(getFilesDir(), "newImage.jpg");

	     if(!out.exists()) {

	    Toast.makeText(getBaseContext(),

	      "Error while capturing image", Toast.LENGTH_LONG)

	      .show();

	    return;

	   }

	      Bitmap mBitmap = BitmapFactory.decodeFile(out.getAbsolutePath());

	   imageView1.setImageBitmap(mBitmap);

	  }

	 }

	  

	 @Override

	 protected void onDestroy() {

	  super.onDestroy();

	  imageView1 = null;

	 }

	}
*/