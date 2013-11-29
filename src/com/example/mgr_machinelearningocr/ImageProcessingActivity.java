package com.example.mgr_machinelearningocr;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageProcessingActivity extends Activity {
	
	ImageView imageView;
	String imagePath;
	Bitmap imageBitmap;
	boolean[] colHasBlacks;
	boolean[] rowHasBlacks;
	int minRow=10000, maxRow=0, minCol=10000, maxCol=0;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view_layout);
    	imagePath = getIntent().getStringExtra(Constants.IMAGE_PATH);
    	imageView = (ImageView) findViewById(R.id.imageForProcessing);
        if (imagePath != null) {
        	BitmapFactory.Options options = new BitmapFactory.Options();
        	options.inMutable = true;
    		imageBitmap = BitmapFactory.decodeFile(imagePath, options);
    		int h = imageBitmap.getHeight();
    		int w = imageBitmap.getWidth();
    		colHasBlacks = new boolean[w];
    		rowHasBlacks = new boolean[h];
    		for (int i = 0; i < w; i++)
    			colHasBlacks[i] = false;
    		for (int i = 0; i < h; i++)
    			rowHasBlacks[i] = false;
    		if(w>h)
    			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    		else
    			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    		imageView.setImageBitmap(imageBitmap);
        }
        imageView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				int x = (int)event.getX();
				int y = (int)event.getY();
				int width = imageView.getWidth();
				int height = imageView.getHeight();
				x = (x * imageBitmap.getWidth()) / width;
				y = (y * imageBitmap.getHeight()) / height;
				//int pixel = imageBitmap.getPixel(x, y);
				/*
				for (int i = x-50 ; i < x+ 50; i++) {
					for (int j = y-50 ; j < y+50; j++) {
						imageBitmap.setPixel(i, j, Color.RED);
					}
				}*/
				//here we call process method but just to test, change the color of the pixel for now
				identify(x, y);
				return false;
			}
		});
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_options, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
     
     switch (item.getItemId()) {
        case R.id.contrast:
         contrast();
          break;
        case R.id.Process:
         process();

        default:
          break;
        }

        return true;
     
    }
    
	private void contrast() {
		Toast.makeText(this, "in contrast", Toast.LENGTH_SHORT)
        .show();
		for (int i = minCol; i < maxCol; i++) {
			for (int j =minRow; j < maxRow; j++) {
				int p = imageBitmap.getPixel(i,j);
				int R = (p >> 16) & 0xff;
				int G = (p >> 8) & 0xff;
				int B = p & 0xff;
				int sum = R+G+B;
				if (sum > 250)
					imageBitmap.setPixel(i, j, Color.WHITE);
				else if (sum > 100);
				else {
					imageBitmap.setPixel(i, j, Color.BLACK);
					colHasBlacks[j] = true;
					rowHasBlacks[i] = true;
					if(j>maxCol)
						maxCol=j;
					if(j<minCol)
						minCol=j;
					if(i>maxRow)
						maxRow=j;
					if(i<minRow)
						minRow=j;
				}
			}
		}
		imageView.setImageBitmap(imageBitmap);
	}
	
	private void identify(int x, int y) {
		
	    minRow = maxRow = y;
	    minCol = maxCol = x;
	    
	    int maxWidth = imageBitmap.getWidth();
	    int maxHeight = imageBitmap.getHeight();
	    
	    while( minRow > 0) {
	    	boolean flag = false;
	    	for(int i = Math.max(x -1000, 0); i< Math.min(x+1000, maxWidth); i++) {
	    		int p = imageBitmap.getPixel(i,minRow);
				int R = (p >> 16) & 0xff;
				int G = (p >> 8) & 0xff;
				int B = p & 0xff;
				int sum = R+G+B;
				if (sum < 250) {
					minRow--;
					flag = true;
					break;
				}
	    	}
			if(!flag)
				break;
	    }
	    
	    while( maxRow < maxHeight) {
	    	boolean flag = false;
	    	for(int i = Math.max(x -1000, 0); i< Math.min(x+1000, maxWidth); i++) {
	    		int p = imageBitmap.getPixel(i,maxRow);
				int R = (p >> 16) & 0xff;
				int G = (p >> 8) & 0xff;
				int B = p & 0xff;
				int sum = R+G+B;
				if (sum < 250) {
					maxRow++;
					flag = true;
					break;
				}
	    	}
			if(!flag)
				break;
	    	
	    }
	    
	    while( minCol > 0) {
	    	boolean flag = false;
	    	for(int i = Math.max(y -1000, 0); i< Math.min(y+1000, maxWidth); i++) {
	    		int p = imageBitmap.getPixel(minCol, i);
				int R = (p >> 16) & 0xff;
				int G = (p >> 8) & 0xff;
				int B = p & 0xff;
				int sum = R+G+B;
				if (sum < 250) {
					minCol--;
					flag = true;
					break;
				}
	    	}
			if(!flag)
				break;
	    	
	    }
	    
	    while( maxCol < maxWidth) {
	    	boolean flag = false;
	    	for(int i = Math.max(y -1000, 0); i< Math.min(y+1000, maxWidth); i++) {
	    		int p = imageBitmap.getPixel(maxCol, i);
				int R = (p >> 16) & 0xff;
				int G = (p >> 8) & 0xff;
				int B = p & 0xff;
				int sum = R+G+B;
				if (sum < 250) {
					maxCol++;
					flag = true;
					break;
				}
	    	}
			if(!flag)
				break;
	    	
	    }
	    for (int i = minRow ; i < maxRow; i++) {
			for (int j = minCol ; j < maxCol; j++) {
				imageBitmap.setPixel(j, i, Color.RED);
			}
		}
	    imageView.setImageBitmap(imageBitmap);
	    contrast();
		/*
		Toast.makeText(this, "in identify", Toast.LENGTH_SHORT)
        .show();
		for(int i = minCol; i < maxCol; i++) {
			imageBitmap.setPixel(maxRow, i, Color.GREEN);
			imageBitmap.setPixel(maxRow+1, i, Color.GREEN);
			imageBitmap.setPixel(minRow, i, Color.GREEN);
			imageBitmap.setPixel(minRow-1, i, Color.GREEN);
		}

		for(int i = minRow; i < maxRow; i++) {
			imageBitmap.setPixel(i, maxCol, Color.GREEN);
			imageBitmap.setPixel(i, maxCol+1, Color.GREEN);
			imageBitmap.setPixel(i, maxCol+2, Color.GREEN);
			imageBitmap.setPixel(i, maxCol+3, Color.GREEN);
			imageBitmap.setPixel(i, maxCol+4, Color.GREEN);
			imageBitmap.setPixel(i, minCol, Color.GREEN);
			imageBitmap.setPixel(i, minCol-1, Color.GREEN);
			imageBitmap.setPixel(i, minCol-2, Color.GREEN);
			imageBitmap.setPixel(i, minCol-3, Color.GREEN);
			imageBitmap.setPixel(i, minCol-4, Color.GREEN);
		}*/
	}
	
	private void process() {
		Toast.makeText(this, "in processing", Toast.LENGTH_SHORT)
        .show();
	}
	
	@Override
	public void onBackPressed () {
		Intent intent = new Intent( this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}
