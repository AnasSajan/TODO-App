package com.example.anassajan.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Anas Sajan on 17-Feb-18.
 */

public class Dbhelper extends SQLiteOpenHelper {

    private static final String DB_NAME ="TESTer";
    private static final int DB_VER =1;
    private static final  String DB_TABLE ="Task";
    private static final  String DB_COLUMN ="TaskName";

    public Dbhelper(Context context){
        super(context,DB_NAME,null,DB_VER);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL);",DB_TABLE,DB_COLUMN);
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = String.format("DELETE TABLE IF EXISTS %s",DB_TABLE);
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);

    }

    public void insertNewTask(String task){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN,task);
        sqLiteDatabase.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        sqLiteDatabase.close();
    }

    public void deleteTask(String task){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(DB_TABLE,DB_COLUMN + "= ?",new String[]{task});
        sqLiteDatabase.close();
    }
    public ArrayList<String> getTaskList(){
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(DB_TABLE,new String[]{DB_COLUMN},null,null,null,null,null);
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_COLUMN);
            taskList.add(cursor.getString(index));
        }

            cursor.close();
        sqLiteDatabase.close();


        return taskList;
    }

}
