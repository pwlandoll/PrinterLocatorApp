package edu.jcu.plandoll16.printerlocator;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for communicating with the printer.db database via PrinterSQLiteHelper.
 *
 * @author Peter Landoll
 * @version 1.0
 * @since 2016-5-6
 */
public class PrinterDataSource {
    private ArrayList<String> names;
    private PrinterSQLiteHelper dbHelper;
    private SQLiteDatabase database;
    private String[] cols = {PrinterSQLiteHelper.KEY, PrinterSQLiteHelper.NAME, PrinterSQLiteHelper.LAT,
            PrinterSQLiteHelper.LON, PrinterSQLiteHelper.DES, PrinterSQLiteHelper.BUILD};

    /**
     * Constructor.
     *
     * @param context necessary for passing to PrinterSQLiteHelper
     */
    public PrinterDataSource(Context context) {
        names = new ArrayList<>();
        dbHelper = new PrinterSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getReadableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Gets all records from printer.db database, converts them to an ArrayList of Printers.
     *
     * @return ArrayList of Printers that all have information directly from printer.db
     */
    public ArrayList<Printer> getAllRecords() {
        ArrayList<Printer> printers = new ArrayList<>();
        Cursor cursor = database.query(PrinterSQLiteHelper.TABLE_NAME, cols, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Printer p = cursorToRecord(cursor);
                printers.add(p);
                names.add(p.getName());
            } while (cursor.moveToNext());
        }
        cursor.close();
        return printers;
    }

    /**
     * Converts a cursor to a Printer.
     *
     * Could be refactored into getAllRecords since it's only used there.
     *
     * @param cursor to be converted into a Printer
     * @return Printer object with necessary details filled in from database entry.
     */
    private Printer cursorToRecord(Cursor cursor) {
        Printer p = new Printer();
        p.setId(cursor.getInt(0));
        p.setName(cursor.getString(1));
        p.setLocationLatitude(cursor.getDouble(2));
        p.setLocationLongitude(cursor.getDouble(3));
        p.setDescription(cursor.getString(4));
        p.setBuilding(cursor.getString(5));
        return p;
    }

    public ArrayList<String> getNames() {
        return names;
    }
}
