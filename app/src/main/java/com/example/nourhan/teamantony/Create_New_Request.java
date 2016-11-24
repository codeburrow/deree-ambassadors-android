package com.example.nourhan.teamantony;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nourhan on 01/01/2016.
 */
public class Create_New_Request extends AppCompatActivity {

    String office;
    boolean formal;

    NumberPicker amb_num;
    Calendar calendar = Calendar.getInstance();

    int ambNum;

    String dressCode, amb_arrival, typeOfEvent,
            amb_dep,
            meeting_loc,
            name,
            description,
            event_date,
            event_startTime,
            event_endTime,
            pickup,
            dropoff,
            contactName,
            contactDetails,
            duties, meetingTime, eventLocation;


    CheckBox tour,
            other,
            social,
            presentation;

    EditText officeText,
            nameText,
            descText,
            pickupText,
            dropoffText,
            meeting_locText,
            contactNameText,
            contactDetailsText,
            dutiesText, eventLocText;
    TextView date, arrival, departure, start, end, meeting;
    RadioButton is_formal, meetAmbYes, meetAmbNo;

    Button dateButton, meetingButton, startButton, endButton, arrivalButton, departureButton;


    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();


    // url to create new product
    private static String url_create_request = "http://ashoka.students.acg.edu/" +
            "ws_test/create_request.php";


    // JSON Node names
    private static final String TAG_SUCCESS = "success";


    public void initializeEditTexts() {
        officeText = (EditText) findViewById(R.id.office);
        nameText = (EditText) findViewById(R.id.final_name);
        descText = (EditText) findViewById(R.id.final_description);
        pickupText = (EditText) findViewById(R.id.pickup);
        dropoffText = (EditText) findViewById(R.id.dropOff);
        meeting_locText = (EditText) findViewById(R.id.meetingPlace);
        contactNameText = (EditText) findViewById(R.id.contactPerson);
        contactDetailsText = (EditText) findViewById(R.id.contactDetails);
        dutiesText = (EditText) findViewById(R.id.duties);
        eventLocText = (EditText) findViewById(R.id.location);

    }

    public void initializeCheckboxes() {

        presentation = (CheckBox) findViewById(R.id.presentation_checkbox);
        other = (CheckBox) findViewById(R.id.other_checkbox);
        social = (CheckBox) findViewById(R.id.social_event_checkbox);
        tour = (CheckBox) findViewById(R.id.campus_tour_checkbox);

    }

    public void initializeNumberpickers() {

        amb_num = (NumberPicker) findViewById(R.id.ambNum);
    }

    public void initializeTextViews() {

        arrival = (TextView) findViewById(R.id.arrival_tv);
        departure = (TextView) findViewById(R.id.dep_tv);
        meeting = (TextView) findViewById(R.id.meeting_tv);
        start = (TextView) findViewById(R.id.startTime_tv);
        end = (TextView) findViewById(R.id.endTime_tv);
        date = (TextView) findViewById(R.id.date_tv);
    }

    public void initializeButtons() {

        arrivalButton = (Button) findViewById(R.id.arrival_button);
        departureButton = (Button) findViewById(R.id.dep_button);
        startButton = (Button) findViewById(R.id.startTime_button);
        endButton = (Button) findViewById(R.id.endTime_button);
        dateButton = (Button) findViewById(R.id.date_button);
        meetingButton = (Button) findViewById(R.id.meeting_button);
        meetAmbYes = (RadioButton) findViewById(R.id.meet_amb_yes_radiobut);
        meetAmbNo = (RadioButton) findViewById(R.id.meet_amb_no_radiobut);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_request);

        initializeEditTexts();
        initializeCheckboxes();
        initializeNumberpickers();
        initializeTextViews();
        initializeButtons();

        is_formal = (RadioButton) findViewById(R.id.formal_radiobut);


/////////////////////////////////AMBASSADORS///////////////////////
        String[] nums = new String[44];
        for (int i = 0; i < nums.length; i++)
            nums[i] = Integer.toString(i);

