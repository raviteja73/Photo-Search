package com.chsra.photosearch;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {
    private SQLiteDatabase db;

    public HistoryDAO(SQLiteDatabase db) {
        this.db = db;
    }

    //saving history to database
    public long save(History history) {
        ContentValues values = new ContentValues();
        values.put(HistoryTable.COLUMN_SEARCHTERM, history.getSearchTerm());
        values.put(HistoryTable.COLUMN_DATE, history.getDate());
        values.put(HistoryTable.COLUMN_TIME, history.getTime());

        return db.insert(HistoryTable.TABLENAME, null, values);
    }

    //deleting history from database
    public boolean delete() {
        return db.delete(HistoryTable.TABLENAME, null, null) > 0;
    }

    //get complete history
    public List < History > getCompleteHistory() {
        List < History > searchHistories = new ArrayList < History > ();
        Cursor c = db.query(HistoryTable.TABLENAME, new String[] {
                HistoryTable.COLUMN_ID, HistoryTable.COLUMN_SEARCHTERM, HistoryTable.COLUMN_DATE, HistoryTable.COLUMN_TIME
        }, null, null, null, null, HistoryTable.COLUMN_ID + " DESC");

        if (c != null && c.moveToFirst()) {
            do {
                History history = buildSearchHistoryFromCursor(c);
                if (history != null) {
                    searchHistories.add(history);
                }

            } while (c.moveToNext());
            if (!c.isClosed()) {
                c.close();
            }
        }
        return searchHistories;
    }

    //get top three from history
    public List < History > getTopThreeFromHistory() {
        List < History > searchHistories = new ArrayList < History > ();
        Cursor c = db.query(HistoryTable.TABLENAME, new String[] {
                HistoryTable.COLUMN_ID, HistoryTable.COLUMN_SEARCHTERM, HistoryTable.COLUMN_DATE, HistoryTable.COLUMN_TIME
        }, null, null, null, null, HistoryTable.COLUMN_ID + " ASC");
        int cnt = 0;
        if (c != null && c.moveToFirst()) {
            do {
                History history = buildSearchHistoryFromCursor(c);
                if (history != null) {
                    searchHistories.add(history);
                    cnt++;
                }
            } while (c.moveToNext() && cnt < 3);
            if (!c.isClosed()) {
                c.close();
            }
        }
        return searchHistories;
    }


    private History buildSearchHistoryFromCursor(Cursor c) {
        History history = null;
        if (c != null) {
            history = new History();
            history.set_id(c.getInt(0));
            history.setSearchTerm(c.getString(1).toUpperCase());
            history.setDate(c.getString(2) + "  " + c.getString(3));
            history.setTime(c.getString(3));
        }
        return history;
    }
}