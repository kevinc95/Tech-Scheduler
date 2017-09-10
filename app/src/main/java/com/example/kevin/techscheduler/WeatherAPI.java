package com.example.kevin.techscheduler;

import android.content.ContentValues;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

/**
 * Class for retrieving weather data from JSON file
 */
public class WeatherAPI implements Response.Listener<String>, Response.ErrorListener {

    String url = "http://api.wunderground.com/api/";
    String key = "7243bb3173e93a80";

    MainActivity mainActivity;

    RequestQueue queue;

    /**
     * Initialize instance of WeatherAPI.
     */
    public WeatherAPI(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        queue = Volley.newRequestQueue(mainActivity);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
    }

    /**
     * When data is returned from API call, the data is parsed into a
     * LinkedList of ContentValues, and saved by mainActivity into the database.
     */
    @Override
    public void onResponse(String response) {
        try {

            LinkedList<ContentValues> forecastsEveryHour = new LinkedList<>();

            JSONObject jsonObject = new JSONObject(response);
            JSONArray hourlyForecastArray = jsonObject.getJSONArray("hourly_forecast");

            for (int i = 0; i < hourlyForecastArray.length(); i++) {
                ContentValues contentValues = new ContentValues();

                JSONObject forecastObject = (JSONObject) hourlyForecastArray.get(i);

                String fcttimeStr = forecastObject.getString("FCTTIME");
                JSONObject fcttimeObject = new JSONObject(fcttimeStr);
                int year = fcttimeObject.getInt("year");
                int month = fcttimeObject.getInt("mon");
                int day = fcttimeObject.getInt("mday");
                int hour = fcttimeObject.getInt("hour");

                String tempStr = forecastObject.getString("temp");
                JSONObject tempObject = new JSONObject(tempStr);
                int degreesF = tempObject.getInt("english");

                String condition = forecastObject.getString("condition");

                String iconUrl = forecastObject.getString("icon_url");

                int humidity = forecastObject.getInt("humidity");

                String feelsLikeStr = forecastObject.getString("feelslike");
                JSONObject feelsLikeObject = new JSONObject(feelsLikeStr);
                int feelsLikeF = feelsLikeObject.getInt("english");

                contentValues.put(DBOpenHelper.COLUMN_NAME_YEAR, year);
                contentValues.put(DBOpenHelper.COLUMN_NAME_MONTH, month);
                contentValues.put(DBOpenHelper.COLUMN_NAME_DAY, day);
                contentValues.put(DBOpenHelper.COLUMN_NAME_HOUR, hour);
                contentValues.put(DBOpenHelper.COLUMN_NAME_TEMP_F, degreesF);
                contentValues.put(DBOpenHelper.COLUMN_NAME_FEELS_LIKE, feelsLikeF);
                contentValues.put(DBOpenHelper.COLUMN_NAME_HUMIDITY, humidity);
                contentValues.put(DBOpenHelper.COLUMN_NAME_CONDITION, condition);
                contentValues.put(DBOpenHelper.COLUMN_NAME_ICON_URL, iconUrl);

                forecastsEveryHour.add(contentValues);
            }

            mainActivity.saveInfo(forecastsEveryHour);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Request if made to the link
     */
    public void getInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                getRequest(), this, this);
        queue.add(stringRequest);
    }

    /**
     * URL link we are getting data from.
     * "http://api.wunderground.com/api/7243bb3173e93a80/hourly/q/VA/Blacksburg.json"
     */
    public String getRequest() {
        return url + key + "/hourly/q/VA/Blacksburg.json";
    }
}
