package Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import recycler_handlers.InfoData;

/**
 * Created by dede on 09/03/2015.
 */
public class DatabaseAlarms extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "alarmsManager";

    // Contacts table name
    private static final String TABLE_ALARM = "alarms";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TIME = "mainText";
    private static final String KEY_ABOUT = "optionalText";
    private final ArrayList<InfoData> infoDatas = new ArrayList<InfoData>();

    public DatabaseAlarms(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ALARM_TABLE = "CREATE TABLE " + TABLE_ALARM + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TIME + " TEXT,"
                + KEY_ABOUT + " TEXT" + ")";
        db.execSQL(CREATE_ALARM_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARM);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public DatabaseAlarms addAlarm(InfoData infoData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TIME, infoData.getMainText());
        values.put(KEY_ABOUT, infoData.getOptionalText());
        // Inserting Row
        db.insert(TABLE_ALARM, null, values);
        db.close(); // Closing database connection

        return this;
    }

    // Getting single contact
    InfoData getAlarm(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ALARM, new String[]{KEY_ID,
                        KEY_TIME, KEY_ABOUT}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        InfoData infoData = new InfoData(
                Utils.chooseResFromTime(cursor.getString(1)),
                cursor.getString(1),
                cursor.getString(2),
                false);
        // return contact
        cursor.close();
        db.close();

        return infoData;
    }

    // Getting All Contacts
    public ArrayList<InfoData> getAlarms() {
        try {
            infoDatas.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_ALARM;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    InfoData infoData = new InfoData(
                            Utils.chooseResFromTime(cursor.getString(1)),
                            cursor.getString(1),
                            cursor.getString(2),
                            false);
                    // Adding contact to list
                    infoDatas.add(infoData);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return infoDatas;
        } catch (Exception e) {
            Log.e("All infoDatas", "" + e);
        }

        return infoDatas;
    }

    // Updating single contact
    public int updateAlarm(InfoData infoData, int position) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIME, infoData.getMainText());
        values.put(KEY_ABOUT, infoData.getOptionalText());

        // updating row
        return db.update(TABLE_ALARM, values, KEY_ID + " = ?",
                new String[]{String.valueOf(position)});
    }

    // Deleting single contact
    public void deleteAlarm(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALARM, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // Getting contacts Count
    public int getAlarmsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ALARM;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
