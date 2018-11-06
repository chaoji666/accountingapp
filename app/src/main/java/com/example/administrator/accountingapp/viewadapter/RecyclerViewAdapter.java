package com.example.administrator.accountingapp.viewadapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.accountingapp.R;
import com.example.administrator.accountingapp.listener.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    Context context;
    List<Map<String,Object>> list;
    RecyclerViewHolder holder;
    RecyclerViewItemClickListener listener;
    public RecyclerViewAdapter(Context context,List<Map<String,Object>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        if(holder==null){
        holder = new RecyclerViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_item, viewGroup,
                false));
//        viewGroup.setTag(holder);
//        }else{
//            holder = (RecyclerViewHolder) viewGroup.getTag();
//        }
        return holder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder recyclerViewHolder, final int i) {
        recyclerViewHolder.tv.setText((String)list.get(i).get("recordType"));
        int drawableId = context.getResources().getIdentifier("icon_"+list.get(i).get("recordType"), "mipmap", context.getPackageName());
        Drawable res = context.getResources().getDrawable(drawableId);
        recyclerViewHolder.img.setImageDrawable(res);

        if(listener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v,i);
                }
            });
        }
    }

    public void setOnRecyclerViewItemClickListerer(RecyclerViewItemClickListener listener){
        this.listener = listener;
    }

}

class RecyclerViewHolder extends RecyclerView.ViewHolder
{
    ImageView img;
    TextView tv;

    public RecyclerViewHolder(View view)
    {
        super(view);
        img = view.findViewById(R.id.recyclerview_img);
        tv = view.findViewById(R.id.recyclerview_text);
    }
}