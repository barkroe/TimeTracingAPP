/*
package com.example.mabdullah.timetracingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

*/
/**
 * Created by mabdullah on 26.03.2018.
 *//*


public class DBManager extends SQLiteOpenHelper {
    public static final String DBName = "TimeManager.db";
    private static final String User_TABLE_CREATE = "create table User (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, password TEXT)";
    private static final String Project_TABLE_CREATE = "create table Project (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT)";
    private static final String Time_TABLE_CREATE = "create table Time (id INTEGER PRIMARY KEY AUTOINCREMENT, date DATE, von datetime default current_timestamp, bis datetime )";
    private static final String CrossTable_TABLE_CREATE = "create table CrossTable (id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER REFERENCES User(id), projectId INTEGER REFERENCES Project(id), timeId INTEGER REFERENCES TIme(id) )";


    public DBManager(Context context) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User_TABLE_CREATE);
        db.execSQL(Project_TABLE_CREATE);
        db.execSQL(Time_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists User ");
        onCreate(db);
    }

    void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("password", user.getPassword());

        db.insert("User", null, values);
        db.close();

    }

    User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("User", new String[]{"id", "name", "password"}, "id" + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        User user = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        return user;
    }

    public ArrayList<Project> getProjects() {
        ArrayList<Project> projectList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String projectlist = "select * from Project";
        Cursor cursor = db.rawQuery(projectlist, null);
        cursor.moveToFirst();
        if (cursor != null) {
            // Loop through all Results
            do {
                String ProjectId = cursor.getString(id);
                String projectName = cursor.getString(name);
            } while (c.moveToNext());

        */
/*Cursor cursor= db.query("Project", new String[]{"id","name","description"},"id"+ "=?",
                new String[]{String.valueOf(id)},null,null,null,null);

        projectList.add(cursor);*//*

            return projectList;
        }

    */
/*public List<User> getAllUsers(){
        List<User> userList=new ArrayList<>();
        // vedio halt bei 17 min

    }*//*

    }



}
*/
