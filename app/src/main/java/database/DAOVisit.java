package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Milica on 12-Feb-17.
 */


public class DAOVisit {

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public  DAOVisit (Context context) {
        dbHelper = new DBHelper(context);
        // get permissin on db to write mode
        database = dbHelper.getWritableDatabase();
    }

    public int dbVersion(){
        return database.getVersion();
    }

    public void close(){
        dbHelper.close();
    }

    // insert new Visit in the database
    public void addVisit(Visit visit){
        ContentValues cv = new ContentValues();
        cv.put(DBTables.EUVisits.COLUMN_DATE_ENTRY, visit.getEntryDate());
        cv.put(DBTables.EUVisits.COLUMN_DATE_EXIT, visit.getExitDate());
        cv.put(DBTables.EUVisits.COLUMN_COUNTRY, visit.getCountry());
        cv.put(DBTables.EUVisits.COLUMN_DESC, visit.getDesc());
        database.insert(DBTables.EUVisits.TABLE_NAME, null, cv);
    }

    //get speccific visit
    public Visit getVisit(String visitId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBTables.EUVisits.TABLE_NAME, new String[] {
                DBTables.EUVisits.COLUMN_DATE_ENTRY,
                DBTables.EUVisits.COLUMN_DATE_EXIT,
                DBTables.EUVisits.COLUMN_COUNTRY,
                DBTables.EUVisits.COLUMN_DESC}, DBTables.EUVisits.COLUMN_NUSER_ID + "=?",
                new String[] {visitId}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Visit visit = new Visit();
        visit.setVisitId(cursor.getString(0));
        visit.setEntryDate(cursor.getLong(1));
        visit.setExitDate(cursor.getLong(2));
        visit.setCountry(cursor.getString(3));
        visit.setDesc(cursor.getString(4));
        return visit;
    }

    public List<Visit> getAllVisits(){

        List<Visit> allVisits = new ArrayList<Visit>();
        String query = "SELECT  * FROM " + DBTables.EUVisits.TABLE_NAME;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                Visit visit = new Visit();
                visit.setVisitId(cursor.getString(0));
                visit.setEntryDate(cursor.getLong(1));
                visit.setExitDate(cursor.getLong(2));
                visit.setCountry(cursor.getString(3));
                visit.setDesc(cursor.getString(4));
                allVisits.add(visit);
            } while (cursor.moveToNext());
        }
        return allVisits;
    }

    /*
    gives back all visits that need to be taken into account when calculating number of days left
    */
    public List<Visit> getCountableVisits(){

        List<Visit> countableVisits = new ArrayList<Visit>();
        Calendar c = Calendar.getInstance();
        long milisec = c.getTimeInMillis();
        long days = TimeUnit.DAYS.toMillis(180);
        long diff = milisec - days;
        String query = "SELECT * FROM " + DBTables.EUVisits.TABLE_NAME +
                " WHERE " + DBTables.EUVisits.COLUMN_DATE_ENTRY + " > " + diff;

        Log.d("SQL IZRAZ: ", query);
        Log.d("SQL MILISEC: ", String.valueOf(milisec));
        Log.d("SQL DAYS: ", String.valueOf(days));
        Log.d("SQL DIFF: ", String.valueOf(diff));
        //String query = "SELECT * FROM " + DBTables.EUVisits.TABLE_NAME;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                Visit visit = new Visit();
                visit.setVisitId(cursor.getString(0));
                visit.setEntryDate(cursor.getLong(1));
                visit.setExitDate(cursor.getLong(2));
                visit.setCountry(cursor.getString(3));
                visit.setDesc(cursor.getString(4));
                Log.d("VISIT: ", visit.getCountry() + " " + String.valueOf(visit.getEntryDate()));
                countableVisits.add(visit);
            } while (cursor.moveToNext());
        }
        return countableVisits;
    }

    public int getVisitsCount(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String request = "SELECT * FROM " + DBTables.EUVisits.TABLE_NAME;
        Cursor c = db.rawQuery(request, null);
        c.close();
        return c.getCount();
    }


    public void updateVisit(Visit visit){
        ContentValues cv = new ContentValues();
        cv.put(DBTables.EUVisits.COLUMN_DATE_ENTRY, visit.getEntryDate());
        cv.put(DBTables.EUVisits.COLUMN_DATE_EXIT, visit.getExitDate());
        cv.put(DBTables.EUVisits.COLUMN_COUNTRY, visit.getCountry());
        cv.put(DBTables.EUVisits.COLUMN_DESC, visit.getDesc());
        database.update(DBTables.EUVisits.TABLE_NAME, cv, DBTables.EUVisits.COLUMN_NUSER_ID + "=?",
                new String[] {visit.getVisitId()});

    }

    public void deleteVisit(String id){
        database.delete(DBTables.EUVisits.TABLE_NAME, DBTables.EUVisits.COLUMN_NUSER_ID + " =?",
                new String[]{id});
        database.close();
    }

}
