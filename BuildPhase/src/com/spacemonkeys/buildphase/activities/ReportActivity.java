package com.spacemonkeys.buildphase.activities;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.spacemonkeys.buildphase.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * launch choose activity to get URI of pic to upload
 * upload pic via POST in async task
 * connect to DB and submit text data from form
 * close all remote connections
 */

public class ReportActivity extends Activity implements OnClickListener, LocationListener{
    
	private static int RESULT_LOAD_IMAGE = 1;
	private static String TAG = "TAG";
	private ImageView img;
	private Button submit;
	private String picturePath, assetPath, assetName;
	private Bitmap bitmapScaled;
	private File file;
    private LocationManager locationManager ;
    private String provider;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);    
        
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        
        Date d = new Date();
        CharSequence s  = DateFormat.format("MMMM d, yyyy ", d.getTime());
        
        // Getting reference to TextView tv_longitude
        TextView date = (TextView)findViewById(R.id.date);
        date.setText(s);
        
        // Getting LocationManager object
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
 
        // Creating an empty criteria object
        Criteria criteria = new Criteria();
 
        // Getting the name of the provider that meets the criteria
        provider = locationManager.getBestProvider(criteria, false);
 
        if(provider!=null && !provider.equals("")){
 
            // Get the location from the given provider
            Location location = locationManager.getLastKnownLocation(provider);
 
            locationManager.requestLocationUpdates(provider, 2000, 1, this);
 
            if(location!=null)
                onLocationChanged(location);
            else
                Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
 
        }else{
            Toast.makeText(getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
        }

    	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);       		 
    	startActivityForResult(i, RESULT_LOAD_IMAGE);
    	
    }
    
    @Override
    public void onLocationChanged(Location location) {    
        // Getting reference to TextView tv_latitude
        TextView address = (TextView)findViewById(R.id.address);
        
        Geocoder geoCoder = new Geocoder(
                getBaseContext(), Locale.getDefault());
            try {
                List<Address> addresses = geoCoder.getFromLocation(
                    location.getLatitude(), 
                    location.getLongitude(), 10);

                String add = "";
                if (addresses.size() > 0) 
                {
                    for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); 
                         i++)
                       add += addresses.get(0).getAddressLine(i) + "\n";
                }

                Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
            }
            catch (IOException e) {                
                e.printStackTrace();
            }   
        
        address.setText((int)location.getLatitude() + (int)location.getLongitude());
        
    }
 
    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }
 
    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
    
    @Override
    public void onClick(View arg0) {
    	Log.w(TAG, "onClick BEGIN");
    	onTransportImg();
    	Log.w(TAG, "onClick END");
    }
    
    public void onTransportImg() {
    	Log.w(TAG, "onTransportImg BEGIN");
    	
    	 new ImageUpload().execute();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
             
            img = (ImageView) findViewById(R.id.picpreview);
            img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            
        	BitmapDrawable raw = (BitmapDrawable) img.getDrawable();
        	Bitmap bitmap = raw.getBitmap();
        	
        	int oldWidth = bitmap.getWidth();
        	int oldHeight = bitmap.getHeight();
        	int newWidth = 200;
            int newHeight = 200;
      
            float scaleWidth = ((float) newWidth) / oldWidth;
            float scaleHeight = ((float) newHeight) / oldHeight;

            // create a matrix for the manipulation
            Matrix matrix = new Matrix();
            // resize the bit map
            matrix.postScale(scaleWidth, scaleHeight);

        	// recreate the new Bitmap
        	bitmapScaled = Bitmap.createBitmap(bitmap, 0, 0,  newWidth, newHeight, matrix, true);
        	bitmapScaled = raw.getBitmap();
       	
        	assetPath = picturePath.substring(0, 28);
        	assetName = "transport" + picturePath.substring(29, picturePath.length());
        	Log.w(TAG, "TotalAssetPath: " + picturePath);
        	Log.w(TAG, "AssetPath: " + assetPath);
        	Log.w(TAG, "AssetName: " + assetName);
        	Log.w(TAG, "TotalAssetPath: " + assetPath + "/" + assetName);
        	file = new File(assetPath, "/" + assetName);
        	FileOutputStream outStream = null;
        	
        	Log.w(TAG, "About to start writing file");
        	
			try {
				Log.i(TAG, "Writing file");
				outStream = new FileOutputStream(file);
				bitmapScaled.compress(Bitmap.CompressFormat.JPEG, 50, outStream);
				Log.w(TAG, "bitmapScaledHeight: " + bitmapScaled.getHeight());
				Log.w(TAG, "bitmapScaledWidth: " + bitmapScaled.getWidth());
				
				outStream.flush();
				outStream.close();
				Log.i(TAG, "Finished writing file");
			} catch (Exception e) {
				e.printStackTrace();
				Log.w(TAG, "File write failed");
			}
			
			onTransportImg();
			
        }
    }
    
    private class ImageUpload extends AsyncTask<String, Void, String> {

        FTPClient ftpClient = new FTPClient();
    	
        @Override
        protected String doInBackground(String... params) {
        	Log.w(TAG, "Upload Started");
            	try {
                  		
            		ftpClient.connect(InetAddress.getByName("ftp.31stcenturydesigns.com"));
              	    ftpClient.login("mjdempsey", "C0d3F3st");
              	    ftpClient.changeWorkingDirectory("images");
              	    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                  	
              	    BufferedInputStream buffIn = null;
              	    Log.w(TAG, "FileName: " + assetName);
              		buffIn = new BufferedInputStream(new FileInputStream(file));
              		ftpClient.enterLocalPassiveMode();
              	    ftpClient.storeFile(assetName, buffIn);
              	    buffIn.close();
              	    ftpClient.logout();
              	    ftpClient.disconnect();
              	    Log.w(TAG, "Upload Successful");
              		
                  	} catch (Exception e) {
              			e.printStackTrace();
              			Log.w(TAG, "Upload Failed");
              		}              

              return "Executed";
        }      

        @Override
        protected void onPostExecute(String result) {
              
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

}