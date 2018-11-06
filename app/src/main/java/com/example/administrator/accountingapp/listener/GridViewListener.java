package com.example.administrator.accountingapp.listener;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.example.administrator.accountingapp.activity.AddRecordActivity;

public class GridViewListener implements AdapterView.OnItemClickListener{

    Context context;
    String[] arr;
    public GridViewListener(Context context,String[] arr) {
        this.context = context;
        this.arr = arr;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(AddRecordActivity.balance.getText().toString().contains(".")){
            arr[11]="";
        }else{
            arr[11]=".";
        }
        AddRecordActivity.balance.setText(AddRecordActivity.balance.getText()+arr[position]);
    }
}
