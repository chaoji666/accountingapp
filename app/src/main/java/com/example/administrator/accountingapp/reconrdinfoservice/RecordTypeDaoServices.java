package com.example.administrator.accountingapp.reconrdinfoservice;

import java.util.LinkedList;
import java.util.Map;

public interface RecordTypeDaoServices {
    public LinkedList<Map<String,Object>> queryRecoedType(int inOrOut);
}
