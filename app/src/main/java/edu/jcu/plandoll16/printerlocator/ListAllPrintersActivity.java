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
        mPrinterHelper = new PrinterHelper();

        LinearLayout mLayout;
        ArrayList<Printer> allPrinters = mPrinterHelper.getPrinterArrayList();
        for (Printer p : allPrinters) {
            // mPrinter must be final so the onClick method in the listener can access it
            final Printer mPrinter = p;
            mLayout = mPrinter.getPrinterLayout(getBaseContext());
            ((TextView)mLayout.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
            mLayout.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(getApplicationContext(), DisplayPrinterActivity.class);
                    mIntent.putExtra("edu.jcu.plandoll16.PrinterLocator.printerName", mPrinter.getName());
                    mIntent.putExtra("edu.jcu.plandoll16.PrinterLocator.printerStatusCode", mPrinter.getStatusCode().toString());
                    mIntent.putExtra("edu.jcu.plandoll16.PrinterLocator.printerDescription", mPrinter.getDescription());
                    startActivity(mIntent);
                }
            });
            printerListLinearLayout.addView(mLayout);
        }
    }

}
