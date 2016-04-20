package com.matchfacts.anthony.bartermethis;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.matchfacts.anthony.app.AppConfig;

public class EditProductActivity extends AppCompatActivity {

    EditText txtName;
    EditText txtPrice;
    EditText txtDesc;
    EditText txtCreatedAt;
    Button btnSave;
    Button btnDelete;

    String pid;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCT = "product";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";
    private static final String TAG_DESCRIPTION = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // save button
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        // getting product details from intent
        Intent i = getIntent();

        // getting product id (pid) from intent
        pid = i.getStringExtra(TAG_PID);

        // Getting complete product details in background thread
        new GetProductDetails().execute();

        // save button click event
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // starting background task to update product
                new SaveProductDetails().execute();
            }
        });

        // Delete button click event
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // deleting product in background thread
                new DeleteProduct().execute();
            }
        });
    }

    /**
     * Background Async Task to Get complete product details
     * */
    class GetProductDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProductActivity.this);
            pDialog.setMessage("Loading product details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
            //List<NameValuePair> params = new ArrayList<NameValuePair>();
            //params.add(new BasicNameValuePair("pid", pid));
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("pid", pid);

            // getting product details by making HTTP request
            // Note that product details url will use GET request
            JSONObject json = jsonParser.makeHttpRequest(
                    AppConfig.URL_PRODUCT_DETAILS, "GET", params);

            // check your log for json response
            Log.d("Single Product Details", json.toString());

            int success;
            try {

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // successfully received product details
                    JSONArray productObj = json.getJSONArray(TAG_PRODUCT); // JSON Array

                    // get first product object from JSON Array
                    final JSONObject product = productObj.getJSONObject(0);

                    runOnUiThread(new Runnable() {
                        public void run() {
                            // product with this pid found
                            // Edit Text
                            txtName = (EditText) findViewById(R.id.inputName);
                            txtPrice = (EditText) findViewById(R.id.inputPrice);
                            txtDesc = (EditText) findViewById(R.id.inputDesc);

                            try {
                                // display product data in EditText
                                txtName.setText(product.getString(TAG_NAME));
                                txtPrice.setText(product.getString(TAG_PRICE));
                                txtDesc.setText(product.getString(TAG_DESCRIPTION));
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    });
                }else{
                    // product with pid not found
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }

    /**
     * Background Async Task to  Save product Details
     * */
    class SaveProductDetails extends AsyncTask<String, String, String> {

        // getting updated data from EditTexts
        String name = txtName.getText().toString();
        String price = txtPrice.getText().toString();
        String description = txtDesc.getText().toString();

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProductActivity.this);
            pDialog.setMessage("Saving product ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

            name = txtName.getText().toString();
            price = txtPrice.getText().toString();
            description = txtDesc.getText().toString();
        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {



            // Building Parameters
            //List<NameValuePair> params = new ArrayList<NameValuePair>();
            //params.add(new BasicNameValuePair(TAG_PID, pid));
            //params.add(new BasicNameValuePair(TAG_NAME, name));
            //params.add(new BasicNameValuePair(TAG_PRICE, price));
            //params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));
            HashMap<String, String> params = new HashMap<String, String>();
            params.put(TAG_PID, pid);
            params.put(TAG_NAME, name);
            params.put(TAG_PRICE, price);
            params.put(TAG_DESCRIPTION, description);

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(AppConfig.URL_UPDATE_PRODUCT,
                    "POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully updated
                    Intent i = getIntent();
                    // send result code 100 to notify about product update
                    setResult(100, i);
                    finish();
                } else {
                    // failed to update product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
        }
    }

    /*****************************************************************
     * Background Async Task to Delete Product
     * */
    class DeleteProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProductActivity.this);
            pDialog.setMessage("Deleting Product...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Deleting product
         * */
        protected String doInBackground(String... args) {

            // Check for success tag
            int success;
            try {
                // Building Parameters
                //List<NameValuePair> params = new ArrayList<NameValuePair>();
                //params.add(new BasicNameValuePair("pid", pid));
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("pid", pid);

                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        AppConfig.URL_DELETE_PRODUCT, "POST", params);

                // check your log for json response
                Log.d("Delete Product", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // product successfully deleted
                    // notify previous activity by sending code 100
                    Intent i = getIntent();
                    // send result code 100 to notify about product deletion
                    setResult(100, i);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();

        }

    }

}
