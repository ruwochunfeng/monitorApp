package com.zhoubing.bishe.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhoubing.bishe.R;
import com.zhoubing.bishe.sqilite.RssiSQLiteHelper;

import java.util.List;

/**
 * Created by dell on 2018/3/21.
 */

public class RssiHistoryAdapter extends RecyclerView.Adapter<RssiHistoryAdapter.ViewHolder> {
    private List<String> mImage;
    private List<String> mDate;
    private RssiSQLiteHelper mySQLiteHelper;
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rssi_history_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        mySQLiteHelper = new RssiSQLiteHelper(context,"RssiValue.db",null,1);
        return viewHolder;
    }

    public RssiHistoryAdapter(List<String> mImage, List<String> mDate, Context context){
        this.mImage = mImage;
        this.mDate = mDate;
        this.context = context;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
           holder.textView_name.setText(mImage.get(position));
           holder.textView_date.setText(mDate.get(position));
           holder.textView_date.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View v) {
                   String text = mImage.get(position);

                   mImage.remove(position);
                   mDate.remove(position);
                   notifyDataSetChanged();
                   SQLiteDatabase db = mySQLiteHelper.getWritableDatabase();
                   String[] args = {text};
                   db.delete("rssi","location=?",args);
                   return true;
               }
           });

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
            textView_name = (TextView) itemView.findViewById(R.id.id_location);
            textView_date = (TextView) itemView.findViewById(R.id.history_rssi);

        }
    }
}
