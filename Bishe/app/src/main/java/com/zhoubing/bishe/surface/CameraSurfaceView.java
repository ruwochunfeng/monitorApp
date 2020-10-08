package com.zhoubing.bishe.surface;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.view.SurfaceHolder.Callback;
import android.content.Context;  
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;  
import android.graphics.Color;
import android.graphics.Paint;  
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.Animation;

public class CameraSurfaceView extends SurfaceView implements Callback, Runnable{
   
	private SurfaceHolder sfh;    
    private Thread th;    
    private Canvas canvas;    
    private Paint paint;    
    private int ScreenW, ScreenH; 
    URL videoUrl;
    private String url;
    HttpURLConnection conn;
    Bitmap bmp;
    
    public CameraSurfaceView(Context context) {    
    	super(context);   
        th = new Thread(this);   
        sfh = this.getHolder();   
        sfh.addCallback(this);
        paint = new Paint();   
        paint.setAntiAlias(true);   
        paint.setColor(Color.RED);   
        this.setKeepScreenOn(true);
        
    }    
    @Override    
    public void startAnimation(Animation animation) {    
        super.startAnimation(animation);    
    }    
    public void surfaceCreated(SurfaceHolder holder) {    
        ScreenW = this.getWidth();// ��ȡ��Ļ���    
        ScreenH = this.getHeight();    
        th.start();    
    }    
    private void draw() {    
        try {    
             InputStream inputstream;
             inputstream = null;
                   videoUrl=new URL(url);    
             conn = (HttpURLConnection)videoUrl.openConnection();
                 conn.setDoInput(true);
                  conn.connect();
               inputstream = conn.getInputStream(); 
               bmp = BitmapFactory.decodeStream(inputstream);
            canvas = sfh.lockCanvas(); 
            canvas.drawColor(Color.WHITE);   
            canvas.drawBitmap(bmp, 0, 0, paint);
            sfh.unlockCanvasAndPost(canvas); 
            conn.disconnect();
        } catch (Exception ex) {    
        } finally {   
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }    
    }     
    public void run() {    
        while (true) {    
            draw();    
         
        }    
    }    
    public void surfaceChanged(SurfaceHolder holder, int format, int width,    
            int height) {    
    }    
    public void surfaceDestroyed(SurfaceHolder holder) {    
        // TODO Auto-generated method stub    
    }   
    public void GetCameraIP(String p){url=p;}
}    
