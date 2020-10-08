package com.zhoubing.bishe;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jiahuan.svgmapview.SVGMapView;
import com.jiahuan.svgmapview.overlay.SVGMapLocationOverlay;
import com.jiahuan.svgmapview.sample.helper.AssetsHelper;
import com.zhoubing.bishe.sshUtil.SvgSSGUtil;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SvgActivity extends AppCompatActivity {

    int x= 305;
    int y=84;
    int degree = 90;
    SvgSSGUtil svgSSGUtil;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);
        final SVGMapView mapView = (SVGMapView) findViewById(R.id.mapView);
        textView = (TextView) findViewById(R.id.rssi_text);
        mapView.loadMap(AssetsHelper.getContent(this, "ceshi.svg"));
        final SVGMapLocationOverlay locationOverlay = new SVGMapLocationOverlay(mapView);
        final SVGMapLocationOverlay locationOverlay1 = new SVGMapLocationOverlay(mapView);
        try {
            svgSSGUtil = new SvgSSGUtil(this);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        locationOverlay.setIndicatorArrowBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.indicator_arrow));
        locationOverlay.setPosition(new PointF(221,y));
        locationOverlay.setIndicatorArrowRotateDegree(180);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                    svgSSGUtil.loginIn();
            }
        },2000);

        locationOverlay.setMode(SVGMapLocationOverlay.MODE_COMPASS);



        locationOverlay1.setPosition(new PointF(x,170));
        locationOverlay1.setIndicatorArrowBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.indicator_arrow));
        locationOverlay1.setIndicatorArrowRotateDegree(degree);
        locationOverlay1.setMode(SVGMapLocationOverlay.MODE_COMPASS);
//        locationOverlay.setIndicatorArrowRotateDegree(180);
        mapView.getOverLays().add(locationOverlay);
        mapView.getOverLays().add(locationOverlay1);

        mapView.refresh();
        final Handler handler = new Handler(){
             public void handleMessage(Message msg) {
                         switch (msg.what) {
                                case 1:

                                    degree+=5;

                                    int x1 =(int) (220+152*Math.random());
                                    int y1 =(int) (88+100*Math.random());

                                    int x2 =(int) (220+152*Math.random());
                                    int y2 =(int) (88+100*Math.random());

                                    locationOverlay.setPosition(new PointF(x1,y1));
                                    locationOverlay1.setPosition(new PointF(x2,y2));
                                    locationOverlay1.setIndicatorArrowRotateDegree(degree);
                                    mapView.getOverLays().add(locationOverlay);
                                    mapView.getOverLays().add(locationOverlay1);
                                    mapView.refresh();
                                        break;
                                case 2:
                                    Session session = svgSSGUtil.getSession();
                                    svgSSGUtil.exexCommmad(session,"iwinfo wlan0 scan",textView);

                                        break;
                                }
                            super.handleMessage(msg);
                        }
                 };
        Timer timer = new Timer();
        Timer timer1 = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);

            }
        };
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {

                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);

            }
        };
        timer.schedule(task,0,1000);
        timer1.schedule(task1,5000,3000);

    }
}
