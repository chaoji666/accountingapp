package com.example.administrator.accountingapp.viewadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class RecordInfoFragmentAdapter extends FragmentPagerAdapter {

    List<Fragment> mview;
    public RecordInfoFragmentAdapter(FragmentManager fm,List<Fragment> mview) {
        super(fm);
        this.mview = mview;
    }

    @Override
    public Fragment getItem(int i) {
        return mview.get(i);
    }

    @Override
    public int getCount() {
        return mview.size();
    }
}
