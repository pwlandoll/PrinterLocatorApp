package edu.jcu.plandoll16.printerlocator;

import android.content.Context;
import android.database.SQLException;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Class to coordinate functionality between PrinterDataSource and Printers.
 *
 * @author Peter Landoll
 * @version 0.9
 * @since 2016-4-30
 */
public class PrinterHelper {
    private ArrayList<Printer> availablePrinters, printerArrayList;
    private ArrayList<String> names;
    private ArrayList<String[]> fileContents;
    private Context mContext;
    private String CSVOutputString;

    public PrinterHelper(Context context) {
        availablePrinters = new ArrayList<>();
        printerArrayList = new ArrayList<>();
        names = new ArrayList<>();
        fileContents = new ArrayList<>();
        mContext = context;
        // First, populate the list of printers from the database
        populatePrinterList();
        // Then, get printer status information from online CSV
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
            // Wait a maximum of 15 seconds for the thread to finish, error handling comes later
            getCSV.join(15 * 1000);
            handleCSVString(CSVOutputString);
        } catch (Exception ex) {
            Log.e("fetchPrinterList", ex.getMessage());
        }
    }

    /**
     * Handles output text from the CSV file to store the contents properly as an ArrayList.
     *
     * @param contents  the contents of the CSV file to be passed in from fetchPrinterLists's
     *                  getCSVRunnable Thread
     */
    private void handleCSVString(String contents) {
        // Split on Windows newlines, as the CSV is generated by a Windows machine
        String[] csvLines = contents.split("\r\n");
        // Split each line on commas, and add it to the fileContents ArrayList<String[]>
        for (String line : csvLines) {
            fileContents.add(line.split(","));
        }
        // Get position of status code from CSV header, should be 2
        int statusIndex = Arrays.asList(fileContents.get(0)).indexOf("ERR#");
        // Error catching for if "ERR#" isn't found in the header
        if (statusIndex == -1) {
            statusIndex = 2;
        }
        // We don't need the header row of the CSV, so remove it
        fileContents.remove(0);
        for (String[] printerInfoArray : fileContents) {
            // if the printer with name printerInfoArray[0] is in the list populated by the
            //  database, then update that printer with status code printerInfoArray[statusIndex]
            int position = names.indexOf(printerInfoArray[0].replaceAll("\\$", ""));
            if (position != -1) {
                int code = Integer.parseInt(printerInfoArray[statusIndex]);
                printerArrayList.get(position).setStatusCode(code);
                // To have a separate List for available printers
                if (code == 0) {
                    availablePrinters.add(printerArrayList.get(position));
                }
            }
        }
        // Sort printers based on name, see Printer.compareTo method
        Collections.sort(printerArrayList);
        Collections.sort(availablePrinters);
    }

    /**
     * Pulls records from the database and adds them to the list of printers.
     *
     * All database access happens here.
     */
    private void populatePrinterList() {
        // Copy database from the assets folder to internal storage.
        String dir = "/data/data/" + mContext.getPackageName() + "/databases/";
        String path = dir + "printer.db";
        File databaseFile = new File(path);
        if (databaseFile.exists()) {
            databaseFile.mkdirs();
            databaseFile.delete();
        }
        try {
            copyDB(mContext.getAssets().open("printer.db"), new FileOutputStream(path));
        } catch (IOException ex) {
            Toast.makeText(mContext, "Can't copy printer.db", Toast.LENGTH_LONG).show();
        }
        PrinterDataSource dataSource = new PrinterDataSource(mContext);
        try {
            dataSource.open();
            printerArrayList = dataSource.getAllRecords();
            names = dataSource.getNames();
            dataSource.close();
        } catch (Exception ex) {
            Log.e("Database error?", ex.getMessage());
        }
    }

    /**
     * copies a database from the assets folder.
     *
     * @param in InputStream for existing database
     * @param out OutputStream for new database location
     * @throws IOException
     */
    public void copyDB(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        out.flush();
        out.close();
        in.close();
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
                CSVOutputString = out;
            } catch (Exception ex) {
                Log.e("getCSVRunnable", ex.getMessage());
            }
        }
    }

    public ArrayList<Printer> getAvailablePrinters() {
        return availablePrinters;
    }
}
