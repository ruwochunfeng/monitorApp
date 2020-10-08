package com.zhoubing.bishe;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zhoubing.bishe.util.AndroidDiuBaoUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PingActivity extends AppCompatActivity {

    private LineChart mChart;
    Thread thread;
    Button button;
    int[] mColors = ColorTemplate.VORDIPLOM_COLORS;
    ScrollView scrollView ;
    TextView textView;
    TextView textView_diubao;
    TextView textView_time;
    Process p;
    EditText editText_mubia,editText_changdu,editText_jiedian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping);
        mChart = (LineChart)findViewById(R.id.ping);
        button = (Button) findViewById(R.id.ssssss);
        scrollView = (ScrollView) findViewById(R.id.scrollview_ping);
        textView = (TextView) findViewById(R.id.receiver_ping);
        textView_diubao = (TextView) findViewById(R.id.diubao);
        textView_time = (TextView) findViewById(R.id.sjo);
        editText_mubia = (EditText) findViewById(R.id.mubiao_shezhi);
        editText_changdu = (EditText) findViewById(R.id.changdu_ceshi);
        editText_jiedian = (EditText) findViewById(R.id.jiedian_ceshi);
        initChart();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread =  new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String mubiao  = editText_mubia.getText().toString();
                            String changdu = editText_changdu.getText().toString();
                            String jiedian = editText_jiedian.getText().toString();
                            pingDiuBao("192.168.1."+mubiao,PingActivity.this,changdu,jiedian);
//                            pingDiuBao("101.206.170.217",PingActivity.this,changdu,jiedian);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });

    }

    public  void pingDiuBao(String ip, Context context,String length,String jiedian) throws IOException {

        String lost = new String();
        String delay = new String();
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                textView.setText("");
//                textView_diubao.setText("丢包率：--");
//                textView_time.setText("平均延时：--");
//            }
//        });
        p = Runtime.getRuntime().exec("ping -c "+jiedian+" -s "+ length+ " " + ip);
        BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String str = new String();
        while((str=buf.readLine())!=null){
            Log.e("丢包延时",""+str);
            final String message = str;
            if(str.contains("time")){
                int index = str.indexOf("time");
                String sub = str.substring(index+5);
                String[] shuju= sub.split(" ");
                if(shuju.length==1){
                   final String[] diubaoshuju = str.split(",");
                    if(diubaoshuju.length>2){
                        String quchu = diubaoshuju[2].substring(1);
                        final String[] quchushuju = quchu.split(" ");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(message);
                                textView_diubao.setText("丢包率："+quchushuju[0]);
                            }
                        });
                    }


                }else{
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            textView.append(message+"\n");
//                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//                        }
//                    });
                    addEntryfloat(Double.valueOf(shuju[0]));
                }



            }else if(str.contains("rtt")){
                String shijian = str.substring(str.indexOf("=")+1);
                final String[] shijianshuju = shijian.split("/");


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                       textView_time.setText("平均延时："+shijianshuju[1]+"ms");
                    }
                });

            }

        }


    }

    public void initChart(){
        //  mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);
        Description description = new Description();
        description.setTextAlign(Paint.Align.CENTER);
        description.setText("延时数据表");
        description.setTextColor(Color.WHITE);
        description.setTextSize(15);
        Drawable  drawable =mChart.getBackground();

        mChart.setDescription(description);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextColor(Color.WHITE);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(5,false); //设置有多少个间隔
        // add an empty data object
        mChart.setData(new LineData());
        YAxis y = mChart.getAxisRight();
        ColorDrawable colorDrawable = (ColorDrawable) drawable;


        YAxis y_left = mChart.getAxisLeft();
        y_left.setTextColor(Color.WHITE);
        y.setTextColor(colorDrawable.getColor());
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


        Entry entry1 = new Entry(set.getEntryCount(), (float) d1);
        data.addEntry(entry1,0);
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

        LineDataSet set = new LineDataSet(null, "延时（ms）");
        set.setValueTextColor(Color.WHITE);


        set.setValueTextSize(10f);
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(Color.GREEN);

        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);//设置曲线or折线
        set.setCircleColor(Color.rgb(240, 99, 99));
        set.setHighLightColor(Color.rgb(190, 190, 190));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        return set;
    }
}
