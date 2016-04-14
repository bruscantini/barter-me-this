package com.matchfacts.anthony.bartermethis;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class HomeActivity extends AppCompatActivity {

    Bitmap inputImage;
    ImageView profilePic;
    private static String url_profilePic = "http://matchfacts.asuscomm.com:8888/" +
            "android_connect/pictures/robby.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayShowCustomEnabled(true);

        ImageButton chestButton = (ImageButton) findViewById(R.id.action_chest);
        chestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ImageButton settingsButton = (ImageButton) findViewById(R.id.action_settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
            /*
            pDialog = new ProgressDialog(ShowPictureActivity.this);
            pDialog.setMessage("Loading picture. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            */
        }

        protected String doInBackground(String... args){
            profilePic = (ImageView) findViewById(R.id.img_profile_pic);
            try {
                inputImage = decodeRemoteBitmap(url_profilePic);
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
            // pDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    profilePic.setImageBitmap(inputImage);
                }
            });
        }
    }

}
