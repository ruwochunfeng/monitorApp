package com.zhoubing.bishe.fragment.jiemian;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.zhoubing.bishe.MoshiFour;
import com.zhoubing.bishe.R;
import com.zhoubing.bishe.adapter.ImageAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by dell on 2018/2/26.
 */

public class PhotoFragment extends Fragment {

    ImageView imageView;
    RecyclerView recyclerView;
    Button button;
    Button button_dele;
    List<String> str;
    List<Bitmap> photo;
    private String conStr = "http://192.168.1.100:8080/?action=snapshot";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo,container,false);
        imageView = (ImageView) view.findViewById(R.id.image_jilu);
        str = new ArrayList<>();
        photo =  new ArrayList<>();
        init();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        button = (Button) view.findViewById(R.id.tianjia);
        button_dele = (Button) view.findViewById(R.id.delete);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(linearLayoutManager);
        final ImageAdapter imageAdapter  = new ImageAdapter(photo,(MoshiFour) getActivity(),getActivity());



        recyclerView.setAdapter(imageAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

 //               MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),  BitmapFactory.decodeResource(getActivity().getResources(),R.mipmap.robot), "title", "description");
//                saveImageToGallery(getActivity(), BitmapFactory.decodeResource(getActivity().getResources(),R.mipmap.robot));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Bitmap bitmap = Glide.with(getActivity()).load(conStr).asBitmap().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    .get();
                            saveImageToGallery(getActivity(),bitmap);
                            photo.add(bitmap);
                            imageAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        });

        button_dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImageToGallery(getActivity());
                if(photo.size()!=0){
                    photo.remove(0);
                    imageAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getActivity(),"已经没有图片可以删除了",Toast.LENGTH_SHORT).show();

                }


            }
        });

        return view;
    }


    public void init(){
         boolean ceshi = getImageToGallery(getActivity());
        if(ceshi){

            Toast.makeText(getActivity(),"读取成功",Toast.LENGTH_SHORT).show();
        }else {

            Toast.makeText(getActivity(),"没有图片",Toast.LENGTH_SHORT).show();

        }


    }

    /*
       * 存储图片
       * */
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        Log.e("图片存储路径",storePath);
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    /*
       *  获取图片
       * */
    public  boolean getImageToGallery(Context context) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
          return false;
        }
        File[] files = appDir.listFiles();
        if(files.length==0){

            return false;
        }
        for(int i =0;i<files.length;i++){

            photo.add(BitmapFactory.decodeFile(files[i].getAbsolutePath()));


        }

        return true;
    }

    /*
    *  删除图片
    * */
    public  void deleteImageToGallery(Context context) {
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
        files[files.length-1].delete();

    }
}
