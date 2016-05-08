package edu.jcu.plandoll16.printerlocator;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Extension of SQLiteOpenHelper to help facilitate connection with printer.db database
 *
 * Methods aside from the constructor probably won't be used, but still written just in case.
 * Otherwise, based on the in-class example.
 *
 * @author Peter Landoll
 * @version 0.1
 * @since 2016-5-6
 */
public class PrinterSQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME =	"printers";
    public static final String KEY = "_id";
    public static final String NAME = "name";
    public static final String LAT = "latitude";
    public static final String LON = "longitude";
    public static final String DES = "description";
    public static final String BUILD = "building";
    private static final String DATABASE_NAME = "printer.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DB_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            KEY + " integer primary key autoincrement," +
            NAME + " text not null," +
            LAT + " decimal," +
            LON + " decimal," +
            DES + " text," +
            BUILD + " text);";

    public PrinterSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DB_CREATE);
        }
        catch (SQLException ex) {
            Log.d("SQL Error", "Cannot create printer.db database");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(PrinterSQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion
                + " to " + newVersion + " , which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

