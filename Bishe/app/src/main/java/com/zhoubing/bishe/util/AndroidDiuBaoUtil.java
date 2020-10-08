package com.zhoubing.bishe.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.zhoubing.bishe.MoshiFour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by dell on 2018/3/23.
 */

public class AndroidDiuBaoUtil {

     public  static  void pingDiuBao(String ip, Context context) throws IOException {

         String lost = new String();
         String delay = new String();
         Process p = Runtime.getRuntime().exec("ping -c 10 " + ip);
         BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
         String str = new String();
         while((str=buf.readLine())!=null){
             Log.e("丢包延时",""+str);
             if(str.contains("time")){
                 int index = str.indexOf("time");
                 String sub = str.substring(index+5);
                 String[] shuju= sub.split(" ");
                 Log.e("时间",shuju[0]);


             }
//             if(str.contains("packet loss")){
//                 int i= str.indexOf("received");
//                 int j= str.indexOf("%");
//                 System.out.println("丢包率:"+str.substring(i+10, j+1));
////                  System.out.println("丢包率:"+str.substring(j-3, j+1));
//                 lost = str.substring(i+10, j+1);
//                 Log.e("丢包测试","丢包率"+lost);
//             }
//             if(str.contains("avg")){
//                 int i=str.indexOf("/", 20);
//                 int j=str.indexOf(".", i);
//                 System.out.println("延迟:"+str.substring(i+1, j));
//                 delay =str.substring(i+1, j);
//                 delay = delay+"ms";
//                 Log.e("丢包延时","延时"+delay);
//             }

         }


     }
}
