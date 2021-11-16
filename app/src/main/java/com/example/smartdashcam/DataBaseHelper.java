package com.example.smartdashcam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DataBase = "Signin.db";

    public DataBaseHelper(Context context) {
        super(context, "Signin.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDataBase) {
        MyDataBase.execSQL("create Table users(username TEXT primary key," +
                "password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MYDataBase, int oldVersion, int newVersion) {
        MYDataBase.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String username, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDatabase.insert("users" , null , contentValues);

        if (result == -1) return false;
        else
            return true;
    }


    public Boolean checkusername(String username) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where username = ? ", new String[ ] {username});

        if(cursor.getCount() >0) {
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where username = ? and password = ? ", new String[ ] {username, password});

        if(cursor.getCount() >0) {
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean updatepassword(String username, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        long result = MyDatabase.update("users" , contentValues, "username = ?", new String[ ] {username});

        if (result == -1)
            return false;
        else
            return true;
    }


}
