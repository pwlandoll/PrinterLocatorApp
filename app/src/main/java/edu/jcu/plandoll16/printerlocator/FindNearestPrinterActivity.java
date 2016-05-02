package edu.jcu.plandoll16.printerlocator;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author Peter Landoll
 * @version 0.1
 * @since 2016-4-30
 */
public class FindNearestPrinterActivity extends AppCompatActivity implements LocationListener {
    LinearLayout printerLinearLayout;
    LocationManager mLocationManager;
    PrinterHelper mPrinterHelper;
    TextView waitTextView;
    Toast securityErrorToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_nearest_printer);

        printerLinearLayout = (LinearLayout)findViewById(R.id.printerLinearLayout);
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
     * @return the Euclidean distance between location 1 and location 2 (converted to feet)
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
        // Change top text since location is found, then proceed to calculate distances
        waitTextView.setText(R.string.found);
        // We only want available printers here, not all printers
        // TODO: Fix issue where this line executes before mPrinterHelper can complete the handleCSVString method
        ArrayList<Printer> availablePrinters = mPrinterHelper.getAvailablePrinters();
        for (Printer p : availablePrinters) {
            p.setDistance(distance(location.getLatitude(), location.getLongitude(), p.getLocationLatitude(), p.getLocationLongitude()));
        }
        int smallestIndex = 0;
        if (availablePrinters.size() == 0) {
            // In this case, PrinterHelper failed to make a list of any available printers
            // Serves as error handling for the .join() in PrinterHelper's fetchPrinterList
            waitTextView.setText(R.string.none);
            return;
        }
        double smallestDistance = availablePrinters.get(0).getDistance();
        for (int i = 0; i < availablePrinters.size(); i++) {
            if (availablePrinters.get(i).getDistance() < smallestDistance) {
                smallestIndex = i;
            }
        }
        // Closest printer is availablePrinters.get(smallestIndex)
        final Printer closestPrinter = availablePrinters.get(smallestIndex);
        LinearLayout mLayout = closestPrinter.getPrinterLayout(getBaseContext());
        ((TextView)mLayout.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
        mLayout.getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), DisplayPrinterActivity.class);
                mIntent.putExtra("edu.jcu.plandoll16.PrinterLocator.printerName", closestPrinter.getName());
                // TODO: add status code
                mIntent.putExtra("edu.jcu.plandoll16.PrinterLocator.printerDescription", closestPrinter.getDescription());
                startActivity(mIntent);
            }
        });
        printerLinearLayout.addView(mLayout);
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
