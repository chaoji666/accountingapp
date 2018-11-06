package com.example.administrator.accountingapp.listener;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.administrator.accountingapp.activity.MainActivity;
import com.example.administrator.accountingapp.reconrdinfoservice.RecordInfoDaoImpl;
import com.example.administrator.accountingapp.reconrdinfoservice.RecordInfoDaoServices;

import java.math.BigDecimal;
import java.util.List;

public class FragmentChangeListener implements ViewPager.OnPageChangeListener {
    List<Fragment> list;
    RecordInfoDaoServices services;
    public FragmentChangeListener(List<Fragment> list, Context context) {
        this.list = list;
        services = new RecordInfoDaoImpl(context);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        MainActivity.today.setText(list.get(i).getArguments().getString("date"));
        MainActivity.balance.setText("今日总收入:¥"+""+new BigDecimal(Double.parseDouble(services.queryAmount(list.get(i).getArguments().getString("date")))).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
