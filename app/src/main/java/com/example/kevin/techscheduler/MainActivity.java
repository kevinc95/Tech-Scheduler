package com.example.kevin.techscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/**
 * The activity of the home screen
 **/
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ALL_HOURLY_FORECASTS = "allHourlyForecasts";
    public static final String FORECAST_OF_THE_HOUR = "forecastOfTheHour";

    Button scheduleMyDay;
    Button weather;

    DatabaseManager databaseManager;
    WeatherAPI weatherAPI;

    /**
     * On create, we initialize listeners for the SCHEDULE MY DAY and WEATHER button.
     * We also initialize our database manager for the SQLiteDatabase to hold weather data.
     * We also initialize our weatherAPI so we can retrieve weather data. 
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scheduleMyDay = (Button) findViewById(R.id.scheduleMyDay);
        weather = (Button) findViewById(R.id.weather);

        scheduleMyDay.setOnClickListener(this);
        weather.setOnClickListener(this);

        databaseManager = new DatabaseManager(this);

        weatherAPI = new WeatherAPI(this);
    }

    /**
     * Everytime activity is started, open the database, clear the database of any previous
     * weather data, and make weather API call to retrieve new weather data
     **/
    @Override
    protected void onStart() {
        super.onStart();
        databaseManager.open();
        databaseManager.deleteAll();
        weatherAPI.getInfo();
    }

    /**
     * Everytime activity stops, close the database.
     **/
    @Override
    protected void onStop() {
        super.onStop();
        databaseManager.close();
    }

    /**
     * Saves newly retrieved weather data into database
     **/
    public void saveInfo(LinkedList<ContentValues> forecasts) {

        for (int i = 0; i < forecasts.size(); i++) {
            databaseManager.insertForecastInfo(forecasts.get(i));
        }
    }

    /**
     * If a button is clicked, this method checks to see if application
     * has access to the internet because both buttons in this activity
     * lead to other activities that require the most up to date weather data.
     * If there is no internet connection, that means we could update the 
     * the weather data. If there is internet, we move to the activity 
     * that corresponds with the button we pressed.
     **/
    @Override
    public void onClick(View view) {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

            ArrayList<String[]> forecasts = databaseManager.getAllRecords();

            if (forecasts.size() == 0) {
                Toast.makeText(this, "Still Downloading Info...", Toast.LENGTH_SHORT).show();
            }
            else {
                if (view.getId() == scheduleMyDay.getId()) {
                    Intent intent = new Intent(this, ScheduleMyDay.class);
                    intent.putExtra(ALL_HOURLY_FORECASTS, forecasts);
                    startActivity(intent);
                }
                if (view.getId() == weather.getId()) {
                    Intent intent = new Intent(this, WeatherActivity.class);
                    intent.putExtra(FORECAST_OF_THE_HOUR, forecasts.get(0));
                    startActivity(intent);
                }
            }
        }
        else {
            Toast.makeText(this, "Please first find internet connection, and then restart the app", Toast.LENGTH_SHORT).show();
        }
    }
}
