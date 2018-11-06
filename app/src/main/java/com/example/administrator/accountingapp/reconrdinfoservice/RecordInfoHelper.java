package com.example.administrator.accountingapp.reconrdinfoservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecordInfoHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "account.db";
    private static final int DB_VERSION = 1;
    private String recordTableSql = "create table if not exists recordinfo("
            +"id integer primary key autoincrement,"
            +"uuid text,"
            +"recordInOrOut integer,"
            +"recordType text,"
            +"date date,"
            +"time varchar(10),"
            +"balance double,"
            +"remarks text)";
    private String recordTypeTableSql = "create table if not exists recordtype(" +
            "id integer primary key autoincrement," +
            "recordType text unique," +
            "inOrOut integer)";

    public RecordInfoHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(recordTableSql);
            db.execSQL(recordTypeTableSql);
            recordTypeTableInit(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertRecordType(SQLiteDatabase db,String type,int inOrOut) {
        ContentValues values = new ContentValues();
        values.put("recordType",type);
        values.put("inOrOut",inOrOut);
        db.insert("recordtype",null,values);
    }
    public void recordTypeTableInit(SQLiteDatabase db){
        //1为支出，0为收入，2为通用
        insertRecordType(db,"appstore",1);
        insertRecordType(db,"bonus",0);
        insertRecordType(db,"book",1);
        insertRecordType(db,"computer",1);
        insertRecordType(db,"drinking",1);
        insertRecordType(db,"entertain",1);
        insertRecordType(db,"food",1);
        insertRecordType(db,"general",2);
        insertRecordType(db,"gift",1);
        insertRecordType(db,"groceries",1);
        insertRecordType(db,"house",1);
        insertRecordType(db,"investment",0);
        insertRecordType(db,"medical",1);
        insertRecordType(db,"mobile",1);
        insertRecordType(db,"movie",1);
        insertRecordType(db,"travel",1);
        insertRecordType(db,"transport",1);
        insertRecordType(db,"ticket",1);
        insertRecordType(db,"social",1);
        insertRecordType(db,"shopping",1);
        insertRecordType(db,"salary",0);
        insertRecordType(db,"reimburse",0);
        insertRecordType(db,"personal",1);
        insertRecordType(db,"parttime",0);
        insertRecordType(db,"redpocket",0);
        insertRecordType(db,"transfer",1);

    }
}
