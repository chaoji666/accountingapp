package com.example.administrator.accountingapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.accountingapp.R;
import com.example.administrator.accountingapp.listener.ListViewListener;
import com.example.administrator.accountingapp.reconrdinfoservice.RecordInfoDaoImpl;
import com.example.administrator.accountingapp.reconrdinfoservice.RecordInfoDaoServices;
import com.example.administrator.accountingapp.viewadapter.ListViewAdapter;

import java.util.LinkedList;

public class RecordInfoFragment extends android.support.v4.app.Fragment {
    RecordInfoDaoServices services;
    public ListViewAdapter adapter;
    public ListView listView;
    public RecordInfoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        services = new RecordInfoDaoImpl(container.getContext());
        View view = inflater.inflate(R.layout.fragment_page,null);
        listView = view.findViewById(R.id.listview);
        LinkedList list = services.queryDateRecoed((String) getArguments().get("date"));
        ListViewListener listener;// = new ListViewListener(container.getContext(),list,adapter);
        if(list.size()>0){
            adapter = new ListViewAdapter(container.getContext(),list);
            listener = new ListViewListener(container.getContext(),list,adapter);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(listener);
            listView.setOnItemLongClickListener(listener);
        }else{
            view = inflater.inflate(R.layout.fragment_page_null,null);
        }
        return view;
    }

}
