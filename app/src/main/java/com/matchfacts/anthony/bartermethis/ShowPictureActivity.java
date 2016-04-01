package com.matchfacts.anthony.bartermethis;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ShowPictureActivity extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // URL to my picture
    private static String url_myFirstPicture = "http://matchfacts.asuscomm.com:8888/" +
            "android_connect/pictures/myFirstPicture.jpg";

    // My picture imageView
    ImageView myFirstPicture;
    Bitmap inputImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myFirstPicture = (ImageView) findViewById(R.id.myFirstPicture);

        // load my picture
        new LoadMyPicture().execute();


    }

    public static Bitmap decodeRemoteBitmap(String urlString) throws MalformedURLException, IOException {
        URL url = new URL(urlString);
        InputStream inputStream = url.openStream();
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }

    class LoadMyPicture extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ShowPictureActivity.this);
            pDialog.setMessage("Loading picture. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args){
            myFirstPicture = (ImageView) findViewById(R.id.myFirstPicture);
            try {
                inputImage = decodeRemoteBitmap(url_myFirstPicture);
            } catch (MalformedURLException m){
                m.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myFirstPicture.setImageBitmap(inputImage);
                }
            });
        }
    }

}
