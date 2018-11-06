package com.example.administrator.accountingapp.reconrdinfoservice;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class RecordTypeDaoImpl implements  RecordTypeDaoServices {

    private RecordInfoHelper helper;
    private SQLiteDatabase db;
    private Context context;
    private static final String TABLENAME = "recordtype";
    public RecordTypeDaoImpl(Context context){
        this.context = context;
        helper = new RecordInfoHelper(context);
    }

    @Override
    public LinkedList<Map<String, Object>> queryRecoedType(int inOrOut) {

        Cursor cursor = null;
        LinkedList<Map<String,Object>> list = new LinkedList<Map<String,Object>>();
        Map<String,Object> map;
        db = helper.getWritableDatabase();
        try{
            cursor = db.query(TABLENAME,new String[]{"*"},null
                    ,new String[]{},null,null,null);
            while(cursor.moveToNext()){
                map = new HashMap<String,Object>();
                for(int i = 0;i<cursor.getColumnCount();i++){
                    map.put(cursor.getColumnName(i),cursor.getString(i));
                }
                if(map.get("inOrOut").equals(inOrOut+"")||map.get("inOrOut").equals("2")) {
                    list.add(map);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            //cursor.close();
            //db.close();
        }

        return list;
    }
}
