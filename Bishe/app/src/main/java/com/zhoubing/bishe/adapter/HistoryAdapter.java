package com.zhoubing.bishe.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhoubing.bishe.MoshiFour;
import com.zhoubing.bishe.PhotoActivi;
import com.zhoubing.bishe.R;

import java.util.List;

/**
 * Created by dell on 2018/3/21.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<String> mImage;
    private List<String> mDate;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    public HistoryAdapter(List<String> mImage,List<String> mDate){
        this.mImage = mImage;
        this.mDate = mDate;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
           holder.textView_name.setText(mImage.get(position));
           holder.textView_date.setText(mDate.get(position));

    }


    @Override
    public int getItemCount() {
        return mImage.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView_name;
        private TextView textView_date;
        public ViewHolder(final View itemView) {
            super(itemView);
            textView_name = (TextView) itemView.findViewById(R.id.id);
            textView_date = (TextView) itemView.findViewById(R.id.history_time);

        }
    }
}
