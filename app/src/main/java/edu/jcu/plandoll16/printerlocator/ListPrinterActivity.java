package edu.jcu.plandoll16.printerlocator;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Peter Landoll
 * @version 0.1
 * @since 2016-4-27
 */
public class ListPrinterActivity extends AppCompatActivity {
    PrinterHelper mPrinterHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_printer);

        mPrinterHelper = new PrinterHelper();
    }

}
