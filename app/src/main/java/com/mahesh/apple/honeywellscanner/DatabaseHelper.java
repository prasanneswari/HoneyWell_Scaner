package com.mahesh.apple.honeywellscanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Barcode.db";
    public static final String TABLE_NAME = "ScanTable";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "barcodedata";
    public static final String COL_3 = "companydb";
    public static final String COL_4 = "commanditydb";
    public static final String COL_5 = "timedb";
    public static final String COL_6 = "datedb";
    public static final String COL_7 = "quantitydb";

    public static final String TABLE_NAME1 = "OutwardTable";
    public static final String COL_11 = "ID";
    public static final String COL_21 = "barcodedata";
    public static final String COL_31 = "companydb";
    public static final String COL_41 = "commanditydb";
    public static final String COL_51 = "timedb";
    public static final String COL_61 = "datedb";
    public static final String COL_71 = "quantitydb";

    public static final String TABLE_NAME2 = "CompanyTable";
    public static final String COL_COMID = "ID";
    public static final String COL_COMPANY = "company";

    public static final String TABLE_NAME3 = "CommodityTable";
    public static final String COL_COMMADYID = "ID";
    public static final String COL_COMMODITY = "commodity";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,barcodedata TEXT,companydb TEXT,commanditydb TEXT,timedb TEXT,datedb TEXT,quantitydb TEXT)");
        db.execSQL("create table " + TABLE_NAME1 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,barcodedata TEXT,companydb TEXT,commanditydb TEXT,timedb TEXT,datedb TEXT,quantitydb TEXT)");
        db.execSQL("create table " + TABLE_NAME2 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,company TEXT)");
        db.execSQL("create table " + TABLE_NAME3 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,commodity TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);

        onCreate(db);


    }

    public boolean insertData(String barcodedata,String companydb,String commanditydb,String timedb,String datedb,String quantitydb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,barcodedata);
        contentValues.put(COL_3,companydb);
        contentValues.put(COL_4,commanditydb);
        contentValues.put(COL_5,timedb);
        contentValues.put(COL_6,datedb);
        contentValues.put(COL_7,quantitydb);

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertData1(String barcodedata,String companydb,String commanditydb,String timedb,String datedb,String quantitydb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_21,barcodedata);
        contentValues.put(COL_31,companydb);
        contentValues.put(COL_41,commanditydb);
        contentValues.put(COL_51,timedb);
        contentValues.put(COL_61,datedb);
        contentValues.put(COL_71,quantitydb);

        long result = db.insert(TABLE_NAME1,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean insertData2(String companydb) {

       // Log.d("log","companydb database"+companydb);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL_12,id);
        contentValues.put(COL_COMPANY,companydb);

        long result = db.insert(TABLE_NAME2,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean insertData3(String commoditydb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
       // contentValues.put(COL_13,id);
        contentValues.put(COL_COMMODITY,commoditydb);

        long result = db.insert(TABLE_NAME3,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getScanData(String barcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where "+COL_2 + " = ? " , new String[] {barcode + ""} );
        return res;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public Cursor getAllData1() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME1,null);
        return res;
    }
    public Cursor getAllData2() {
        Log.d("array length---", "--enterdb-" );

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME2,null);
        return res;
    }
    public Cursor getAllData3() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME3,null);
        return res;
    }

    public boolean updateData(int id,String barcodeU,String companyU,String commadityU,String timeU,String dateU,String quantityU) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,barcodeU);
        contentValues.put(COL_3,companyU);
        contentValues.put(COL_4,commadityU);
        contentValues.put(COL_5,timeU);
        contentValues.put(COL_6,dateU);
        contentValues.put(COL_7,quantityU);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] {String.valueOf(id)});
        return true;
    }
    public boolean updateData1(int id,String barcodeU,String companyU,String commadityU,String timeU,String dateU,String quantityU) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_11,id);
        contentValues.put(COL_21,barcodeU);
        contentValues.put(COL_31,companyU);
        contentValues.put(COL_41,commadityU);
        contentValues.put(COL_51,timeU);
        contentValues.put(COL_61,dateU);
        contentValues.put(COL_71,quantityU);
        db.update(TABLE_NAME1, contentValues, "ID = ?",new String[] {String.valueOf(id)});
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
}
