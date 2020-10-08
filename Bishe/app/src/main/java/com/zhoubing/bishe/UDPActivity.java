package com.zhoubing.bishe;

import android.app.Activity;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xugaoxiang.vlcdemo.utils.ScreenUtils;
import com.zhoubing.bishe.Inerdoor.IndoorLocation;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;

public class UDPActivity extends Activity {
    private static final String TAG = UDPActivity.class.getSimpleName();
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private LibVLC libvlc = null;
    private MediaPlayer mediaPlayer = null;
    private IVLCVout ivlcVout;
    private Media media;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udp);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        editText = (EditText) findViewById(R.id.edit_udp);
        button = (Button) findViewById(R.id.udP_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPlayer();
            }
        });
        IndoorLocation indoorLocation = new IndoorLocation();
        WifiManager wifiManager= indoorLocation.init(this);
        Log.e("地址为：","测试："+indoorLocation.getWifiInfo(wifiManager).getRssi());

    }

    private void initPlayer() {
        ArrayList<String> options = new ArrayList<>();
        options.add("--network-caching=100");


//        options.add("--aout=opensles");
//        options.add("--audio-time-stretch");
//
//        options.add("--audio-resampler");
//        options.add("soxr");
//
//        options.add("--avcodec-skiploopfilter");
//        options.add("1");
//
//        options.add("--avcodec-skip-frame");
//        options.add("0");
//
//        options.add("--avcodec-skip-idct");
//        options.add("0");
//
//        options.add("--udp-timeout");
//        options.add("1");
//
//        // deinterlace and deinterlace-mode, see https://wiki.videolan.org/Deinterlacing/#VLC_deinterlace_modes
//        options.add("--deinterlace");
//        options.add("1");
//
//        options.add("--deinterlace-mode");
//        options.add("bob");
//
//        options.add("-vv");
        libvlc = new LibVLC(UDPActivity.this, options);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setKeepScreenOn(true);
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                int sw = getWindow().getDecorView().getWidth();
                int sh = getWindow().getDecorView().getHeight();


                mediaPlayer.getVLCVout().setWindowSize(sw, sh);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        mediaPlayer = new MediaPlayer(libvlc);
//
        media = new Media(libvlc, Uri.parse(editText.getText().toString()));
//        media = new Media(libvlc, Uri.parse("http://192.168.1.100:8080/?action=stream"));
        mediaPlayer.setMedia(media);

        ivlcVout = mediaPlayer.getVLCVout();
        ivlcVout.setVideoView(surfaceView);
        ivlcVout.attachViews();
        ivlcVout.addCallback(new IVLCVout.Callback() {
            @Override
            public void onSurfacesCreated(IVLCVout vlcVout) {

            }

            @Override
            public void onSurfacesDestroyed(IVLCVout vlcVout) {

            }
        });
        mediaPlayer.getVLCVout().setWindowSize(800,800);
        
        mediaPlayer.play();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_STAR:
                try {
                    ScreenUtils.takeScreenshot(getApplicationContext());
                } catch (Exception e) {
                    new Exception("TakeScreenshot error.").printStackTrace();
                }
                break;
        }

        return super.onKeyDown(keyCode, event);
    }
}
