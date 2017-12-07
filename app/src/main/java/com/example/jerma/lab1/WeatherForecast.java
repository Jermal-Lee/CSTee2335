package com.example.jerma.lab1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class WeatherForecast extends Activity {

    ProgressBar progress;
    TextView min;
    TextView max;
    TextView current;
    TextView weatherIcon;
    //String iconName;
    ImageView iv;
    HTTPUtils utils = new HTTPUtils();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        progress = findViewById(R.id.progressBar);


        ForecastQuery myAsync = null;
        try {
            myAsync = new ForecastQuery();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        myAsync.execute();
        //myAsync.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
        //Log.i("debug", "HELLOO000000000000000000000O " + min.getText() );


    }


    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        String minTemp;
        String maxTemp;
        String curTemp;
        Bitmap image;
        String weathR;

        //URL url = new URL("http://openweathermap.org/img/w/13n.png");
        // URL url = new URL("http://openweathermap.org/img/w/" + weathR + ".png");

        public ForecastQuery() throws MalformedURLException {
        }

        @Override
        protected String doInBackground(String... args) {


            min = findViewById(R.id.minTemp);
            max = findViewById(R.id.maxTemp);
            current = findViewById(R.id.currentTemp);
            weatherIcon = findViewById(R.id.weatherIcon);
            iv = findViewById(R.id.weatherPic);

            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);

                XmlPullParser parser = factory.newPullParser();

                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);

                conn.setRequestMethod("GET");

                conn.setDoInput(true);
                // Starts the query
                conn.connect();

                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(conn.getInputStream(), "UTF-8");


                int eventType = -1;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        String locationValue = parser.getName();
                        if (locationValue.equals("temperature")) {
                            minTemp = parser.getAttributeValue(null, "min");
                            publishProgress(25);
                            maxTemp = parser.getAttributeValue(null, "max");
                            publishProgress(50);
                            curTemp = parser.getAttributeValue(null, "value");
                            publishProgress(75);


                        }
                        if (locationValue.equals("weather")) {
                            weathR = parser.getAttributeValue(null, "icon");
                            //open from local disk
                            if(fileExistance(weathR +".png")) {
                                FileInputStream fis = null;
                                try {
                                    fis = openFileInput(weathR + ".png");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                image = BitmapFactory.decodeStream(fis);
                                Log.i("File Exists", "THE FILE ALREADY EXISTS");
                            } else{
                                //dl image
                                image =  utils.getImage("http://openweathermap.org/img/w/" + weathR + ".png");
                                //save image to local
                                FileOutputStream outputStream = openFileOutput(weathR + ".png", Context.MODE_PRIVATE);
                                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                outputStream.flush();
                                outputStream.close();
                                Log.i("File Does Not Exist", "THE FILE DOESN'T EXIST YET");
                            }
                        }
                    }
                    eventType = parser.next();
                }
            } catch (MalformedURLException m) {

            } catch (IOException io) {

            } catch (XmlPullParserException ppe) {


            }
            return "";
        }

        public void onProgressUpdate(Integer... value) {
            progress.setVisibility(View.VISIBLE);
            progress.setProgress(value[0]);
        }

        public void onPostExecute(String s) {
            current.setText("Current Temp: " + curTemp);
            min.setText("Min Temp: " + minTemp);
            max.setText("Max Temp: " + maxTemp);
            weatherIcon.setText("Weather Icon: " + weathR);
            iv.setImageBitmap(image);
            progress.setVisibility(View.INVISIBLE);

        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }



    }
}



















/*
Using an AsyncTask
The AsyncTask class is used to manage background operations that will eventually post back to the UI thread.

To use an AsyncTask, you must create a subclass of the AsyncTask class and implement the appropriate callback methods:
1. onPreExecute(): This method runs on the UI thread before background processing begins.
2. doInBackground(): This method runs in the background and is where all the real work is done.
3. publishProgress(): This method, called from the doInBackground() method,
 periodically informs the UI thread about the background process progress.
 This method sends information to the UI process. Use this opportunity to send updated
 progress for a progress bar that the user can see.
4. onProgressUpdate(): This method runs on the UI thread whenever the doInBackground() method calls publishProgress().
This method receives information from the background process. Use this opportunity to update
 a ProgressBar control that the user can see.
5. onPostExecute(): This method runs on the UI thread once the background processing is completed.

public class TimeConsumingTask extends AsyncTask<Integer , Integer, Integer> {

    protected void onPreExecute () {

        super.onPreExecute();
        textMessage.setText("Async Task started");
        progressBar.setVisibility(View.VISIBLE);
    }

    protected void onPostExecute (Integer result) {
        super.onPostExecute(result);
        Log.i("tag---------",  result +"") ;

        if (result == 0) {
            textMessage.setText(" UI updated from Async");
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onProgressUpdate (Integer ... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]);
    }

    protected Integer doInBackground  (Integer ... params) {

        for (int i = 0 ; i< params.length ; i++) {

            SystemClock.sleep(1000);
            publishProgress(i);
        }
        return 0 ;
    }

}
*/
