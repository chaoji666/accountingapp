package com.example.administrator.accountingapp.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.accountingapp.R;
import com.example.administrator.accountingapp.fragment.RecordInfoFragment;
import com.example.administrator.accountingapp.listener.FragmentChangeListener;
import com.example.administrator.accountingapp.reconrdinfoservice.GetTime;
import com.example.administrator.accountingapp.reconrdinfoservice.RecordInfoDaoImpl;
import com.example.administrator.accountingapp.reconrdinfoservice.RecordInfoDaoServices;
import com.example.administrator.accountingapp.viewadapter.RecordInfoFragmentAdapter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    List<Fragment> mviews;
    RecordInfoDaoServices services;
    ViewPager viewPager;
    public static TextView today;
    ImageView addbtn;
    public static TickerView balance;
    SlidingMenu sm;
    ImageView imageView;   //slidingmenu imageview
    public static TextView sliding_text; //slidingmenu textview
    AnimationDrawable animationDrawable;
    AlertDialog.Builder ab;
    String yearSelect;
    String monthSelect;
    String dayOfMonthSelect;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    /*
    初始化日历布局
     */
    public void initCalendarView(){
        ab = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.calendarview_page,null);
        CalendarView calendarView = view.findViewById(R.id.calendarview);
        LinkedList dateList = services.queryAllDate();
        calendarView.setMinDate(new GetTime().getDate(dateList.get(0).toString()));
        calendarView.setMaxDate(new GetTime().getDate(dateList.get(dateList.size()-1).toString()));
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                yearSelect = year + "";
                month += 1;
                dayOfMonthSelect = dayOfMonth + "";
                if(month<10){
                    monthSelect = "0" + month;
                }else{
                    monthSelect = month + "";
                }
            }
        });
        ab.setView(view);
        ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i = 0;i<mviews.size();i++){
                    if (mviews.get(i).getArguments().getString("date").equals(yearSelect+"-"+monthSelect+"-"+dayOfMonthSelect)){
                        viewPager.setCurrentItem(i);
                    }
                }
            }
        });
        ab.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    /*
    初始化布局
     */
    public void initView(){
        mviews = new ArrayList<>();
        services = new RecordInfoDaoImpl(this);
        viewPager = findViewById(R.id.viewpager);
        today = findViewById(R.id.date);
        addbtn = findViewById(R.id.addbtn);
        balance = findViewById(R.id.total);
        balance.setCharacterLists(TickerUtils.provideNumberList());
        balance.setAnimationInterpolator(new OvershootInterpolator());

        //slidingmenu
        sm = new SlidingMenu(this);
        sm.setBehindOffsetRes(R.dimen.slidingmenu);
        sm.setMode(SlidingMenu.LEFT);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        sm.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        View view = LayoutInflater.from(this).inflate(R.layout.slidingmenu_page,null);
        imageView = view.findViewById(R.id.slidingmenu_img);
        sliding_text = view.findViewById(R.id.slidingmenu_tv);

        animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.animation);
        imageView.setBackgroundDrawable(animationDrawable);
        sliding_text.setText(new BigDecimal(Double.parseDouble(services.queryAmount("null"))).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+"");
        animationDrawable.start();
        sm.setMenu(view);

        //bundle date
        Bundle bundle;
        String date = new GetTime().getDate();

        //fragment data
        LinkedList linkedList = services.queryAllDate();
        for (Object object : linkedList){
            Fragment fragment = new RecordInfoFragment();
            bundle = new Bundle();
            bundle.putString("date",object.toString());
            fragment.setArguments(bundle);
            mviews.add(fragment);
        }
        if(!linkedList.contains(new GetTime().getDate())){
            Fragment fragment = new RecordInfoFragment();
            bundle = new Bundle();
            bundle.putString("date",date);
            fragment.setArguments(bundle);
            mviews.add(fragment);
        }

            RecordInfoFragmentAdapter adapter = new RecordInfoFragmentAdapter(getSupportFragmentManager(),mviews);

            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(mviews.size()-1);
            today.setText(date);
            balance.setText("今日总收入:¥"+""+new BigDecimal(Double.parseDouble(services.queryAmount(date))).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
            viewPager.setOnPageChangeListener(new FragmentChangeListener(mviews,this));

    }
    /*
        点击事件
     */
    public void clickEvents(View view){

        switch (view.getId()){
            case R.id.addbtn:
                Intent intent = new Intent(this,AddRecordActivity.class);
                intent.putExtra("date",today.getText());
                intent.putExtra("uuid","");
                startActivity(intent);
                break;
            case R.id.slidingmenu:
                sm.toggle();
                sliding_text.setText(services.queryAmount("null"));
                break;
            case R.id.selectdate:
                initCalendarView();
                ab.show();
                break;
            case R.id.slidingmenu_btn:
                ab = new AlertDialog.Builder(this);
                LayoutInflater inflater = LayoutInflater.from(this);
                View chartview = inflater.inflate(R.layout.piechart_page,null);

                pieChart = chartview.findViewById(R.id.piechart);
                //饼状图
                pieChart.setUsePercentValues(true);
                pieChart.getDescription().setEnabled(false);
                pieChart.setExtraOffsets(5, 10, 5, 5);
                pieChart.setDragDecelerationFrictionCoef(0.95f);

                //设置中间文件
                pieChart.setCenterText("");
                pieChart.setDrawHoleEnabled(true);
                pieChart.setHoleColor(Color.WHITE);
                pieChart.setTransparentCircleColor(Color.WHITE);
                pieChart.setTransparentCircleAlpha(110);
                pieChart.setHoleRadius(58f);
                pieChart.setTransparentCircleRadius(61f);
                pieChart.setDrawCenterText(true);
                pieChart.setRotationAngle(0);

                // 触摸旋转
                pieChart.setRotationEnabled(true);
                pieChart.setHighlightPerTapEnabled(true);

                //变化监听
                pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {

                    }

                    @Override
                    public void onNothingSelected() {

                    }
                });

                //报表数据
                LinkedList<Map<String,Object>> list = services.queryDateRecoed("null");//所有金额数据
                List typeList = new ArrayList();//所有类型数据
                List<Map<String,Object>> infoList = new ArrayList<>();
                int amount = 0;
                //计算支出的总金额、获取所有类型
                for (Map map:list){
                    if(map.get("recordInOrOut").equals("1")){
                        amount += Double.parseDouble(map.get("balance").toString());
                        typeList.add(map.get("recordType").toString());
                    }
                }
                //typelist去重
                HashSet h = new HashSet(typeList);
                typeList.clear();
                typeList.addAll(h);
                //获取同一类型下所有金额总和
                for (Object str : typeList){
                    double i = 0;
                    for (Map map : list){
                        if(str.equals(map.get("recordType"))){
                            i += Double.parseDouble(map.get("balance").toString());
                        }
                    }
                    Map infoMap = new HashMap();
                    infoMap.put(str,i);
                    infoList.add(infoMap);
                }
                //添加数据
                ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
                String key;
                for (Map map : infoList){
                    Set<String> set = map.keySet();
                    for (String key1:set) {
                        entries.add(new PieEntry((int)((double)map.get(key1)/amount*100),key1));
                    }
                }

                //设置数据
                setData(entries);
                pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
                Legend l = pieChart.getLegend();
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                l.setOrientation(Legend.LegendOrientation.VERTICAL);
                l.setDrawInside(false);
                l.setXEntrySpace(7f);
                l.setYEntrySpace(0f);
                l.setYOffset(0f);

                Legend mLegend = pieChart.getLegend();
                mLegend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);  //在左边中间显示比例图
                mLegend.setFormSize(14f);//比例块字体大小 
                mLegend.setXEntrySpace(4f);//设置距离饼图的距离，防止与饼图重合
                mLegend.setYEntrySpace(4f);
                //设置比例块换行...
                mLegend.setWordWrapEnabled(true);
                mLegend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);//设置字跟图表的左右顺序

//                mLegend.setTextColor(Color.BLACK);
                mLegend.setForm(Legend.LegendForm.SQUARE);//设置比例块形状，默认为方块

                // 输入标签样式
                pieChart.setEntryLabelColor(Color.BLACK);
                pieChart.setEntryLabelTextSize(12f);


                ab.setView(chartview);
                ab.show();
                break;
        }
    }
    //设置数据
    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "总支出情况");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        //刷新
        pieChart.invalidate();
    }

}
