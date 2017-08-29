package com.example.kevin.techscheduler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {

    TextView temp;
    TextView feelsLike;
    TextView hum;
    TextView condition;
    ImageView conditionPic;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Intent intent = getIntent();

        String[] forecast = intent.getStringArrayExtra(MainActivity.FORECAST_OF_THE_HOUR);

        temp = (TextView) findViewById(R.id.temp);
        feelsLike = (TextView) findViewById(R.id.feelsLike);
        hum = (TextView) findViewById(R.id.hum);
        condition = (TextView) findViewById(R.id.condition);
        conditionPic = (ImageView) findViewById(R.id.conditionPic);
        date = (TextView) findViewById(R.id.date);

        temp.setText("Temp: " + forecast[4] + " degrees F");
        feelsLike.setText("Feels like: " + forecast[5] + " degrees F");
        hum.setText("Humidity: " + forecast[6] + "%");
        condition.setText("Condition: " + forecast[7]);
        date.setText(forecast[1] + "/" + forecast[2] + "/" + forecast[0]);

        DownloaderAsyncTask downloaderAsyncTask = new DownloaderAsyncTask(conditionPic);
        downloaderAsyncTask.execute(forecast[8]);
    }

    private class DownloaderAsyncTask extends AsyncTask<String, String, Bitmap> {

        ImageView image;

        public DownloaderAsyncTask(ImageView image) {
            this.image = image;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap image = null;
            try {
                InputStream in = new URL(url).openStream();
                image = BitmapFactory.decodeStream(in);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }

            return image;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            image.setImageBitmap(bitmap);
        }
    }
}
