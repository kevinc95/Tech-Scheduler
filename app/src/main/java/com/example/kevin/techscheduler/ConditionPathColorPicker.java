package com.example.kevin.techscheduler;

import android.graphics.Color;

/**
 * A class for deciding the color of a walking path based off the weather condition.
 */
public class ConditionPathColorPicker {

    String condition;

    /**
     * Initialize an instance of ConditionPathColorPicker.
     */
    public ConditionPathColorPicker(String condition) {
        this.condition = condition;
    }

    /**
     * Determines the color based off condition string.
     */
    public int getPathColor() {

        int color = Color.BLACK;

        if (condition.equals("Light Drizzle") || condition.equals("Heavy Drizzle") ||
                condition.equals("Drizzle") || condition.equals("Light Rain") ||
                condition.equals("Heavy Rain") || condition.equals("Rain") ||
                condition.equals("Light Rain Mist") || condition.equals("Heavy Rain Mist") ||
                condition.equals("Rain Mist") || condition.equals("Light Rain Showers") ||
                condition.equals("Heavy Rain Showers") || condition.equals("Rain Showers") ||
                condition.equals("Light Freezing Drizzle") || condition.equals("Heavy Freezing Drizzle") ||
                condition.equals("Freezing Drizzle") || condition.equals("Light Freezing Rain") ||
                condition.equals("Heavy Freezing Rain") || condition.equals("Freezing Rain")) {

            color = Color.CYAN;
        }
        if (/*snow/hail*/condition.equals("Snow") || condition.equals("Light Snow") ||
                condition.equals("Heavy Snow") || condition.equals("Snow Grains") ||
                condition.equals("Light Snow Grains") || condition.equals("Heavy Snow Grains") ||
                condition.equals("Ice Crystals") || condition.equals("Light Ice Crystals") ||
                condition.equals("Heavy Ice Crystals") || condition.equals("Ice Pellets") ||
                condition.equals("Light Ice Pellets") || condition.equals("Heavy Ice Pellets") ||
                condition.equals("Hail") || condition.equals("Light Hail") ||
                condition.equals("Heavy Hail") || condition.equals("Low Drifting Snow") ||
                condition.equals("Light Low Drifting Snow") || condition.equals("Heavy Low Drifting Snow") ||
                condition.equals("Blowing Snow") || condition.equals("Light Blowing Snow") ||
                condition.equals("Heavy Blowing Snow") || condition.equals("Snow Showers") ||
                condition.equals("Light Snow Showers") || condition.equals("Heavy Snow Showers") ||
                condition.equals("Snow Blowing Snow Mist") || condition.equals("Light Snow Blowing Snow Mist") ||
                condition.equals("Heavy Snow Blowing Snow Mist") || condition.equals("Ice Pellet Showers") ||
                condition.equals("Light Ice Pellet Showers") || condition.equals("Heavy Ice Pellet Showers") ||
                condition.equals("Hail Showers") || condition.equals("Light Hail Showers") ||
                condition.equals("Heavy Hail Showers") || condition.equals("Small Hail Showers") ||
                condition.equals("Light Small Hail Showers") || condition.equals("Heavy Small Hail Showers") ||
                condition.equals("Small Hail")) {

            color = Color.BLUE;
        }
        if (condition.equals("Thunderstorm") || condition.equals("Light Thunderstorm") ||
                condition.equals("Heavy Thunderstorm") || condition.equals("Thunderstorms and Rain") ||
                condition.equals("Light Thunderstorms and Rain") || condition.equals("Heavy Thunderstorms and Rain") ||
                condition.equals("Thunderstorms and Snow") || condition.equals("Light Thunderstorms and Snow") ||
                condition.equals("Heavy Thunderstorms and Snow") || condition.equals("Thunderstorms and Ice Pellets") ||
                condition.equals("Light Thunderstorms and Ice Pellets") || condition.equals("Heavy Thunderstorms and Ice Pellets") ||
                condition.equals("Thunderstorms with Hail") || condition.equals("Light Thunderstorms with Hail") ||
                condition.equals("Heavy Thunderstorms with Hail") || condition.equals("Thunderstorms with Small Hail") ||
                condition.equals("Light Thunderstorms with Small Hail") || condition.equals("Heavy Thunderstorms with Small Hail")) {

            color = Color.DKGRAY;
        }
        if (condition.equals("Overcast") || condition.equals("Partly Cloudy") ||
                condition.equals("Mostly Cloudy") || condition.equals("Scattered Clouds")) {

            color = Color.GRAY;
        }
        if (condition.equals("Mist") || condition.equals("Light Mist") ||
                condition.equals("Heavy Mist") || condition.equals("Fog") ||
                condition.equals("Light Fog") || condition.equals("Heavy Fog") ||
                condition.equals("Fog Patches") || condition.equals("Light Fog Patches") ||
                condition.equals("Heavy Fog Patches") || condition.equals("Freezing Fog") ||
                condition.equals("Light Freezing Fog") || condition.equals("Heavy Freezing Fog") ||
                condition.equals("Patches of Fog") || condition.equals("Shallow Fog") ||
                condition.equals("Partial Fog")) {

            color = Color.LTGRAY;
        }
        if (condition.equals("Clear")) {

            color = Color.GREEN;
        }

        return color;
    }
}
