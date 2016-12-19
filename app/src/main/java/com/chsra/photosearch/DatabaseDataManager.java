package com.chsra.photosearch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class DatabaseDataManager {
    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private HistoryDAO historyDAO;

    public DatabaseDataManager(Context mContext){
        this.mContext=mContext;
        dbOpenHelper=new DatabaseOpenHelper(this.mContext);
        db=dbOpenHelper.getWritableDatabase();
        historyDAO=new HistoryDAO(db);
    }

    public void close(){
        if(db!=null){
            db.close();
        }
    }

    public HistoryDAO getHistoryDAO(){
        return this.historyDAO;
    }

    public long saveSearchInfo(History history){
        return this.historyDAO.save(history);
    }

    public boolean deleteHistory(){
        return this.historyDAO.delete();
    }

    public List<History> getCompleteHistory(){
        return this.historyDAO.getCompleteHistory();
    }

    public List<History> getHistoryAscending(){
        return this.historyDAO.getTopThreeFromHistory();
    }
}
