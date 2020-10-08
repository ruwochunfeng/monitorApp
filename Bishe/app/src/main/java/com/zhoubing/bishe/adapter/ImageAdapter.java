package com.zhoubing.bishe.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhoubing.bishe.MoshiFour;
import com.zhoubing.bishe.PhotoActivi;
import com.zhoubing.bishe.R;

import java.io.File;
import java.util.List;

/**
 * Created by dell on 2018/3/21.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<Bitmap> mImage;
    static MoshiFour moshiFour;
    public  int daxiao = 0;
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_image,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    public ImageAdapter(List<Bitmap> mImage,MoshiFour moshiFour,Context context){

        this.mImage = mImage;
        this.moshiFour = moshiFour;
        this.context = context;

    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Bitmap imageView = mImage.get(position);
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.imageView.setImageBitmap(imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(moshiFour,PhotoActivi.class);
                Bundle bundle =new Bundle();
                bundle.putInt("daxiao",position);

                intent.putExtra("key", bundle);// 封装 email
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                        .makeScaleUpAnimation(holder.imageView,(int)holder.imageView.getX(),(int)holder.imageView.getY(),0,0);

                moshiFour.startActivity(intent,activityOptionsCompat.toBundle());
            }
        });
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mImage.remove(position);
                notifyDataSetChanged();
                deleteImageToGallery(context,position);

                return true;
            }
        });



    }


    @Override
    public int getItemCount() {
        return mImage.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        static int ceshi = 0;
        public ViewHolder(final View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_jilu);

        }



    }

    public  void deleteImageToGallery(Context context,int index) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            return;
        }
        File[] files = appDir.listFiles();
        if(files.length==0){

            return;
        }
        files[index].delete();

    }
}
