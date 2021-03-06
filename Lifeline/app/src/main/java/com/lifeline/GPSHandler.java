package com.lifeline;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shantanubobhate on 12/3/16.
 */

public class GPSHandler {

    private static final long MIN_TIME = 1000;
    private static final float MIN_DISTANCE = 1f;
    private static final int MAX_RESULTS = 1;

    private Context mContext;
    private LocationManager mLocationManager;
    private String currentAddress;

    private List<Point> mPoints;

    public GPSHandler(Context context) {
        mContext = context;
        mLocationManager = (LocationManager) context.getSystemService(mContext.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);

        mPoints = new ArrayList<>();
    }

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            getHospital(location);

            /*
             * Code for calculation Speed

            Point mPoint = new Point(location.getLongitude(), location.getLatitude(), System.currentTimeMillis());
            mPoints.add(mPoint);
            calcSpeed();

             */
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) { }

        @Override
        public void onProviderEnabled(String s) { }

        @Override
        public void onProviderDisabled(String s) { }
    };

    private void getHospital(Location location) {
        Geocoder mGeocoder = new Geocoder(mContext);    // Object to get address using coordinates
        List<Address> addresses = null;                 // To hold the location and hospital addresses

        currentAddress = "";
        if (addresses.size() > 0) {
            currentAddress = addresses.get(0).getAddressLine(0);
        }
        try {
            addresses = mGeocoder.getFromLocation(location.getLatitude(), location.getLongitude(), MAX_RESULTS);
        } catch (IOException e) {
        }
    }

    /*
     * Function for calculating speed
     *
    private double calcSpeed() {
        if (mPoints.size() < 2) {
            return;
        }

        if (mPoints.size() > 5) {
            mPoints.remove(0);
        }

        long startTime = mPoints.get(0).getLtime();
        long endTime = mPoints.get(mPoints.size() - 1).getLtime();

        double sumDis = 0;//保存时间段内总走过的距离，单位 km

        Point startPoint = mPoints.get(0);//起点
        for (int i = 1; i < mPoints.size(); i++) {
            //遍历计算每相隔两点之间的距离并加起来
            sumDis += Distance.GetDistance(startPoint, mPoints.get(i));
            startPoint = mPoints.get(i);
        }

        //计算速度,此时的单位为 km/h
        double speed = (sumDis * 1000 / ((endTime - startTime) / 1000.0)) * 3.6;

        return speed;
    }
    */
}
