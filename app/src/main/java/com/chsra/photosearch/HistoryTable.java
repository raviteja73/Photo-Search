package com.chsra.photosearch;

import android.database.sqlite.SQLiteDatabase;

public class HistoryTable {
    static final String TABLENAME = "history";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_SEARCHTERM = "searchTerm";
    static final String COLUMN_DATE = "date";
    static final String COLUMN_TIME = "time";

    //creating database
    static public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLENAME + " (");
        sb.append(COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(COLUMN_SEARCHTERM + " text not null, ");
        sb.append(COLUMN_DATE + " text not null, ");
        sb.append(COLUMN_TIME + " text not null);");

        try {
            db.execSQL(sb.toString());
        } catch (android.database.SQLException ex) {
            ex.printStackTrace();
        }
    }

    //upgrading database
    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLENAME);
        HistoryTable.onCreate(db);
    }
}