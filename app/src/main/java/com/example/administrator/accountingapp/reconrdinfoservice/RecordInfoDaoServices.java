package com.example.administrator.accountingapp.reconrdinfoservice;

import com.example.administrator.accountingapp.pojo.RecordInfo;

import java.util.LinkedList;
import java.util.Map;

public interface RecordInfoDaoServices {
    public boolean insert(RecordInfo recordInfo);
    public boolean delete(String uuid);
    public boolean update(String uuid,RecordInfo recordInfo);
    public LinkedList<Map<String,Object>> queryDateRecoed(String date);
    public boolean queryDate(String date);
    public LinkedList queryAllDate();
    public String queryAmount(String date);
}
