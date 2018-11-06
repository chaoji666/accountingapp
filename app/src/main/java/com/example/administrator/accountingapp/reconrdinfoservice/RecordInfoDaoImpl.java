package com.example.administrator.accountingapp.reconrdinfoservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.accountingapp.pojo.RecordInfo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class RecordInfoDaoImpl implements RecordInfoDaoServices {

    private RecordInfoHelper helper;
    private SQLiteDatabase db;
    private Context context;
    private static final String TABLENAME = "recordinfo";
    public RecordInfoDaoImpl(Context context){
        this.context = context;
        helper = new RecordInfoHelper(context);
    }

    @Override
    public boolean insert(RecordInfo recordInfo) {

        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uuid",recordInfo.getUuid());
        values.put("recordInOrOut",recordInfo.getRecordInOrOut());
        values.put("recordType",recordInfo.getRecordType());
        values.put("time",recordInfo.getTime());
        values.put("date",recordInfo.getDate());
        values.put("balance",recordInfo.getBalance());
        values.put("remarks",recordInfo.getRemarks());
//        values.put("typeImg",recordInfo.getTypeImg());
        long i = db.insert(TABLENAME,null,values);
        db.close();
        if(i>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean delete(String uuid) {
        db = helper.getWritableDatabase();
        int i = db.delete(TABLENAME,"uuid=?",new String[]{uuid});
        db.close();
        if(i>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean update(String uuid, RecordInfo recordInfo) {
        db = helper.getWritableDatabase();
        boolean flag1 = this.delete(uuid);
        boolean flag2 = this.insert(recordInfo);
        db.close();
        if(flag1&&flag2){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public LinkedList<Map<String, Object>> queryDateRecoed(String date) {

        Cursor cursor = null;
        LinkedList<Map<String,Object>> list = new LinkedList<Map<String,Object>>();
        Map<String,Object> map;
        db = helper.getWritableDatabase();
        try{
            if(date.equals("null")){
                cursor = db.query(TABLENAME,new String[]{"*"},null
                        ,new String[]{},null,null,"time");
            }else{
                cursor = db.query(TABLENAME,new String[]{"*"},"date=?"
                        ,new String[]{date},null,null,"time");
            }

             while(cursor.moveToNext()){
                map = new HashMap<String,Object>();
                for(int i = 0;i<cursor.getColumnCount();i++){
                    map.put(cursor.getColumnName(i),cursor.getString(i));
                }
                list.add(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
                //cursor.close();
                //db.close();
        }

        return list;
    }

    @Override
    public boolean queryDate(String date) {
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLENAME,new String[]{"date"},"date=?"
                ,new String[]{date},null,null,null);
        cursor.close();
        db.close();
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public LinkedList queryAllDate() {
        db = helper.getReadableDatabase();
        LinkedList list = new LinkedList();
        Cursor cursor = db.rawQuery("select distinct date from recordinfo order by date",new String[]{});
        while(cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex("date")));
        }
        return list;
    }

    @Override
    public String queryAmount(String date) {
        db = helper.getReadableDatabase();
        double amount = 0;
        Cursor cursor ;
        if(!date.equals("null")){
            cursor= db.query(TABLENAME,new String[]{"*"},"date=?",new String[]{date},null,null,null);
        }else{
            cursor= db.query(TABLENAME,new String[]{"*"},null,new String[]{},null,null,null);
        }
        while(cursor.moveToNext()){
            if (cursor.getString(cursor.getColumnIndex("recordInOrOut")).equals("1")){
                amount -= Double.parseDouble((String)cursor.getString(cursor.getColumnIndex("balance")));
            }else if (cursor.getString(cursor.getColumnIndex("recordInOrOut")).equals("0")){
                amount += Double.parseDouble((String)cursor.getString(cursor.getColumnIndex("balance")));
            }
        }
        return amount+"";
    }
}
