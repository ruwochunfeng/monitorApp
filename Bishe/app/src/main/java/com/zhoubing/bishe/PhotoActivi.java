package com.zhoubing.bishe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhoubing.bishe.adapter.PhotoAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoActivi extends AppCompatActivity {
    ImageView imageView ;
    ViewPager viewPager;
    List<View> mList;
    int length = 0;
    LayoutInflater lf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo1);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("key");
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        mList = new ArrayList<>();

        int index = bundle.getInt("daxiao");
        Log.e("大小",""+index);
        init();
        final TextView textView = (TextView)findViewById(R.id.text_view_pager);
        textView.setText(String.valueOf((index+1)+"/"+length));
        PagerAdapter pagerAdapter = new PhotoAdapter(mList);


        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(index);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                textView.setText(String.valueOf((position+1)+"/"+length));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void init(){


       lf = getLayoutInflater().from(this);
        File[] files = getImageToGallery(this);
        length = files.length;
        for(int i=0;i<files.length;i++){
             View view =  lf.inflate(R.layout.image_infla,null);

             ImageView imageView = (ImageView) view.findViewById(R.id.image_pho);
         //   TextView textView = (TextView) view.findViewById(R.id.text_view_pager);
             imageView .setImageBitmap(BitmapFactory.decodeFile(files[i].getAbsolutePath()));
            int j= i+1;
         //   textView.setText(String.valueOf((j)+"/"+files.length));
            mList.add(view);
        }


    }
    public File[] getImageToGallery(Context context) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            return  null;
        }
        File[] files = appDir.listFiles();
        if(files.length==0){
            return  null;
        }

        return files;
    }


}
