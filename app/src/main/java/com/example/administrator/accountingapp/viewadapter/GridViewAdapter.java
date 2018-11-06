package com.example.administrator.accountingapp.viewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.accountingapp.R;

public class GridViewAdapter extends BaseAdapter {

    String[] arr;
    Context context;
    TextView tv;
    public GridViewAdapter(String[] arr,Context context) {
        this.arr = arr;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arr.length;
    }

    @Override
    public Object getItem(int position) {
        return arr[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_item,null);
            tv = convertView.findViewById(R.id.gridview_text);
        }
        tv.setText(arr[position]+"");

        return convertView;
    }
}
