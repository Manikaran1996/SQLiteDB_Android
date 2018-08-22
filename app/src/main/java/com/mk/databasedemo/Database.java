package com.mk.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;

/**
 * Created by manikaran on 20/7/18.
 */

class Database {

    private DatabaseHelper dbh;
    private SQLiteDatabase db;

    private String getDate() {
        return String.valueOf(Calendar.DATE) +
                "/" +
                (Calendar.MONTH + 1) +
                "/" +
                Calendar.YEAR;
    }

    Database(Context context) {
        dbh = new DatabaseHelper(context);
        db = null;
    }

    void openWritableDb() {
        db =  dbh.getWritableDatabase();
    }

    void closeDatabase() {
        db.close();
    }

    private boolean insertTask(String task_name, String details, String date) {
        if(db == null) {
            openWritableDb();
        }
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_TASK_NAME, task_name);
        cv.put(DatabaseHelper.COLUMN_DETAILS, details);
        cv.put(DatabaseHelper.COLUMN_DATE, date);
        cv.put(DatabaseHelper.COLUMN_DONE, "no");
        long id = db.insert(DatabaseHelper.todo_list_table_name, null, cv);
        return id != -1;
    }

    boolean insertTask(String task_name, String details) {
        return insertTask(task_name, details, getDate());
    }

    Cursor getTasks() {
        if(db == null)
            openWritableDb();
        return db.query(DatabaseHelper.todo_list_table_name, new String[] {"_id", DatabaseHelper.COLUMN_TASK_NAME, DatabaseHelper.COLUMN_DETAILS}
                , null, null, null, null, null);
    }

    static class DatabaseHelper extends SQLiteOpenHelper {


        static String DB_NAME = "app_main_db";
        static int DB_VERSION = 1;
        static String todo_list_table_name = "Todo";
        static String COLUMN_TASK_NAME = "task_name";
        static String COLUMN_DONE = "done";
        static String COLUMN_DETAILS = "details";
        static String COLUMN_DATE = "date";
        static String CREATE_TABLE = "create table " + todo_list_table_name + " ( _id integer auto_increment, "
                +  COLUMN_TASK_NAME +" varchar(50), " + COLUMN_DONE + " varchar(5), " + COLUMN_DETAILS + " varchar(100), " +
                COLUMN_DATE + " varchar(12) )";

        static String DROP_TABLE = "drop table if exists " + todo_list_table_name;

        DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE);
            db.execSQL(CREATE_TABLE);
        }
    }

}