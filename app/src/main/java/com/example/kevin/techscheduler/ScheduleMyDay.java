package com.example.kevin.techscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Activity for the Schedule My Day screen where the user inserts their class schedule
 */
public class ScheduleMyDay extends AppCompatActivity implements View.OnClickListener {

    public static final String SCHEDULE = "schedule";

    Spinner locationSpinner;
    TimePicker startTimeSpinner;
    TimePicker endTimeSpinner;
    Button addButton;
    Button undoButton;
    Button clearButton;
    TextView locationText;
    Button showMap;

    ArrayAdapter<CharSequence> adapter1;
    String locationString;

    // contains the hourly forecasts from the main activity
    ArrayList<String[]> listOfHourlyForecasts;
    
    // will contain the user's schedule data
    ArrayList<String[]> listOfLocations;

    /**
     * On create, the button listeners for add, undo, clear, and showMap are initialized,
     * the spinner listener for the locations/buildings are initialized, and the timepicker listeners
     * are initialized. The locationText will list the users schedule.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_my_day);

        Intent intent = getIntent();
        listOfHourlyForecasts = (ArrayList<String[]>) intent.getSerializableExtra(MainActivity.ALL_HOURLY_FORECASTS);

        locationSpinner = (Spinner) findViewById(R.id.locationSpinner);
        adapter1 = ArrayAdapter.createFromResource(this, R.array.locations, android.R.layout.simple_dropdown_item_1line);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter1);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                locationString = ((String) adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        startTimeSpinner = (TimePicker) findViewById(R.id.startTimeSpinner);
        endTimeSpinner = (TimePicker) findViewById(R.id.endTimeSpinner);
        addButton = (Button) findViewById(R.id.addButton);
        undoButton = (Button) findViewById(R.id.undoButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        locationText = (TextView) findViewById(R.id.locationText);
        showMap = (Button) findViewById(R.id.showMap);

        addButton.setOnClickListener(this);
        undoButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        showMap.setOnClickListener(this);

        listOfLocations = new ArrayList<>();

        startTimeSpinner.setCurrentHour(12);
        startTimeSpinner.setCurrentMinute(0);

        endTimeSpinner.setCurrentHour(12);
        endTimeSpinner.setCurrentMinute(0);
    }

    /**
     * Sorts the list of users schedule data in ascending order of user's the time the user needs to be there.
     */
    public ArrayList<String[]> listOfLocationsChronoOrder(ArrayList<String[]> locations) {

        ArrayList<String[]> listOfLocationsCopy = (ArrayList<String[]>) locations.clone();

        if (!listOfLocationsCopy.isEmpty()) {

            for (int i = 1; i < listOfLocationsCopy.size(); i++) {
                int j = i;
                while (j > 0 &&
                        (Integer.valueOf(listOfLocationsCopy.get(j - 1)[1] + listOfLocationsCopy.get(j - 1)[2]) >
                                Integer.valueOf(listOfLocationsCopy.get(j)[1] + listOfLocationsCopy.get(j)[2]))) {

                    String[] swap = listOfLocationsCopy.get(j);
                    listOfLocationsCopy.remove(j);
                    listOfLocationsCopy.add(j, listOfLocationsCopy.get(j - 1));
                    listOfLocationsCopy.remove(j - 1);
                    listOfLocationsCopy.add(j - 1, swap);

                    j -= 1;
                }
            }
        }

        return listOfLocationsCopy;
    }

    /**
     * Check to see if the ending hour time of a location goes out of bounds
     * of our current day. If true, it exists and therefore doesn't 
     * go out of bounds. False otherwise.
     */
    private boolean findEndHour(String endHour) {

        for (int i = 0; i < listOfHourlyForecasts.size(); i++) {

            String[] forecast = listOfHourlyForecasts.get(i);
            Calendar cal = Calendar.getInstance();
            String dayOfMonth = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
            if (endHour.equals(forecast[3]) && dayOfMonth.equals(forecast[2])) {
                return true;
            }
        }
        return false;
    }

    /**
     * executes the method for the button pressed
     */
    @Override
    public void onClick(View view) {

        if (view.getId() == addButton.getId()) {
            // We add a location with a start time and end time to the locationData.
            
            String[] locationData = new String[5];

            locationData[0] = locationString;
            locationData[1] = String.valueOf(startTimeSpinner.getCurrentHour());
            locationData[2] = String.format("%02d", startTimeSpinner.getCurrentMinute());
            locationData[3] = String.valueOf(endTimeSpinner.getCurrentHour());
            locationData[4] = String.format("%02d", endTimeSpinner.getCurrentMinute());

            listOfLocations.add(locationData);

            // We update the textview that displays on the screen to show the newly added 
            // location with the textview in chronological order.
            locationText.setText("");

            ArrayList<String[]> listOfLocationsInOrder = listOfLocationsChronoOrder(listOfLocations);

            for (int i = 0; i < listOfLocationsInOrder.size(); i++) {
                String[] locationAndTime = listOfLocationsInOrder.get(i);

                locationText.setText(locationText.getText().toString() + locationAndTime[0] + " / " +
                        locationAndTime[1] + ":" + locationAndTime[2] + "-" + locationAndTime[3] + ":" +
                        locationAndTime[4] + "\n");
            }
        }
        if (view.getId() == undoButton.getId()) {

            // if location list is not empty, we remove the last added location from the list and update the textview 
            if (!listOfLocations.isEmpty()) {
                listOfLocations.remove(listOfLocations.size() - 1);

                locationText.setText("");

                ArrayList<String[]> listOfLocationsInOrder = listOfLocationsChronoOrder(listOfLocations);

                for (int i = 0; i < listOfLocationsInOrder.size(); i++) {
                    String[] locationAndTime = listOfLocationsInOrder.get(i);

                    locationText.setText(locationText.getText().toString() + locationAndTime[0] + " / " +
                            locationAndTime[1] + ":" + locationAndTime[2] + "-" + locationAndTime[3] + ":" +
                            locationAndTime[4] + "\n");
                }
            }
        }
        if (view.getId() == clearButton.getId()) {

            // empty the list and update the textview
            if (!listOfLocations.isEmpty()) {
                listOfLocations = new ArrayList<>();

                locationText.setText("");
            }
        }
        if (view.getId() == showMap.getId()) {

            // list for in case any times go out of range of the current day
            ArrayList<String[]> outOfRangeLocations = new ArrayList<>();

            // checks all location's ending times to see if they go out of bounds 
            // of current day
            boolean exists = true;
            for (int i = 0; i < listOfLocations.size(); i++) {
                String[] location = listOfLocations.get(i);
                String endHour = location[3];

                boolean containsHour = findEndHour(endHour);

                if (!containsHour) {
                    exists = false;
                    outOfRangeLocations.add(location);
                }
            }

            // if exists is true, all locations are within the bounds of the current day
            // so go to map activity
            // if exists is false, we run an error message listing the locations that are out
            // of bounds of the current day
            if (exists) {
                Intent intent = new Intent(this, MapsActivity.class);

                intent.putExtra(MainActivity.ALL_HOURLY_FORECASTS, listOfHourlyForecasts);
                intent.putExtra(SCHEDULE, listOfLocationsChronoOrder(listOfLocations));

                startActivity(intent);
            }
            else {
                Toast.makeText(this, "At least one location's time has passed for today", Toast.LENGTH_SHORT).show();

                for (int i = 0; i < outOfRangeLocations.size(); i++) {

                    String[] location = outOfRangeLocations.get(i);
                    Toast.makeText(this, location[0] + " / " + location[1] + ":" + location[2] + "-" +
                            location[3] + ":" + location[4], Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
