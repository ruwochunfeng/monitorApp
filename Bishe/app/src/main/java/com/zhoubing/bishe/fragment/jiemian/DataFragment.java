package com.zhoubing.bishe.fragment.jiemian;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.Service;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.location.DPoint;

import com.amap.api.maps.model.LatLng;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zhoubing.bishe.MoshiFour;
import com.zhoubing.bishe.R;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.amap.api.maps.CoordinateConverter;
/**
 * Created by dell on 2018/2/26.
 */

public class DataFragment extends Fragment {

    public TextView tv1;
    public Button button,button_clear,button_tubiao,button_tianjia;
    double weidu = 0.0;
    double jingdu = 0.0;
    double weidu1 = 0.0;
    double jingdu1 = 0.0;
    SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
    private LineChart mChart;

    MoshiFour moshiFour;
    ScrollView scrollView;
    Date curDate;
    String str;
    Thread thread;
    Thread thread1;
    com.amap.api.maps.CoordinateConverter converter  = new com.amap.api.maps.CoordinateConverter(getActivity());
    public LatLng latLng1;
    public LatLng latLng2;
    boolean start= true;
    boolean always= true;
    double resultlng =0.0;
    double resultlat =0.0;
    double resultlng1 =0.0;
    double resultlat1 =0.0;
    int[] mColors = ColorTemplate.VORDIPLOM_COLORS;
    boolean xianshi = false;
    boolean tubiao_start = true;
     Handler handler;
    Timer timer;
    String jindu_xianshi,weidu_xianshi;
    String jindu_xianshi1,weidu_xianshi1;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.data,container,false);
        tv1= (TextView) view.findViewById(R.id.receiver);
        button = (Button) view.findViewById(R.id.button_begin);
        button_clear = (Button) view.findViewById(R.id.button_clera);
        button_tubiao = (Button) view.findViewById(R.id.button_tubiao);
        button_tianjia = (Button) view.findViewById(R.id.button_dongtai);
        scrollView = (ScrollView) view.findViewById(R.id.scrollview);
        mChart = (LineChart) view.findViewById(R.id.chart);


