package com.example.administrator.accountingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.accountingapp.R;
import com.example.administrator.accountingapp.listener.GridViewListener;
import com.example.administrator.accountingapp.listener.RecyclerViewItemClickListener;
import com.example.administrator.accountingapp.pojo.RecordInfo;
import com.example.administrator.accountingapp.reconrdinfoservice.GetTime;
import com.example.administrator.accountingapp.reconrdinfoservice.RecordInfoDaoImpl;
import com.example.administrator.accountingapp.reconrdinfoservice.RecordInfoDaoServices;
import com.example.administrator.accountingapp.reconrdinfoservice.RecordTypeDaoImpl;
import com.example.administrator.accountingapp.reconrdinfoservice.RecordTypeDaoServices;
import com.example.administrator.accountingapp.viewadapter.GridViewAdapter;
import com.example.administrator.accountingapp.viewadapter.RecyclerViewAdapter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AddRecordActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GridView gridView;
    List<Map<String,Object>> list;
    RecordTypeDaoServices services;
    RecordInfoDaoServices recordInfoDaoServices;
    ImageView change;
    int flag=1;
    public static TextView balance;
    TextView remarktext;
    String type="";
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addrecord);
        initView();
    }

    public void initView(){
        //初始化控件
        balance = findViewById(R.id.balance);
        change = findViewById(R.id.change);
        remarktext = findViewById(R.id.remarks);
        //初始化recyclerview
        services = new RecordTypeDaoImpl(AddRecordActivity.this);
        recordInfoDaoServices = new RecordInfoDaoImpl(AddRecordActivity.this);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(AddRecordActivity.this,4));

        list = services.queryRecoedType(1);
        adapter = new RecyclerViewAdapter(AddRecordActivity.this,list);
        adapter.setOnRecyclerViewItemClickListerer(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view,int i) {
                type = list.get(i).get("recordType").toString();
                view.setSelected(true);
                Toast.makeText(AddRecordActivity.this,list.get(i).get("recordType").toString(),Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);

        //初始化gridview
        String []arr={"7","8","9","4","5","6","1","2","3","","0","."};
        gridView = findViewById(R.id.gridview);
        gridView.setAdapter(new GridViewAdapter(arr,AddRecordActivity.this));
        gridView.setOnItemClickListener(new GridViewListener(this,arr));
    }

    public void clickEvent(View view){

        switch (view.getId()){
            case R.id.change:
                if(flag==0){
                    list = services.queryRecoedType(1);
                    change.setImageResource(R.mipmap.cost);
                    flag = 1;
                }else if(flag==1){
                    list = services.queryRecoedType(0);
                    change.setImageResource(R.mipmap.earn);
                    flag = 0;
                }
                for(Map<String,Object> map :list){
                    Log.i("msg", map.get("recordType").toString()+"=="+map.get("inOrOut"));
                }
//                adapter.notifyDataSetChanged();
//                RecyclerViewAdapter adapter = new RecyclerViewAdapter(AddRecordActivity.this,list);
//                recyclerView.setAdapter(adapter);
                adapter = new RecyclerViewAdapter(AddRecordActivity.this,list);
                adapter.setOnRecyclerViewItemClickListerer(new RecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view,int i) {
                        type = list.get(i).get("recordType").toString();
                        Toast.makeText(AddRecordActivity.this,list.get(i).get("recordType").toString(),Toast.LENGTH_SHORT).show();
                    }
                });
                recyclerView.setAdapter(adapter);
                break;
            case R.id.back:
                balance.setText(balance.getText().subSequence(0,balance.getText().length()-1));
                break;
            case R.id.done:
                if(type.equals("")){
                    Toast.makeText(this,"请选择消费类型",Toast.LENGTH_SHORT).show();
                }else if(balance.getText().toString().substring(1).equals("")){
                    Toast.makeText(this,"请输入消费金额",Toast.LENGTH_SHORT).show();
                }else{

                    Intent intent = getIntent();
                    String date = intent.getStringExtra("date");    //日期
                    String uuid = intent.getStringExtra("uuid");    //uuid
                    String time = new GetTime().getTime();                 //time
                    int inOrOut = flag;                                    //消费方式
//                String type;                                            //消费类型
                    double cost = Double.parseDouble(balance.getText().toString().substring(1));//消费金额
                    String remarks = remarktext.getText().toString();       //备注
                    if(remarks==null){
                        remarks = " ";
                    }

                    RecordInfo recordInfo = new RecordInfo();
                    recordInfo.setUuid(UUID.randomUUID().toString());
                    recordInfo.setRecordInOrOut(inOrOut);
                    recordInfo.setBalance(new BigDecimal(cost).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    recordInfo.setDate(date);
                    recordInfo.setRecordType(type);
                    recordInfo.setTime(time);
                    recordInfo.setRemarks(remarks);

                    recordInfoDaoServices.update(uuid,recordInfo);
                    startActivity(new Intent(AddRecordActivity.this,MainActivity.class));
                    finish();
                }
                break;
        }
    }
}

