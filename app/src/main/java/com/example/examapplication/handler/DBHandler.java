package com.example.examapplication.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.examapplication.model.Country;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    public static final String DbName ="MyDB";
    public DBHandler(Context context) { super(context, DbName, null,1);}
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query ="CREATE TABLE IF NOT EXISTS countrys (id INTEGER PRIMARY KEY AUTOINCREMENT, countryName Text, population long)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public boolean Insert_Records(Country country){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues c = new ContentValues();
        c.put("countryName", country.getCountryName());
        c.put("population", country.getPopulation());

        long result = db.insert("county", null, c);
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }
    public ArrayList<String> Read_Records(){
        ArrayList<String> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM countrys";
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()){
            String row = cursor.getString(0) + "\t\t\t" + cursor.getString(1) + "\t\t\t" + cursor.getString(2);
            data.add(row);
        }
        return data;
    }
    public boolean Update_Record(Country country){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues c = new ContentValues();
        c.put("countryName", country.getCountryName());
        c.put("population", country.getPopulation());

        int result = db.update("countys", c, "id=?", new String[]{String.valueOf(country.getId())});
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }
    public boolean  Delete_Record(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete("countys", "id=?", new String[]{id});
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }
}