        amb_num.setMinValue(1);
        amb_num.setMaxValue(41);
        amb_num.setWrapSelectorWheel(false);
        amb_num.setDisplayedValues(nums);
        amb_num.setValue(1);


        arrivalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(Create_New_Request.this, onTimeSetListenerArriv, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        });


        departureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(Create_New_Request.this, onTimeSetListenerDep, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(Create_New_Request.this, onDateSetListenerEvent, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ////// Event Start Time//////////

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(Create_New_Request.this, onTimeSetListenerStart, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        });

        meetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (meetAmbYes.isChecked()) {
                    new TimePickerDialog(Create_New_Request.this, onTimeSetListenerMeeting, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
                } else {
                    Toast.makeText(Create_New_Request.this, "You chose not to meet the ambassadors above...", Toast.LENGTH_SHORT).show();
                }

            }
        });

////// Event End Time//////////

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(Create_New_Request.this, onTimeSetListenerEnd, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        });

        meetAmbNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meeting_locText.setVisibility(View.INVISIBLE);
                meetingButton.setEnabled(false);

            }
        });

        meetAmbYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meeting_locText.setVisibility(View.VISIBLE);
                meetingButton.setEnabled(true);

            }
        });


    }

    public void cancelRequest(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void submitRequest(View view) {
        office = officeText.getText().toString();
        Log.v("Antony", "office: " + office);

        formal = is_formal.isChecked();
        Log.d("HELLOOOOOO", String.valueOf(formal));
        if (formal) {
            dressCode = "formal";
        } else dressCode = "casual";

        ambNum = amb_num.getValue();


        event_startTime = "'" + start.getText().toString() + ":00'";


        event_endTime = "'" + end.getText().toString() + ":00'";

        amb_arrival = "'" + arrival.getText().toString() + ":00'";

        amb_dep = "'" + departure.getText().toString() + ":00'";


        eventLocation = eventLocText.getText().toString();

        meetingTime = "'" + meeting.getText().toString() + ":00'";


        name = nameText.getText().toString();
        description = descText.getText().toString();

//        String eventDate;
        event_date = "'" + date.getText().toString() + "'";


        pickup = "N/A";
        dropoff = "N/A";
        typeOfEvent = "tour";

        if (presentation.isChecked()) {
            typeOfEvent = "presentation";
        } else if (other.isChecked()) {
            typeOfEvent = "other";
        } else if (social.isChecked()) {
            typeOfEvent = "social";
        }

        if (tour.isChecked()) {
            pickup = pickupText.getText().toString();
            dropoff = dropoffText.getText().toString();
            typeOfEvent = "tour";
        }

        contactName = contactNameText.getText().toString();
        contactDetails = contactDetailsText.getText().toString();
        duties = dutiesText.getText().toString();

        if (formal) {
            dressCode = "formal";
        } else dressCode = "casual";

        if (meetAmbNo.isChecked()) {
            meeting_loc = "N/A";

        } else meeting_loc = meeting_locText.getText().toString();


        //CHECK FOR ALL FIELDS
        if (name.isEmpty()) {
            Log.d("NAME", name);
            Toast.makeText(this, "Please make sure the Event's Name field is filled...", Toast.LENGTH_LONG).show();
        } else if (office.isEmpty()) {
            Log.d("NAME", office);
            Toast.makeText(this, "Please make sure the Office's Name field is filled...", Toast.LENGTH_LONG).show();
        } else if (date.getText().toString().isEmpty()) {
            Log.d("NAME", date.getText().toString());
            Toast.makeText(this, "Please make sure you specified the event date...", Toast.LENGTH_LONG).show();
        } else if (description.isEmpty()) {
            Log.d("NAME", description);
            Toast.makeText(this, "Please make sure the Event's Description field is filled...", Toast.LENGTH_LONG).show();
        } else if (!tour.isChecked() && !social.isChecked() && !other.isChecked() && !presentation.isChecked()) {
            Toast.makeText(this, "Please make sure you picked an Event Type...", Toast.LENGTH_LONG).show();
        } else if (eventLocation.isEmpty()) {
            Log.d("NAME TYPE", eventLocation);
            Toast.makeText(this, "Please make sure you specified where the event will take place...", Toast.LENGTH_LONG).show();
        } else if (start.getText().toString().isEmpty()) {
            Log.d("NAME", start.getText().toString());
            Toast.makeText(this, "Please make sure you specified when the event starts...", Toast.LENGTH_LONG).show();
        } else if (end.getText().toString().isEmpty()) {
            Log.d("NAME", end.getText().toString());
            Toast.makeText(this, "Please make sure ou specified when the event ends...", Toast.LENGTH_LONG).show();
        } else if (arrival.getText().toString().isEmpty()) {
            Log.d("NAME", start.getText().toString());
            Toast.makeText(this, "Please make sure you specified when the ambassadors should arrive...", Toast.LENGTH_LONG).show();
        } else if (end.getText().toString().isEmpty()) {
            Log.d("NAME", end.getText().toString());
            Toast.makeText(this, "Please make sure ou specified when the ambassadors should leave...", Toast.LENGTH_LONG).show();
        } else if ((pickupText.getText().toString().isEmpty() || dropoffText.getText().toString().isEmpty()) && tour.isChecked()) {
            Log.d("NAME", pickupText.getText().toString());
            Toast.makeText(this, "Please make sure you specified both a pickup and dropoff location for the tour...", Toast.LENGTH_LONG).show();
        } else if (amb_num.getValue() == 0) {
            Log.d("NAME", String.valueOf(amb_num.getValue()));
            Toast.makeText(this, "Please make sure you specified the number of ambassadors assisting...", Toast.LENGTH_LONG).show();
        } else if (dutiesText.getText().toString().isEmpty()) {
            Log.d("NAME", dutiesText.getText().toString());
            Toast.makeText(this, "Please make sure you specified duties of the ambassadors assisting...", Toast.LENGTH_LONG).show();
        } else if (meeting_loc.isEmpty() && meetAmbYes.isChecked()) {
            Log.d("HELLOOOOOO", meeting_loc);
            Toast.makeText(this, "You chose to meet up with our Ambassadors before the event, \n Please make sure you entered a meeting place...", Toast.LENGTH_LONG).show();
        } else if (meeting.getText().toString().equals("00:00") && meetAmbYes.isChecked()) {
            Log.d("HELLOOOOOO", meetingTime);
            Toast.makeText(this, "You chose to meet up with our Ambassadors before the event, \n Please make sure you entered a meeting time...", Toast.LENGTH_LONG).show();
        } else if (contactDetails.isEmpty() || contactName.isEmpty()) {
            Toast.makeText(this, "Please make sure you entered your contact details...", Toast.LENGTH_LONG).show();
        } else {
            new CreateRequest().execute();
//            new CreateEvent().execute();
//            new CreateContact().execute();
        }
    }

    /**
     * Background Async Task to Create new request
     */
    class CreateRequest extends AsyncTask<Object, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Create_New_Request.this);
            pDialog.setMessage("Sending Request..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating Request
         */
        protected String doInBackground(Object... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("officeName", office));
            params.add(new BasicNameValuePair("dressingCode", dressCode));
            params.add(new BasicNameValuePair("numberOfAmbassadorsRequested", Integer.toString(ambNum)));
            params.add(new BasicNameValuePair("ambassadorArrivalTime", amb_arrival));
            params.add(new BasicNameValuePair("ambassadorDepartureTime", amb_dep));
            params.add(new BasicNameValuePair("meetingContactPlace", meeting_loc));
            params.add(new BasicNameValuePair("meetingContactTime", meetingTime));
            params.add(new BasicNameValuePair("duties", duties));
            params.add(new BasicNameValuePair("nameOfEvent", name));
            params.add(new BasicNameValuePair("descriptionOfEvent", description));
            params.add(new BasicNameValuePair("dateOfEvent", event_date));
            params.add(new BasicNameValuePair("startTimeOfEvent", event_startTime));
            params.add(new BasicNameValuePair("endTimeOfEvent", event_endTime));
            params.add(new BasicNameValuePair("pickUpLocation", pickup));
            params.add(new BasicNameValuePair("dropOffLocation", dropoff));
            params.add(new BasicNameValuePair("typeOfEvent", typeOfEvent));
            params.add(new BasicNameValuePair("contactPerson", contactName));
            params.add(new BasicNameValuePair("contactInfo", contactDetails));
            params.add(new BasicNameValuePair("locationOfEvent", eventLocation));

            Log.v("Antony", "officeName: " + office);
            Log.v("Antony", "dressingCodeID: " + dressCode);
            Log.v("Antony", "NUMBER OF AMB: " + Integer.toString(ambNum));
            Log.v("Antony", "ARRIVAL: " + amb_arrival);
            Log.v("Antony", "DEP: " + amb_dep);
            Log.v("Antony", "MEETING: " + meeting_loc);
            Log.v("Antony", "DUTIES: " + duties);
            Log.v("Antony", "EVENT NAME: " + name);
            Log.v("Antony", "DESCRIPTION: " + description);
            Log.v("Antony", "DATE: " + event_date);
            Log.v("Antony", "START: " + event_startTime);
            Log.v("Antony", "END: " + event_endTime);
            Log.v("Antony", "PICKUP: " + dropoff);
            Log.v("Antony", "DROPOFF: " + dropoff);
            Log.v("Antony", "TYPE ID: " + typeOfEvent);

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_request,
                    "POST", params);

            // check log cat from response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created request
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    Log.v("Antony", "Failure to insert whatever");
                    // failed to create request
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
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }


    TimePickerDialog.OnTimeSetListener onTimeSetListenerStart = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String display;
            if (hourOfDay < 10) {
                display = "0" + hourOfDay;
            } else display = String.valueOf(hourOfDay);

            if (minute < 10) {

                display = display + ":0" + minute;

            } else display = display + ":" + minute;
            start.setText(display);

        }
    };

    TimePickerDialog.OnTimeSetListener onTimeSetListenerEnd = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String display;
            if (hourOfDay < 10) {
                display = "0" + hourOfDay;
            } else display = String.valueOf(hourOfDay);

            if (minute < 10) {

                display = display + ":0" + minute;

            } else display = display + ":" + minute;
            end.setText(display);

        }
    };


    TimePickerDialog.OnTimeSetListener onTimeSetListenerArriv = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String display;
            if (hourOfDay < 10) {
                display = "0" + hourOfDay;
            } else display = String.valueOf(hourOfDay);

            if (minute < 10) {

                display = display + ":0" + minute;

            } else display = display + ":" + minute;
            arrival.setText(display);

        }
    };

    TimePickerDialog.OnTimeSetListener onTimeSetListenerMeeting = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String display;
            if (hourOfDay < 10) {
                display = "0" + hourOfDay;
            } else display = String.valueOf(hourOfDay);

            if (minute < 10) {

                display = display + ":0" + minute;

            } else display = display + ":" + minute;
            meeting.setText(display);
        }
    };

    TimePickerDialog.OnTimeSetListener onTimeSetListenerDep = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String display;
            if (hourOfDay < 10) {
                display = "0" + hourOfDay;
            } else display = String.valueOf(hourOfDay);

            if (minute < 10) {

                display = display + ":0" + minute;

            } else display = display + ":" + minute;
            departure.setText(display);
        }
    };

    DatePickerDialog.OnDateSetListener onDateSetListenerEvent = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String display;
            if (monthOfYear < 10) {
                display = year + "-" + "0" + monthOfYear + 1;
            } else display = year + "-" + monthOfYear + 1;

            if (dayOfMonth < 10) {

                display = display + "-0" + dayOfMonth;

            } else display = display + "-" + dayOfMonth;
            date.setText(display);
        }
    };


}
