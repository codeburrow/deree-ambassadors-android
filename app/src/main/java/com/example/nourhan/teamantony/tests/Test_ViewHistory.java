package com.example.nourhan.teamantony.tests;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nourhan.teamantony.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nourhan on 06/12/2015.
 */
public class Test_ViewHistory extends Activity{

    private ListView requests;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.test_history_foo);

            //Populate the list
            requests = (ListView) findViewById(R.id.history_listView);

            List<String> requestsArray = new ArrayList<>();
            requestsArray.add("foo");
            requestsArray.add("bar");
            requestsArray.add("bar1");
            requestsArray.add("bar2");
            requestsArray.add("bar3");
            requestsArray.add("bar4");
            requestsArray.add("bar5");
            requestsArray.add("bar6");
            requestsArray.add("bar7");
            requestsArray.add("bar8");
            requestsArray.add("bar9");
            requestsArray.add("bar10");
            requestsArray.add("bar11");
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    requestsArray);

            requests.setAdapter(arrayAdapter);
        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
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

    }
