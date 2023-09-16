package com.TwentyOneCodes.madmathematics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "madMathsHighScores.db";
    public static final String EASY_MODE_TABLE_NAME = "easyModeTable_data";
    public static final String INTER_MODE_TABLE_NAME = "interModeTable_data";
    public static final String HARD_MODE_TABLE_NAME = "hardModeTable_data";
    public static final String col1 = "ID";
    public static final String col2 = "SCORES";
    public static final String col3 = "TimeTaken";

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createEasyTable = "CREATE TABLE "+EASY_MODE_TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, SCORES TEXT, TimeTaken TEXT);";
        String createInterTable = "CREATE TABLE "+INTER_MODE_TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, SCORES TEXT, TimeTaken TEXT);";
        String createHardTable = "CREATE TABLE "+HARD_MODE_TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, SCORES TEXT, TimeTaken TEXT);";
        db.execSQL(createEasyTable);
        db.execSQL(createInterTable);
        db.execSQL(createHardTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " +EASY_MODE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " +INTER_MODE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " +HARD_MODE_TABLE_NAME);

    }

    public boolean addData(String score, int type, String TimeTaken){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col2, score);
        contentValues.put(col3, TimeTaken);
        long result= 0;
        switch(type){
            case 1:
                result = db.insert(EASY_MODE_TABLE_NAME, null, contentValues);
                break;

            case 2:
                result = db.insert(INTER_MODE_TABLE_NAME, null, contentValues);
                break;

            case 3:
                result = db.insert(HARD_MODE_TABLE_NAME, null, contentValues);
                break;

        }

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getScores(int type){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = null;
        switch(type){
            case 1:
                data = db.rawQuery("SELECT * FROM "+EASY_MODE_TABLE_NAME+" ORDER BY CAST("+col2+" as int) DESC",null);
                break;

            case 2:
                data = db.rawQuery("SELECT * FROM "+INTER_MODE_TABLE_NAME+" ORDER BY CAST("+col2+" as int) DESC",null);
                break;

            case 3:
                data = db.rawQuery("SELECT * FROM "+HARD_MODE_TABLE_NAME+" ORDER BY CAST("+col2+" as int) DESC",null);
                break;

            case 4:
                data = db.rawQuery("SELECT * FROM "+EASY_MODE_TABLE_NAME,null);
                break;

            case 5:
                data = db.rawQuery("SELECT * FROM "+INTER_MODE_TABLE_NAME,null);
                break;

            case 6:
                data = db.rawQuery("SELECT * FROM "+HARD_MODE_TABLE_NAME,null);
                break;

        }

        return data;
    }

    public void deleteAllTheData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ EASY_MODE_TABLE_NAME);
        db.execSQL("DELETE FROM "+ INTER_MODE_TABLE_NAME);
        db.execSQL("DELETE FROM "+ HARD_MODE_TABLE_NAME);
        db.close();
    }
}
