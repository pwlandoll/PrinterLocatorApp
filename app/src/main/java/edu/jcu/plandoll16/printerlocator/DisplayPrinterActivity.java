package edu.jcu.plandoll16.printerlocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DisplayPrinterActivity extends AppCompatActivity {
    private TextView printerNameTextView, printerDesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_printer);

        printerNameTextView = (TextView)findViewById(R.id.printerNameTextView);
        printerDesTextView = (TextView)findViewById(R.id.printerDesTextView);

        Intent mIntent = this.getIntent();
        try {
            printerNameTextView.setText(mIntent.getStringExtra("edu.jcu.plandoll16.PrinterLocator.printerName"));
            printerDesTextView.setText(mIntent.getStringExtra("edu.jcu.plandoll16.PrinterLocator.printerDes"));
        } catch (Exception ex) {
            Log.e("PROBLEM", ex.getMessage());
        }
    }
}
