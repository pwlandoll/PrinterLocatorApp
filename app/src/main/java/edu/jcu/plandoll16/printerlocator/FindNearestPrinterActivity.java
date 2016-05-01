package edu.jcu.plandoll16.printerlocator;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author Peter Landoll
 * @version 0.1
 * @since 2016-4-30
 */
public class FindNearestPrinterActivity extends AppCompatActivity implements LocationListener {
    LocationManager mLocationManager;
    PrinterHelper mPrinterHelper;
    TextView waitTextView;
    Toast securityErrorToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_nearest_printer);

        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        mPrinterHelper = new PrinterHelper();
        waitTextView = (TextView)findViewById(R.id.waitTextView);
        securityErrorToast = Toast.makeText(getBaseContext(), "App is unable to access location " +
                "services - please enable location services and try again.", Toast.LENGTH_LONG);

        // try/catch necessary for permission errors?
        // TODO: downgrade? target API
        try {
            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null && location.getTime() > System.currentTimeMillis() - 2 * 60 * 1000) {
                locationFound(location);
            } else {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
        } catch (SecurityException secEx) {
            securityErrorToast.show();
        }
    }

    /**
     * returns the distance (in feet) between two latitudes and longitudes.
     *
     * Parameters are given as two separate doubles per location to allow use between Printer object's
     * getLocationLat/Lon and any other method of determining locations
     *
     * @param location1Latitude latitude of location 1
     * @param location1Longitude longitude of location 1
     * @param location2Latitude latitude of location 2
     * @param location2longitude latitude of location 2
     * @return
     */
    private Double distance(double location1Latitude, double location1Longitude, double location2Latitude, double location2longitude) {
        double latD = Math.pow((364605 * (location1Latitude - location2Latitude)), 2);
        double lonD = Math.pow((258683 * (location1Longitude - location2longitude)), 2);
        return Math.sqrt(latD + lonD);
    }

    /**
     * calculates the nearest printer to a given location.
     *
     * @param location user's location used for calculating distances
     */
    private void locationFound(Location location) {
        waitTextView.setText(getResources().getString(R.string.found));
        ArrayList<Double> distances = new ArrayList<Double>();
        ArrayList<Printer> printerArrayList = mPrinterHelper.getPrinterArrayList();
        for (Printer p : printerArrayList) {
            distances.add(distance(location.getLatitude(), location.getLongitude(), p.getLocationLatitude(), p.getLocationLongitude()));
        }
        // TODO: Find smallest distance
    }

    /**
     * LocationListener's onLocationChanged method, overridden.
     *
     * After finding the location once, stop receiving updates from the LocationManager. After finding
     * the location once, we don't want to keep calculating the nearest printer. The user will be
     * able to refresh to enable locations once again.
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.v("Location Changed", location.getLatitude() + ", " + location.getLongitude());
            locationFound(location);
            try {
                // We only want to get the user's location once per launch of this activity.
                mLocationManager.removeUpdates(this);
            } catch (SecurityException secEx) {
                securityErrorToast.show();
            } catch (Exception ex) {
                Log.e("Other Location Error", ex.getMessage());
                Toast.makeText(getBaseContext(), "ERROR - try restarting the app.", Toast.LENGTH_LONG).show();
            }
        }
    }

    // The following methods are necessary, but empty, overridden methods for the LocationListener interface.
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}
}
