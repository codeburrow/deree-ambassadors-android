package com.example.nourhan.teamantony;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Antony on 1/7/2016.
 */
public class View_Request_History extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> requestsList;

    // url to get all requests list
    private static String url_view_requests = "http://ashoka.students.acg.edu/" +
            "ws_test/view_requests.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_REQUESTS = "requests";
    private static final String TAG_ID = "ID";
    private static final String TAG_OFFICE_NAME = "officeName";
    private static final String TAG_NAME_OF_EVENT = "nameOfEvent";
    private static final String TAG_DRESSING_CODE_ID = "dressingCodeID";

    // requests JSONArray
    JSONArray requests = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_request_history);

        // Hashmap for ListView
        requestsList = new ArrayList<HashMap<String, String>>();

        // Loading requests in Background Thread
        new LoadAllRequests().execute();

        // Get listview
        ListView lv = getListView();

        // on selecting single request
        // launching View Single Request Screen
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //Take ID from TextView
                //String HashMape = parent.getItemAtPosition(position).toString();
                HashMap<String, String> theHashMap = (HashMap) parent.getItemAtPosition(position);
                String ID = theHashMap.get("ID");
                //Log.e("Antony", "HashMape: " + HashMape + "ID: " + ID + "Position: " + position);

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        View_Single_Request.class);
                // sending ID to next activity
                in.putExtra(TAG_ID, ID);

                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });

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

    // Response from Edit Product Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     */
    class LoadAllRequests extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(View_Request_History.this);
            pDialog.setMessage("Loading requests. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All requests from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_view_requests, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Requests: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // requests found
                    // Getting Array of Products
                    requests = json.getJSONArray(TAG_REQUESTS);

                    // looping through All Products
                    for (int i = 0; i < requests.length(); i++) {
                        JSONObject c = requests.getJSONObject(i);

                        // Storing each json item in variable
                        String ID = c.getString(TAG_ID);
                        String nameOfEvent = c.getString(TAG_NAME_OF_EVENT);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_ID, ID);
                        map.put(TAG_NAME_OF_EVENT, nameOfEvent);

                        // adding HashList to ArrayList
                        requestsList.add(map);
                    }
                } else {
                    // no requests found
                    // Launch Add New product Activity
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
            // dismiss the dialog after getting all requests
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            View_Request_History.this, requestsList,
                            R.layout.view_request_history_single, new String[]{TAG_ID,
                            TAG_NAME_OF_EVENT},
                            new int[]{R.id.theID, R.id.theNameOfEvent});

                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}