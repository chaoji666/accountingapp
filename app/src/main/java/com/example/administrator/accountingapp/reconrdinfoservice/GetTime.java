package com.example.administrator.accountingapp.reconrdinfoservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class GetTime extends SimpleFormatter {
    Date current = new Date();
    public String getDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(current);
        return date;
    }
    public String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        String time = format.format(current);
        return time;
    }
    public long getDate(String datestr){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Date date = stringToDate(datestr, "yyyy-MM-dd"); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = date.getTime(); // date类型转成long类型
            return currentTime;
        }
    }
}
