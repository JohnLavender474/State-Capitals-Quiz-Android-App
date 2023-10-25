package edu.uga.cs.csci4830_project4.database;

import android.provider.BaseColumns;

public final class DatabaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DatabaseContract() {
    }

    /* Inner class that defines the table contents */
    public static class DatabaseEntry implements BaseColumns {
        public static final String TABLE_NAME = "state_capitals";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }
}

