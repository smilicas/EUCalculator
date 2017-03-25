package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Milica on 12-Feb-17.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "my_database";
    public static final int DB_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String NUMERIC_TYPE = " NUMERIC";
    private static final String COMMA_SEP = ",";

    private static final String CREATE_EUVISITS_TABLE = "CREATE TABLE " + DBTables.EUVisits.TABLE_NAME + " (" +
            DBTables.EUVisits.COLUMN_NUSER_ID +  " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            DBTables.EUVisits.COLUMN_DATE_ENTRY + NUMERIC_TYPE + COMMA_SEP +
            DBTables.EUVisits.COLUMN_DATE_EXIT + NUMERIC_TYPE + COMMA_SEP +
            DBTables.EUVisits.COLUMN_COUNTRY + TEXT_TYPE + COMMA_SEP +
            DBTables.EUVisits.COLUMN_DESC + TEXT_TYPE +
            " )";

    private static final String DELETE_EUVISITS_TABLE = "DROP TABLE IF EXISTS " + DBTables.EUVisits.TABLE_NAME;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EUVISITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(CREATE_EUVISITS_TABLE);
        onCreate(db);
    }
}
