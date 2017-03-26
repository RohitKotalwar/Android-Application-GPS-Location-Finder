package com.example.rohitkotalwar.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;


public class MainActivity extends Activity {
    Button Location;

    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Location.setOnClickListener(new View.OnClickListener());
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        addButtonClickListner();


    }
    public void addButtonClickListner()
    {
        Button btnNavigator = (Button)findViewById(R.id.button);
        btnNavigator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gps = new GPSTracker(MainActivity.this);





                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    HttpClient Client = new DefaultHttpClient();
                    HttpClient httpClient = new DefaultHttpClient();
                    String URL ="https://mscloud.herokuapp.com/postlocation?Lat="+latitude+"&Lng="+longitude;
                    String SetServerString;
                    HttpGet httpget = new HttpGet(URL);
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    try {
                        SetServerString = Client.execute(httpget, responseHandler);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    /* HttpPost httpPost = new HttpPost(
                            "https://mscloud.herokuapp.com/postlocation");

                    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                    nameValuePair.add(new BasicNameValuePair("Lat", "user@gmail.com"));
                    nameValuePair.add(new BasicNameValuePair("Lng","longitude"
                            ));

                    try {
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                    } catch (UnsupportedEncodingException e) {
                        // writing error to Log
                        e.printStackTrace();
                    }

                    try {
                        HttpResponse response = httpClient.execute(httpPost);

                        // writing response to log
                        Log.d("Http Response:", response.toString());
                    } catch (ClientProtocolException e) {
                        // writing exception to log
                        e.printStackTrace();
                    } catch (IOException e) {
                        // writing exception to log
                        e.printStackTrace();

                    }
                    */
                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }


            }
        });
    }



}