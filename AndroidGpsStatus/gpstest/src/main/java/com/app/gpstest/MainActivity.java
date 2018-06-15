package com.app.gpstest;



import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private double latitude = 0.0;
    private double longitude = 0.0;
    private TextView info;
    private LocationManager locationManager;
    private final int CODE_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.tv);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            List<String> permissionsNeeded = new ArrayList<String>();
            permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[permissionsNeeded.size()]), CODE_LOCATION_PERMISSION);
            return;
        }
        openlm();
    }

    protected void onPause()
    {
        super.onPause();
        if(locationManager != null){
            locationManager.removeUpdates(locationListener);
        }
    }

    private void openlm()
    {

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            toggleGPS();
            getLocation();
            //gps已打开
        } else {
            toggleGPS();
            new Handler() {}.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getLocation();
                }
            }, 2000);

        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_LOCATION_PERMISSION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openlm();
            }
        }
    }

    private void toggleGPS() {
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
        gpsIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(this, 0, gpsIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location1 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location1 != null) {
                latitude = location1.getLatitude(); // 经度
                longitude = location1.getLongitude(); // 纬度
            }
        }
    }

    private void getLocation() {
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        } else {

//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                    6000, 1, locationListener);

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000, 0, locationListener);
        }
        info.setText("纬度：" + latitude + "\n" + "经度：" + longitude);
    }

    LocationListener locationListener = new LocationListener() {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, provider);
        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, provider);
        }

        // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                Log.e("Map", "Location changed : Lat: " + location.getLatitude() + " Lng: " + location.getLongitude());
                latitude = location.getLatitude(); // 经度
                longitude = location.getLongitude(); // 纬度
                info.setText("纬度：" + latitude + "\n" + "经度：" + longitude);
            }
        }
    };



    // 打开和关闭gps第二种方法
    private void openGPSSettings() {
        //获取GPS现在的状态（打开或是关闭状态）
        boolean gpsEnabled = Settings.Secure.isLocationProviderEnabled(getContentResolver(), LocationManager.GPS_PROVIDER);
        if (gpsEnabled) {
            //关闭GPS
            Settings.Secure.setLocationProviderEnabled(getContentResolver(), LocationManager.GPS_PROVIDER, false);
        } else {
            //打开GPS
            Settings.Secure.setLocationProviderEnabled(getContentResolver(), LocationManager.GPS_PROVIDER, true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
