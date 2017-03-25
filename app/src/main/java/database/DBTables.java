package database;

import android.provider.BaseColumns;

/**
 * Created by Milica on 12-Feb-17.
 */

// class for holding all db names and columns

public class DBTables {

    //to prevent accidental instantiation of the contract class, give empty constructor
    public DBTables() {}

    // inner class for table content
    public static abstract class EUVisits implements BaseColumns{
        public static final String TABLE_NAME = "EU_VISITS";
        public static final String COLUMN_NUSER_ID = "user_id";
        public static final String COLUMN_DATE_ENTRY = "date_entry";
        public static final String COLUMN_DATE_EXIT = "date_exit";
        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_DESC = "description";
    }

}
