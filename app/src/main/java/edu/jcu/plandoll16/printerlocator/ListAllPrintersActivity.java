package edu.jcu.plandoll16.printerlocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Activity for listing all available printers, with optional filter given by AllPrinterFilterActivity.
 *
 * @author Peter Landoll
 * @version 0.1
 * @since 2016-4-27
 */
public class ListAllPrintersActivity extends AppCompatActivity {
    LinearLayout printerListLinearLayout;
    PrinterHelper mPrinterHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_printers);

        printerListLinearLayout = (LinearLayout)findViewById(R.id.printerListLinearLayout);
        mPrinterHelper = new PrinterHelper(getApplicationContext());

        LinearLayout mLayout;
        ArrayList<Printer> allPrinters = mPrinterHelper.getAvailablePrinters();
        ArrayList<Printer> printersByBuilding = new ArrayList<>();
        String building = this.getIntent().getStringExtra("edu.jcu.plandoll16.PrinterLocator.building");
        if (building != null && !building.equals("all") && !building.equals("")) {
            for (Printer p : allPrinters) {
                if (p.getBuilding().equals(building)) {
                    printersByBuilding.add(p);
                }
            }
        } else {
            printersByBuilding = allPrinters;
        }
        for (Printer p : printersByBuilding) {
            // mPrinter must be made final so the onClick method in the listener can access it
            final Printer mPrinter = p;
            mLayout = mPrinter.getPrinterLayout(getApplicationContext());
            ((TextView)mLayout.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
            mLayout.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(getApplicationContext(), DisplayPrinterActivity.class);
                    mIntent.putExtra("edu.jcu.plandoll16.PrinterLocator.latitude", mPrinter.getLocationLatitude());
                    mIntent.putExtra("edu.jcu.plandoll16.PrinterLocator.longitude", mPrinter.getLocationLongitude());
                    mIntent.putExtra("edu.jcu.plandoll16.PrinterLocator.printerName", mPrinter.getName());
                    mIntent.putExtra("edu.jcu.plandoll16.PrinterLocator.printerDescription", mPrinter.getDescription());
                    startActivity(mIntent);
                }
            });
            printerListLinearLayout.addView(mLayout);
        }
    }

}
