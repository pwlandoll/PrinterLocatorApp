package edu.jcu.plandoll16.printerlocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DisplayPrinterActivity extends AppCompatActivity {
    private TextView printerNameTextView, printerStatusCodeTextView, printerDesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_printer);

        printerNameTextView = (TextView)findViewById(R.id.printerNameTextView);
        printerStatusCodeTextView = (TextView)findViewById(R.id.printerStatusCodeTextView);
        printerDesTextView = (TextView)findViewById(R.id.printerDesTextView);

        Intent mIntent = this.getIntent();
        try {
            printerNameTextView.setText(mIntent.getStringExtra("edu.jcu.plandoll16.PrinterLocator.printerName"));
            printerStatusCodeTextView.setText(mIntent.getStringExtra("edu.jcu.plandoll16.PrinterLocator.printerStatusCode"));
            printerDesTextView.setText(mIntent.getStringExtra("edu.jcu.plandoll16.PrinterLocator.printerDes"));
        } catch (Exception ex) {
            Log.e("PROBLEM", ex.getMessage());
        }
    }
}
