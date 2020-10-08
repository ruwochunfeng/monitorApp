package com.zhoubing.bishe.Inerdoor;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.view.View;

import java.util.List;

/**
 * Created by dell on 2018/4/11.
 */

public class IndoorLocation {
    public WifiManager init(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        return  wifiManager;
    }

    public WifiInfo getWifiInfo(WifiManager wifiManager){


        return  wifiManager.getConnectionInfo();

    }
    public int getRssi(WifiInfo wifiInfo){


        return  wifiInfo.getRssi();
    }
    public  int getIp(WifiInfo wifiInfo){

        return wifiInfo.getIpAddress();
    }




}
