package com.example.administrator.accountingapp.pojo;

public class RecordInfo {

    public Integer recordInOrOut;   //支入支出，1为支出，0为支入
    public String recordType;       //消费方式
    public String time;               //消费时间
    public String date;             //消费日期
    public double balance;          //消费金额
    public String remarks;          //备注
    public String uuid;             //唯一标识ID

    public Integer getRecordInOrOut() {
        return recordInOrOut;
    }

    public void setRecordInOrOut(Integer recordInOrOut) {
        this.recordInOrOut = recordInOrOut;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
