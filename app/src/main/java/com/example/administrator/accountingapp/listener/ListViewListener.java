package com.example.administrator.accountingapp.listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.accountingapp.R;
import com.example.administrator.accountingapp.activity.AddRecordActivity;
import com.example.administrator.accountingapp.activity.MainActivity;
import com.example.administrator.accountingapp.reconrdinfoservice.RecordInfoDaoImpl;
import com.example.administrator.accountingapp.reconrdinfoservice.RecordInfoDaoServices;
import com.example.administrator.accountingapp.viewadapter.ListViewAdapter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ListViewListener implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{

    Context context;
    List<Map<String,Object>> list;
    RecordInfoDaoServices services;
    ListViewAdapter adapter;
    TextView textView;

    public ListViewListener(Context context, List<Map<String, Object>> list, ListViewAdapter adapter) {
        this.context = context;
        this.list = list;
        this.adapter = adapter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        View remarks_view = LayoutInflater.from(context).inflate(R.layout.remarks_poupwindow, null);
        textView = remarks_view.findViewById(R.id.remarks_text);
        textView.setText("备注："+list.get(position).get("remarks").toString());
        PopupWindow popupWindow = new PopupWindow(remarks_view, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, false);

        // 设置点击外边可消失
        popupWindow.setOutsideTouchable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置获取焦点，否则无法点击
        popupWindow.setFocusable(true);
        // 显示
//        popupWindow.showAsDropDown(view);
        popupWindow.showAtLocation(view, Gravity.CENTER,(int)view.getX(),(int)view.getY());
//        Toast.makeText(context,list.get(position).get("remarks").toString(),Toast.LENGTH_SHORT).show();;
    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(new String[]{"编辑","删除"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    Intent intent = new Intent(context, AddRecordActivity.class);
                    intent.putExtra("uuid",list.get(position).get("uuid").toString());
                    intent.putExtra("date", MainActivity.today.getText());
                    context.startActivity(intent);
                }else if(which==1){
                    services = new RecordInfoDaoImpl(context);
                    services.delete(list.get(position).get("uuid").toString());
                    String date = list.get(position).get("date").toString();
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                    MainActivity.balance.setText("今日总收入:¥"+""+new BigDecimal(Double.parseDouble(services.queryAmount(date))).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    MainActivity.sliding_text.setText(""+new BigDecimal(Double.parseDouble(services.queryAmount("null"))).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }
        });
        builder.show();
        return true;
    }
}
