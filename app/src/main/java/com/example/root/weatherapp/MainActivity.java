package com.example.root.weatherapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static String cityName;
    @SuppressLint("StaticFieldLeak")
    private static TextView lastUpdateTextView, cityTextView, regionTextView, countryTextView, localTimeTextView,tempTextView, descriptionTextView;
    private static String text, temp_c, last_update, localtime,country, region, name;
    private static Drawable drawable;
    @SuppressLint("StaticFieldLeak")
    private static ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lastUpdateTextView = findViewById(R.id.lastUpdate);
        localTimeTextView = findViewById(R.id.localTimeTextView);
        cityTextView = findViewById(R.id.cityTextView);
        regionTextView = findViewById(R.id.regionTextView);
        countryTextView = findViewById(R.id.countryTextView);
        tempTextView = findViewById(R.id.tempTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        imageView = findViewById(R.id.imageView);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            cityName = bundle.getString("name");
        }
        new MyTask(this).execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class MyTask extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        private MyTask(MainActivity activity) {
            dialog = new ProgressDialog(activity);
        }


        @Override
        protected void onPreExecute() {
            dialog.setMessage("please wait.");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {

            try {
                URL url = new URL("https://api.apixu.com/v1/current.json?key=941ea66bb899433c82e52436183004&q=" + cityName);
                InputStream stream = url.openStream();
                String response = convertStreamToString(stream);
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObject1 = jsonObject.getJSONObject("location");
                JSONObject jsonObject2 = jsonObject.getJSONObject("current");
                name = jsonObject1.getString("name");
                region = jsonObject1.getString("region");
                country = jsonObject1.getString("country");
                localtime = jsonObject1.getString("localtime");
                last_update = jsonObject2.getString("last_updated");
                temp_c = jsonObject2.getString("temp_c");
                JSONObject jsonObject3 = jsonObject2.getJSONObject("condition");
                text = jsonObject3.getString("text");
                String path = jsonObject3.getString("icon");
                URL u = new URL("http:" + path);
                drawable = Drawable.createFromStream((InputStream) u.getContent(), "src");

            }  catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String convertStreamToString(InputStream stream) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            lastUpdateTextView.setText(last_update);
            localTimeTextView.setText(localtime);
            cityTextView.setText(name);
            regionTextView.setText(region);
            countryTextView.setText(country);
            tempTextView.setText(temp_c);
            descriptionTextView.setText(text);
            imageView.setImageDrawable(drawable);
        }
    }
}
