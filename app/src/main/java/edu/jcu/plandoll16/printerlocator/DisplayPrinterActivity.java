package edu.jcu.plandoll16.printerlocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity for displaying information on an individual printer.
 *
 * @author Peter Landoll
 * @version 1.0
 * @since 2016-5-1
 */
public class DisplayPrinterActivity extends AppCompatActivity {
    private Button mapButton;
    private double latitude, longitude;
    private String name;
    private TextView printerNameTextView, printerDesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_printer);

        mapButton = (Button)findViewById(R.id.mapButton);
        printerNameTextView = (TextView)findViewById(R.id.printerNameTextView);
        printerDesTextView = (TextView)findViewById(R.id.printerDesTextView);

        Intent mIntent = this.getIntent();
        try {
            // name, latitude, and longitude necessary for map activity intent later
            latitude = mIntent.getDoubleExtra("edu.jcu.plandoll16.PrinterLocator.latitude", 0);
            longitude = mIntent.getDoubleExtra("edu.jcu.plandoll16.PrinterLocator.longitude", 0);
            name = mIntent.getStringExtra("edu.jcu.plandoll16.PrinterLocator.printerName");
            printerNameTextView.setText(name);
            printerDesTextView.setText(mIntent.getStringExtra("edu.jcu.plandoll16.PrinterLocator.printerDescription"));
        } catch (Exception ex) {
            Log.e("DisplayPrinterActivity", ex.getMessage());
        }

        // name, latitude, and longitude are necessary for putting a marker on the map
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), DisplayPrinterOnMapActivity.class);
                mIntent.putExtra("edu.jcu.plandoll16.PrinterLocator.name", name);
                mIntent.putExtra("edu.jcu.plandoll16.PrinterLocator.latitude", latitude);
                mIntent.putExtra("edu.jcu.plandoll16.PrinterLocator.longitude", longitude);
                startActivity(mIntent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
