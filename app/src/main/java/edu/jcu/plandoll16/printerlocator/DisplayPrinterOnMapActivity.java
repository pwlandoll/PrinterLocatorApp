package edu.jcu.plandoll16.printerlocator;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Maps Activity for showing the location of a printer, taken from DisplayPrinterActivity.
 *
 * @author Peter Landoll
 * @version 1.0
 * @since 2016-5-7
 */
public class DisplayPrinterOnMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_printer_on_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * When map is created, simply puts a pin in the map at the location of the printer.
     *
     * @param googleMap default parameter for manipulating maps
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double mLat = 0, mLon = 0;
        String name = "Printer";
        try {
            name = this.getIntent().getStringExtra("edu.jcu.plandoll16.PrinterLocator.name");
            mLat = this.getIntent().getDoubleExtra("edu.jcu.plandoll16.PrinterLocator.latitude", 0);
            mLon = this.getIntent().getDoubleExtra("edu.jcu.plandoll16.PrinterLocator.longitude", 0);
        } catch (Exception ex) {
            Log.d("onMapReady exception", ex.getMessage());
        }
        LatLng printerLocation = new LatLng(mLat, mLon);
        mMap.addMarker(new MarkerOptions().position(printerLocation).title(name));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(printerLocation));
    }
}