//
//         handler= new Handler( ) {
//
//            public void handleMessage(Message msg) {
//
//                switch (msg.what) {
//
//                    case 1:
//                       addEntry();
//
//                }
//
//                super.handleMessage(msg);
//
//            }
//
//        };
        moshiFour = (MoshiFour) getActivity();
        thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                while (always) {
                    // 选择一组可以进行I/O操作的事件，放在selector中,客户端的该方法不会阻塞，
                    //这里和服务端的方法不一样，查看api注释可以知道，当至少一个通道被选中时，
                    //selector的wakeup方法被调用，方法返回，而对于客户端来说，通道一直是被选中的
                    try {
                        moshiFour.selector.select();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Iterator ite = moshiFour.selector.selectedKeys().iterator();
                    while (ite.hasNext()) {
                        SelectionKey key = (SelectionKey) ite.next();
                        // 删除已选的key,以防重复处理
                        ite.remove();
                        // 连接事件发生
                        if (key.isConnectable()) {
                            SocketChannel channel = (SocketChannel) key
                                    .channel();
                            // 如果正在连接，则完成连接
                            try {
                                if (channel.isConnectionPending()) {
                                    channel.finishConnect();
                                }
                                channel.configureBlocking(false);
                                channel.register(moshiFour.selector, SelectionKey.OP_READ);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            // 获得了可读的事件
                        } else if (key.isReadable()) {
                            try {
                                read(key);

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }
        });


        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                synchronized (this){
                    always=false;
                    tv1.setText("");
                }

            }
        });
        button_tianjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(tubiao_start){
//                    timer = new Timer();
//                    TimerTask task = new TimerTask() {
//                        @Override
//                        public void run() {
//                            Message message = new Message( );
//
//                            message.what = 1;
//
//                            handler.sendMessage(message);
//                        }
//                    };
//                    timer.schedule(task,0,500);
//                    tubiao_start = false;
//
//                }else{
//
//                    tubiao_start = true;
//                    timer.cancel();
//                }


            }


        });


        button_tubiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!xianshi){
                    xianshi = true;
                    mChart.setVisibility(View.VISIBLE);
                }else {
                    xianshi = false;
                    mChart.setVisibility(View.INVISIBLE);
                }

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        try {
                        synchronized (this){
                            if(start){
                                start = false;
                                always = true;
                                thread =  new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        while (always) {
                                            // 选择一组可以进行I/O操作的事件，放在selector中,客户端的该方法不会阻塞，
                                            //这里和服务端的方法不一样，查看api注释可以知道，当至少一个通道被选中时，
                                            //selector的wakeup方法被调用，方法返回，而对于客户端来说，通道一直是被选中的
                                            try {
                                                moshiFour.selector.select();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            Iterator ite = moshiFour.selector.selectedKeys().iterator();
                                            while (ite.hasNext()) {
                                                SelectionKey key = (SelectionKey) ite.next();
                                                // 删除已选的key,以防重复处理
                                                ite.remove();
                                                // 连接事件发生
                                                if (key.isConnectable()) {
                                                    SocketChannel channel = (SocketChannel) key
                                                            .channel();
                                                    // 如果正在连接，则完成连接
                                                    try {
                                                        if (channel.isConnectionPending()) {
                                                            channel.finishConnect();
                                                        }
                                                        channel.configureBlocking(false);
                                                        channel.register(moshiFour.selector, SelectionKey.OP_READ);
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }

                                                    // 获得了可读的事件
                                                } else if (key.isReadable()) {
                                                    try {
                                                        read(key);

                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }
                                        }
                                    }
                                });

                                thread.start();
                            }else{
                                start = true;
                                always = false;
                                thread.join();
                            }
                          }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
        initChart();
        initThread();

        return view;
    }

    public void initThread(){
        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {

                    // 选择一组可以进行I/O操作的事件，放在selector中,客户端的该方法不会阻塞，
                    //这里和服务端的方法不一样，查看api注释可以知道，当至少一个通道被选中时，
                    //selector的wakeup方法被调用，方法返回，而对于客户端来说，通道一直是被选中的
                    try {
                        moshiFour.selector1.select();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Iterator ite = moshiFour.selector1.selectedKeys().iterator();
                    while (ite.hasNext()) {
                        SelectionKey key = (SelectionKey) ite.next();
                        // 删除已选的key,以防重复处理
                        ite.remove();
                        // 连接事件发生
                        if (key.isConnectable()) {
                            SocketChannel channel = (SocketChannel) key
                                    .channel();
                            // 如果正在连接，则完成连接
                            try {
                                if (channel.isConnectionPending()) {
                                    channel.finishConnect();
                                }
                                channel.configureBlocking(false);
                                channel.register(moshiFour.selector1, SelectionKey.OP_READ);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            // 获得了可读的事件
                        } else if (key.isReadable()) {
                            try {
                                read1(key);

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }
        });
        thread1.start();
    }



    public void read1(SelectionKey key) throws Exception {
        // 服务器可读取消息:得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        // 创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        channel.read(buffer);
        byte[] data = buffer.array();
        final String msg = new String(data).trim();
        Log.e("定位数据：","计算"+msg);
        parseGPSRMCfromLatLng1(msg);
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
        moshiFour.chanel1.write(outBuffer);// 将消息回送给客户端
    }
    public void read(SelectionKey key) throws Exception {
        // 服务器可读取消息:得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        // 创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        channel.read(buffer);
        byte[] data = buffer.array();
        final String msg = new String(data).trim();
        Log.e("定位数据：","测试"+msg);
        parseGPSRMCfromLatLng(msg);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                curDate = new Date(System.currentTimeMillis());//获取当前时间
                str = formatter.format(curDate);
                if(jindu_xianshi!=null&&weidu_xianshi!=null){


                    addEntry(Double.valueOf(jindu_xianshi),Double.valueOf(weidu_xianshi));

                }
                tv1.append(str+": "+"经度："+jindu_xianshi+" "+"纬度："+weidu_xianshi+"\n");

                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
        moshiFour.chanel.write(outBuffer);// 将消息回送给客户端
    }


    public LatLng parseGPSGGAfromLatLng(String shuju) throws Exception {

        String[] array= shuju.split(",| ");
        int index =0;
        if(shuju.contains("$GPGGA")){
                for(int i =0 ;i<array.length;i++){

                    if("$GPGGA".equals(array[i])){

                        index = i;
                    }

                }
                if(isDouble(array[index+2]) && isDouble(array[index+4])  ){

                    weidu = Double.valueOf(array[index+2]);
                    jingdu = Double.valueOf(array[index+4]);
                    resultlng = (int)(weidu/100) + (weidu/100.0 - (int)(weidu/100)) *100.0 / 60.0;
                    resultlat = (int)(jingdu/100) + (jingdu/100.0 - (int)(jingdu/100)) *100.0 / 60.0;

                    latLng1 = new LatLng(resultlng,resultlat);
                    addEntryfloat(resultlat);
                    Log.e("定位信息：","zhoubing RMC  "+latLng1.longitude+"   "+latLng1.latitude);

                }
            }
        return  latLng1;
    }
    public LatLng parseGPSRMCfromLatLng(String shuju) throws Exception {

        String[] array= shuju.split(",| ");
        int index =0;
        if(shuju.contains("$GPRMC")){
            for(int i =0 ;i<array.length;i++){

                if("$GPRMC".equals(array[i])){

                    index = i;
                }

            }
            if(array.length<5){

                return  null;
            }
            if(isDouble(array[index+3]) && isDouble(array[index+5])  ){

                weidu = Double.valueOf(array[index+3]);
                jingdu = Double.valueOf(array[index+5]);
                resultlng = (int)(weidu/100) + (weidu/100.0 - (int)(weidu/100)) *100.0 / 60.0;
                resultlat = (int)(jingdu/100) + (jingdu/100.0 - (int)(jingdu/100)) *100.0 / 60.0;
                latLng1 = new LatLng(resultlng,resultlat);
                jindu_xianshi = String.valueOf(resultlat);
                weidu_xianshi = String.valueOf(resultlng);

                Log.e("定位信息：","zhoubing RMC  "+latLng1.longitude+"   "+latLng1.latitude);
            }
        }
        return  latLng1;
    }
    public LatLng parseGPSRMCfromLatLng1(String shuju) throws Exception {

        String[] array= shuju.split(",| ");
        int index =0;
        if(shuju.contains("$GPRMC")){
            for(int i =0 ;i<array.length;i++){

                if("$GPRMC".equals(array[i])){

                    index = i;
                }

            }
            if(array.length<5){

                return  null;
            }
            if(isDouble(array[index+3]) && isDouble(array[index+5])  ){

                weidu1 = Double.valueOf(array[index+3]);
                jingdu1 = Double.valueOf(array[index+5]);
                resultlng1 = (int)(weidu1/100) + (weidu1/100.0 - (int)(weidu1/100)) *100.0 / 60.0;
                resultlat1 = (int)(jingdu1/100) + (jingdu1/100.0 - (int)(jingdu1/100)) *100.0 / 60.0;
                latLng2 = new LatLng(resultlng1,resultlat1);
                jindu_xianshi1 = String.valueOf(resultlat1);
                weidu_xianshi1 = String.valueOf(resultlng1);

                Log.e("定位信息2：","zhoubing RMC  "+latLng2.longitude+"   "+latLng2.latitude);
            }
        }
        return  latLng2;
    }


    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        start = true;
        always = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    public void initChart(){
      //  mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);
        Description description = new Description();
        description.setTextAlign(Paint.Align.CENTER);
        description.setText("地理数据");
        mChart.setDescription(description);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setLabelCount(5,false); //设置有多少个间隔
        // add an empty data object
        mChart.setData(new LineData());
        YAxis y = mChart.getAxisRight();
        y.setTextColor(Color.WHITE);
        mChart.invalidate();


    }



    private void removeLastEntry() {

        LineData data = mChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);

            if (set != null) {

                Entry e = set.getEntryForXValue(set.getEntryCount() - 1, Float.NaN);

                data.removeEntry(e, 0);
                // or remove by index
                // mData.removeEntryByXValue(xIndex, dataSetIndex);
                data.notifyDataChanged();
                mChart.notifyDataSetChanged();
                mChart.invalidate();
            }
        }
    }


    private void addEntry(double d1,double d2) {

        LineData data = mChart.getData();


        ILineDataSet set = data.getDataSetByIndex(0);
        ILineDataSet set1 = data.getDataSetByIndex(1);

        // set.addEntry(...); // can be called as well

        if (set == null) {
            set = createSet();
            data.addDataSet(set);
        }

        if (set1 == null) {
            set1 = createSet1();
            data.addDataSet(set1);
        }
        Entry entry1 = new Entry(set.getEntryCount(), (float) d1);
        Entry entry2 = new Entry(set1.getEntryCount(), (float) d2);
        data.addEntry(entry1,0);
        data.addEntry(entry2,1);
        // choose a random dataSet
        data.notifyDataChanged();

        // let the chart know it's data has changed
        mChart.notifyDataSetChanged();

        mChart.setVisibleXRangeMaximum(14);
        //mChart.setVisibleYRangeMaximum(15, AxisDependency.LEFT);
//
//            // this automatically refreshes the chart (calls invalidate())
        Object pendency;
        mChart.moveViewTo(data.getEntryCount() - 15, 50f, YAxis.AxisDependency.LEFT);

    }



    private void addEntryfloat(double f) {

        LineData data = mChart.getData();

        ILineDataSet set = data.getDataSetByIndex(0);


        // set.addEntry(...); // can be called as well

        if (set == null) {
            set = createSet();
            data.addDataSet(set);
        }





        // choose a random dataSet
        int randomDataSetIndex = (int) (Math.random() * data.getDataSetCount());
        float yValue = (float) (Math.random() * 10) + 50f;

        data.addEntry(new Entry(set.getEntryCount(), (float) f), 0);

        data.notifyDataChanged();

        // let the chart know it's data has changed
        mChart.notifyDataSetChanged();

        mChart.setVisibleXRangeMaximum(14);
        //mChart.setVisibleYRangeMaximum(15, AxisDependency.LEFT);
//
//            // this automatically refreshes the chart (calls invalidate())
        Object pendency;
        mChart.moveViewTo(data.getEntryCount() - 15, 0f, YAxis.AxisDependency.LEFT);

    }


    private void addDataSet() {

        LineData data = mChart.getData();

        if (data != null) {

            int count = (data.getDataSetCount() + 1);

            ArrayList<Entry> yVals = new ArrayList<Entry>();

            for (int i = 0; i < data.getEntryCount(); i++) {
                yVals.add(new Entry(i, (float) (Math.random() * 50f) + 50f * count));
            }

            LineDataSet set = new LineDataSet(yVals, "DataSet " + count);
            set.setLineWidth(2.5f);
            set.setCircleRadius(4.5f);

            int color = mColors[count % mColors.length];

            set.setColor(color);
            set.setCircleColor(color);
            set.setHighLightColor(color);
            set.setValueTextSize(10f);
            set.setValueTextColor(color);

            data.addDataSet(set);
            data.notifyDataChanged();
            mChart.notifyDataSetChanged();
            mChart.invalidate();
        }
    }

    private void removeDataSet() {

        LineData data = mChart.getData();

        if (data != null) {

            data.removeDataSet(data.getDataSetByIndex(data.getDataSetCount() - 1));

            mChart.notifyDataSetChanged();
            mChart.invalidate();
        }
    }




    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "经度");
        set.setValueTextSize(0f);
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(Color.rgb(240, 99, 99));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);//设置曲线or折线
        set.setCircleColor(Color.rgb(240, 99, 99));
        set.setHighLightColor(Color.rgb(190, 190, 190));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);


        return set;
    }
    private LineDataSet createSet1() {

        LineDataSet set = new LineDataSet(null, "纬度");
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColors(Color.BLUE);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);//设置曲线or折线
        set.setCircleColor(Color.BLACK);
        set.setHighLightColor(Color.GREEN);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setValueTextSize(0f);

        return set;
    }
}
