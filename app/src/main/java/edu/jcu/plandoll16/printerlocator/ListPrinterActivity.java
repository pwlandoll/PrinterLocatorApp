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
    private ArrayList<Printer> printerArrayList;
    private ArrayList<String[]> fileContents;
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_printer);

        printerArrayList = new ArrayList<>();
        fileContents = new ArrayList<>();
        test = (TextView)findViewById(R.id.testTextView);
        fetchPrinterList();
    }

    /**
     * Creates a Thread on a getCSVRunnable object.
     *
     * Android requires that network operations (e.g. fetching a CSV from a URL) be done on a
     * separate thread
     */
    private void fetchPrinterList() {
        try {
            Thread getCSV = new Thread(new getCSVRunnable());
            getCSV.start();
        } catch (Exception ex) {
            Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Handles output text from the CSV file to store the contents properly as an ArrayList.
     *
     * @param contents  the contents of the CSV file to be passed in from fetchPrinterLists's
     *                  getCSVRunnable Thread
     */
    private void handleCSVString(String contents) {
        String text = "";
        String[] csvLines;
        // Split on Windows newlines, as the CSV is generated by a Windows machine
        csvLines = contents.split("\r\n");
        // Split each line on commas, and add it to the fileContents ArrayList<String[]>
        for (String line : csvLines) {
            fileContents.add(line.split(","));
        }
        // We don't need the header row of the CSV, so remove it
        fileContents.remove(0);
        for (String[] printerInfoArray : fileContents) {
            // TODO: implement checks against printer list
            // if the printer with name printerInfoArray[0] is in the list populated by the
            //  database, then update that printer with status code printerInfoArray[2]?
            //printerArrayList.add(new Printer(printerInfoArray[0]));
        }
    }

    /**
     * Pulls records from the database and adds them to the list of printers.
     */
    private void populatePrinterList() {

    }

    /**
     * Manages the network operations behind fetching the CSV file.
     */
    private class getCSVRunnable implements Runnable {
        @Override
        public void run() {
            Looper.prepare();
            try {
                // Open a scanner that pulls the CSV and opens with UTF-16 character encodings
                Scanner mScanner = new Scanner(new URL("http://stuweb.jcu.edu/printerstatus2.csv").openStream(), "UTF-16");
                // Get the text
                String out = mScanner.useDelimiter("\\A").next();
                mScanner.close();
                // Passes the output string to another method to handle the text
                handleCSVString(out);
            } catch (Exception ex) {
                Log.e("PROBLEM", ex.getMessage());
                Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
