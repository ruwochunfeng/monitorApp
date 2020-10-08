package com.zhoubing.bishe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fengmap.android.map.FMMap;
import com.fengmap.android.map.FMMapView;

public class FengMapActivity extends AppCompatActivity {
    FMMap mFMMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feng_map);
        FMMapView mapView = (FMMapView) findViewById(R.id.mapview);
        mFMMap = mapView.getFMMap();       //获取地图操作对象

        String bid = "comditu";             //地图id
        mFMMap.openMapById(bid, true);          //打开地图
    }

    @Override
    public void onBackPressed() {
        if (mFMMap != null) {
            mFMMap.onDestroy();
        }
        super.onBackPressed();
    }
}
