package com.example.nourhan.teamantony;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.LoginFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Antony on 1/8/2016.
 */
public class View_Single_Request extends AppCompatActivity {

    String officeName, nameOfEvent, ID;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_REQUEST = "request";
    private static final String TAG_ID = "ID";
    private static final String TAG_OFFICE_NAME = "officeName";
    private static final String TAG_NAME_OF_EVENT = "nameOfEvent";

    // requests JSONArray
    JSONArray response = null;

    // Progress Dialog
    private ProgressDialog pDialog;
    private ProgressDialog prDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    // url to get all requests list
    private static String url_get_request = "http://ashoka.students.acg.edu/" +
            "ws_test/get_request.php";

    //url to get last id
    private static String url_get_last_id = "http://ashoka.students.acg.edu/ws_test/get_last_ID_from_requests.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_single_request);

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
                new FindLastID().execute();
            } else {
                newString = extras.getString("ID");
                ID = extras.getString("ID");
                TextView ID = (TextView) findViewById(R.id.requestID);
                ID.setText(newString);
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("TAG_ID");
            ID = "111";
        }

//        ID = "142";

        //Load Additional Details
        new LoadSingleRequest().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Background Async Task to Load Single Request by making HTTP Request
     */
    class LoadSingleRequest extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prDialog = new ProgressDialog(View_Single_Request.this);
            prDialog.setMessage("Loading request. Please wait...");
            prDialog.setIndeterminate(false);
            prDialog.setCancelable(false);
            prDialog.show();
        }

        /**
         * getting request details from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ID", ID));

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_get_request, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("The JRequest: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // requests found
                    // Getting Array of Products
                    response = json.getJSONArray(TAG_REQUEST);
                    Log.e("Antony", "response= " + response);

                    // looping through All Products
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject c = response.getJSONObject(i);
                        Log.e("Antony", "c: " + c);

                        // Storing each json item in variable
                        ID = c.getString(TAG_ID);
                        officeName = c.getString(TAG_OFFICE_NAME);
                        nameOfEvent = c.getString(TAG_NAME_OF_EVENT);
                        Log.e("Antony", "ID: " + ID + " officeName" + officeName + "nameOfEvent" + nameOfEvent);
                    }
                } else {
                    // no request found
                    // Launch Add New request Activity
//                    Toast.makeText(View_Single_Request.this, "No requests found. Create a new one!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),
                            Create_New_Request.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {

            //Set Values To TextViews
            TextView officeNameTV = (TextView) findViewById(R.id.officeName);
            officeNameTV.setText(officeName);
            TextView nameOfEventTV = (TextView) findViewById(R.id.nameOfEvent);
            nameOfEventTV.setText(nameOfEvent);
            Log.e("Antony4", "ID: " + ID + " officeName " + officeName + " nameOfEvent " + nameOfEvent);


            // dismiss the dialog after getting all requests
            prDialog.dismiss();
        }

    }

    /**
     * Background Async Task to Load Single Request by making HTTP Request
     */
    class FindLastID extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(View_Single_Request.this);
            pDialog.setMessage("Loading last request. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting last ID from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_get_last_id, "GET", params);

            // Check your log cat for JSON response
            Log.d("The Response: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // request found
                    // Getting Array of Products
                    ID = Integer.toString(json.getInt(TAG_ID));
                    Log.e("Antony", "ID now: " + ID);

                } else {
                    // no request found
                    // Launch Add New request Activity
//                    Toast.makeText(View_Single_Request.this, "No requests found. Create a new one!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),
                            Create_New_Request.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {

            //Set ID TextView
            TextView IDView = (TextView) findViewById(R.id.requestID);
            IDView.setText(ID);

            // dismiss the dialog after getting last ID
            pDialog.dismiss();
        }
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, View_Request_History.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
