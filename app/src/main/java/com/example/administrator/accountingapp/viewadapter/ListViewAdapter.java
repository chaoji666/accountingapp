package com.example.administrator.accountingapp.viewadapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.accountingapp.R;

import java.util.List;
import java.util.Map;

public class ListViewAdapter extends BaseAdapter {

    List<Map<String,Object>> list;
    Context context;
    public double total;

    public ListViewAdapter(Context context,List<Map<String,Object>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewHolder holder = new ListViewHolder();
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item,null);
            holder.img = convertView.findViewById(R.id.item_img);
            holder.type = convertView.findViewById(R.id.recordtype);
            holder.time = convertView.findViewById(R.id.recordtime);
            holder.balance = convertView.findViewById(R.id.record_balance);

            convertView.setTag(holder);
        }else{
            holder = (ListViewHolder) convertView.getTag();
        }
        int drawableId = context.getResources().getIdentifier("icon_"+list.get(position).get("recordType")+"_white", "mipmap", context.getPackageName());
        Drawable res = context.getResources().getDrawable(drawableId);
        holder.img.setImageDrawable(res);
        holder.type.setText((String)list.get(position).get("recordType"));
        holder.time.setText((String)list.get(position).get("time"));
        if (list.get(position).get("recordInOrOut").equals("1")){
            holder.balance.setText("-"+(String)list.get(position).get("balance"));
        }else if (list.get(position).get("recordInOrOut").equals("0")){
            holder.balance.setText("+"+(String)list.get(position).get("balance"));
        }
        return convertView;
    }

}
