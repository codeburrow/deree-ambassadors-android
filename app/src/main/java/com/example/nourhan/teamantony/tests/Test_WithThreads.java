//package com.example.nourhan.teamantony.tests;
//
//        import android.app.Activity;
//        import android.content.Intent;
//        import android.os.Bundle;
//        import android.util.Log;
//        import android.view.Menu;
//        import android.view.MenuInflater;
//        import android.view.MenuItem;
//        import android.view.View;
//        import android.widget.CheckBox;
//        import android.widget.EditText;
//        import android.widget.NumberPicker;
//        import android.widget.RadioButton;
//
//        import com.example.nourhan.teamantony.MainActivity;
//        import com.example.nourhan.teamantony.R;
//
//        import java.sql.Connection;
//        import java.sql.DriverManager;
//        import java.sql.PreparedStatement;
//        import java.sql.SQLException;
//
//
///**
// * Created by nourhan on 06/12/2015.
// */
//public class Test_WithThreads extends Activity {
//
//
//    final static String username = "ashoka";
//    final static String password = "thisisfun0007";
//    final static String databaseURL = "jdbc:mysql://ashoka.students.acg.edu:3306/ashoka_Nourhan";
//    static Connection myConnection;
//    String office;
//    RadioButton formal;
//    NumberPicker amb_num;
//    String dressCode;
//    int ambNum;
//    String amb_arrival;
//    String amb_dep;
//    String meeting_loc;
//    String name;
//    String description;
//    String event_date;
//    String event_startTime;
//    String event_endTime;
//    CheckBox tour;
//    CheckBox other;
//    CheckBox social;
//    CheckBox presentation;
//    int typeID;
//    String pickup;
//    String dropoff;
//    String contactName;
//    String contactDetails;
//    String duties;
//
//    Thread thread = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            try {
//                try {
//                    Class.forName("com.mysql.jdbc.Driver");
//                    myConnection = DriverManager.getConnection(databaseURL, username, password);
//                    Log.d("HELLOOOOO", "CONNECTION SUCCESS!!");
//
//                } catch (ClassNotFoundException e) {
//                    Log.d("HELLOOOOO", "MySql Driver jar file not included in the project!" + e.getMessage());
//                } catch (SQLException e) {
//                    Log.d("HELLOOOOO", "Check username/password and Database URL " + e.getMessage());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    });
//
//    private PreparedStatement sqlPreparedStatement = null;
//    Thread insertRequest = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            try {
//                sqlPreparedStatement = myConnection.prepareStatement("Insert into requests (officeName, dressingCodeID, numberOfAmbassadorsRequested, ambassadorArrivalTime, ambassadorDepartureTime, meetingPlace, duties) values (?,?,?,?,?,?,?)");
//                sqlPreparedStatement.setString(1, office);
////                sqlPreparedStatement.setString(2, DateFormat.);
//                sqlPreparedStatement.setString(2, dressCode);
//                sqlPreparedStatement.setInt(3, ambNum);
//                sqlPreparedStatement.setString(4, amb_arrival);
//                sqlPreparedStatement.setString(5, amb_dep);
//                sqlPreparedStatement.setString(6, meeting_loc);
//                sqlPreparedStatement.setString(7, duties);
//                sqlPreparedStatement.executeUpdate();
//
//            } catch (SQLException s) {
//
//                System.out.println("SQL Error : Inserting data. " + s.getMessage());
//            }
//        }
//    });
//
//    Thread insertEvent = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            try {
//                sqlPreparedStatement = myConnection.prepareStatement("Insert into events (name," +
//                        "description, date," +
//                        "startTime," +
//                        "endTime," +
//                        "Location," +
//                        "Pickup," +
//                        "Dropoff," +
//                        "typeID) values (?,?,?,?,?,?,?,?,?)");
//                sqlPreparedStatement.setString(1, name);
//                sqlPreparedStatement.setString(2, description);
//                sqlPreparedStatement.setString(3, event_date);
//                sqlPreparedStatement.setString(4, event_startTime);
//                sqlPreparedStatement.setString(5, event_endTime);
//                sqlPreparedStatement.setString(6, meeting_loc);
//                sqlPreparedStatement.setString(7, pickup);
//                sqlPreparedStatement.setString(8, dropoff);
//                sqlPreparedStatement.setInt(9, typeID);
//
//                sqlPreparedStatement.executeUpdate();
//
//            } catch (SQLException s) {
//
//                System.out.println("SQL Error : Inserting data. " + s.getMessage());
//            }
//        }
//    });
//
//    Thread insertContact = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            try {
//                sqlPreparedStatement = myConnection.prepareStatement("Insert into contacts (name," +
//                        "details) values (?,?)");
//                sqlPreparedStatement.setString(1, contactName);
//                sqlPreparedStatement.setString(2, contactDetails);
//                sqlPreparedStatement.executeUpdate();
//
//            } catch (SQLException s) {
//
//                System.out.println("SQL Error : Inserting data. " + s.getMessage());
//            }
//        }
//    });
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.create_new_request);
//        thread.start();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void submitRequest(View view) {
//
//        EditText officeText = (EditText) findViewById(R.id.office);
//        office = officeText.getText().toString();
//
//        formal = (RadioButton) findViewById(R.id.formal_radiobut);
//        amb_num = (NumberPicker) findViewById(R.id.ambNum);
//
//        if (formal.isChecked()) {
//            dressCode = "Formal";
//        } else dressCode = "Casual";
//        ambNum = amb_num.getValue();
//
//        EditText ambArrivText = (EditText) findViewById(R.id.ambassadorArrival);
//        amb_arrival = ambArrivText.getText().toString();
//
//        EditText ambDepText = (EditText) findViewById(R.id.ambassadorDeparture);
//        amb_dep = ambDepText.getText().toString();
//
//
//        meeting_loc = "N/A";
//
//        EditText nameText = (EditText) findViewById(R.id.officeName);
//        name = nameText.getText().toString();
//        EditText descText = (EditText) findViewById(R.id.description);
//        description = descText.getText().toString();
//
//        EditText event_dateText = (EditText) findViewById(R.id.date);
//        event_date = event_dateText.getText().toString();
//
//        EditText event_startTimeText = (EditText) findViewById(R.id.startTime);
//        event_startTime = event_startTimeText.getText().toString();
//
//
//        EditText event_endTimeText = (EditText) findViewById(R.id.endTime);
//        event_endTime = event_endTimeText.getText().toString();
//
//
//        tour = (CheckBox) findViewById(R.id.campus_tour_checkbox);
//        presentation = (CheckBox) findViewById(R.id.presentation_checkbox);
//        other = (CheckBox) findViewById(R.id.other_checkbox);
//        social = (CheckBox) findViewById(R.id.social_event_checkbox);
//        pickup = "N/A";
//        dropoff = "N/A";
//        typeID = 0;
//        if (presentation.isChecked()) {
//            typeID = 2;
//        } else if (other.isChecked()) {
//            typeID = 4;
//        } else if (social.isChecked()) {
//            typeID = 3;
//        }
//        if (tour.isChecked()) {
//            EditText pickupText = (EditText) findViewById(R.id.pickup);
//            EditText dropoffText = (EditText) findViewById(R.id.dropOff);
//            pickup = pickupText.getText().toString();
//            dropoff = dropoffText.getText().toString();
//            typeID = 1;
//        } else {
//            EditText meeting_locText = (EditText) findViewById(R.id.meetingPlace);
//            meeting_loc = meeting_locText.getText().toString();
//        }
//
//
//        EditText contactNameText = (EditText) findViewById(R.id.contactPerson);
//        contactName = contactNameText.getText().toString();
//
//        EditText contactDetailsText = (EditText) findViewById(R.id.contactDetails);
//        contactDetails = contactDetailsText.getText().toString();
//
//        EditText dutiesText = (EditText) findViewById(R.id.duties);
//        duties = dutiesText.getText().toString();
//
//        insertRequest.start();
//        insertEvent.start();
//        insertContact.start();
//
//        Intent back = new Intent(this, MainActivity.class);
//        startActivity(back);
//    }
//
//    public void cancel(View view) {
//        Intent back = new Intent(this, MainActivity.class);
//        startActivity(back);
//    }
//
//    Thread testTread = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            try {
//                sqlPreparedStatement = myConnection.prepareStatement("Insert into Users (name) values (?)");
//                sqlPreparedStatement.setString(1, "BLAH BLAH 2");
//                sqlPreparedStatement.executeUpdate();
//            } catch (SQLException s) {
//                System.out.println("SQL Error : Inserting data. " + s.getMessage());
//            }
//        }
//    });
//
//}
