package edu.jcu.plandoll16.printerlocator;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PrinterDataSource {
    private ArrayList<String> names;
    private PrinterSQLiteHelper dbHelper;
    private SQLiteDatabase database;
    private String[] cols = {PrinterSQLiteHelper.KEY, PrinterSQLiteHelper.NAME, PrinterSQLiteHelper.LAT,
            PrinterSQLiteHelper.LON, PrinterSQLiteHelper.DES, PrinterSQLiteHelper.BUILD};


    public PrinterDataSource(Context context) {
        names = new ArrayList<>();
        dbHelper = new PrinterSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getReadableDatabase();
        //database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Printer> getAllRecords() {
        ArrayList<Printer> printers = new ArrayList<>();
        Cursor cursor = database.query(PrinterSQLiteHelper.TABLE_NAME, cols, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Printer p = cursorToRecord(cursor);
            printers.add(p);
            names.add(p.getName());
            cursor.moveToNext();
        }
        cursor.close();
        return printers;
    }

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
