package com.zhoubing.bishe.fragment.jiemian;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;
import com.zhoubing.bishe.MoshiFour;
import com.zhoubing.bishe.R;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by dell on 2018/2/26.
 */

public class MapFragment extends Fragment  implements LocationSource,AMapLocationListener{

    AMapLocationClient mLocationClient = null;//声明定位回调监听器
    MyLocationStyle myLocationStyle;
    MapView mapView = null;
    OnLocationChangedListener mListener;
    AMapLocationClientOption option;
    AMap aMap;
    CircleOptions circleOptions;
    MoshiFour moshiFour;
    Marker marker;
    MarkerOptions mark1;
    MarkerOptions mark2;
    MarkerOptions mark3;
    double lastDistance=0.0;
    double lastDistance1=0.0;
    boolean clear = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map,container,false);
        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mLocationClient=new AMapLocationClient(getActivity());
        mLocationClient.setLocationListener(this);//定位回调监听
        moshiFour = (MoshiFour) getActivity();
        init();
        return view;
    }

    public void init(){
        aMap=mapView.getMap();//获取地图对象
        aMap.showIndoorMap(true);
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(3000);
        myLocationStyle.showMyLocation(true);

        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);
        UiSettings settings=aMap.getUiSettings();//获取定位按钮
        aMap.setLocationSource(this);//设置定位监听
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
        settings.setMyLocationButtonEnabled(true);//显示定位按钮
        aMap.setMyLocationEnabled(true);//显示定位层并可触发定位
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(mListener!=null){
            if(aMapLocation!=null&&aMapLocation.getErrorCode()==0){

                Log.e("经度: ", String.valueOf(aMapLocation.getLatitude()));
                Log.e("纬度: ", String.valueOf(aMapLocation.getLongitude()));
                LatLng latLngshuju =new LatLng((moshiFour.dataFragment.latLng1.latitude-0.0022),(moshiFour.dataFragment.latLng1.longitude+0.0025));
                LatLng latLngshuju4 =new LatLng((moshiFour.dataFragment.latLng2.latitude-0.0022),(moshiFour.dataFragment.latLng2.longitude+0.0025));
                LatLng latLngshuju2 =new LatLng((moshiFour.dataFragment.latLng1.latitude-0.0021),(moshiFour.dataFragment.latLng1.longitude+0.0024));
                LatLng latLngshuju3 =new LatLng((moshiFour.dataFragment.latLng1.latitude-0.0023),(moshiFour.dataFragment.latLng1.longitude+0.0026));
                LatLng latLngshuju1 =new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
                double distance = AMapUtils.calculateLineDistance(latLngshuju,latLngshuju1);
                double distance1 = AMapUtils.calculateLineDistance(latLngshuju4,latLngshuju1);
                Log.e("距1离差距",""+AMapUtils.calculateLineDistance(latLngshuju,latLngshuju1));
                if((Math.abs((latLngshuju.latitude-latLngshuju1.latitude))<0.01||Math.abs((latLngshuju.longitude-latLngshuju1.longitude))<0.01)&&AMapUtils.calculateLineDistance(latLngshuju,latLngshuju1)<50){
                    if (lastDistance == 0.0 ) {
                        lastDistance = distance;
                        mark1 = new MarkerOptions().position(latLngshuju).title("设备测试")
                                .snippet("移动端1").visible(true);
                        mark1.setFlat(true);
                        mark2 = new MarkerOptions().position(latLngshuju4).title("多设备测试").snippet("移动端2").visible(true);
                        mark3 = new MarkerOptions().position(latLngshuju3).title("设备测试").snippet("车载车辆").visible(true);

                        marker  = aMap.addMarker(mark1);
                        aMap.addMarker(mark2);
                        Log.e("距离",""+AMapUtils.calculateLineDistance(latLngshuju,latLngshuju1));
                    }else if(lastDistance >=distance){
                        lastDistance = distance;
                        if(marker.isVisible()){
                            marker.remove();
                        }
                        mark1 = new MarkerOptions().position(latLngshuju)
                                .title("设备测试").snippet("移动端1").visible(true);
                        mark2 = new MarkerOptions().position(latLngshuju4).title("多设备测试").snippet("移动端2").visible(true);

//

                        marker = aMap.addMarker(mark1);
                        aMap.addMarker(mark2);


                        Log.e("距离",""+AMapUtils.calculateLineDistance(latLngshuju,latLngshuju1));
                    }
                    Log.e("定位信息：","zhoubing1 GPRMC  "+(moshiFour.dataFragment.latLng1.latitude-aMapLocation.getLatitude())+"    "+ (moshiFour.dataFragment.latLng1.longitude-aMapLocation.getLongitude()));
                    Log.e("纬度偏差"," "+(moshiFour.dataFragment.latLng1.latitude-aMapLocation.getLatitude()));
                    Log.e("经度偏差"," "+(moshiFour.dataFragment.latLng1.longitude-aMapLocation.getLongitude()));
                }
//                if((Math.abs((latLngshuju4.latitude-latLngshuju1.latitude))<0.01||Math.abs((latLngshuju4.longitude-latLngshuju1.longitude))<0.01)&&AMapUtils.calculateLineDistance(latLngshuju4,latLngshuju1)<50){
//                    if (lastDistance1 == 0.0 ) {
//                        lastDistance1 = distance1;
//
//                        mark2 = new MarkerOptions().position(latLngshuju2).title("设备测试").snippet("移动端2").visible(true);
//                        mark2.setFlat(true);
//
//                        marker  = aMap.addMarker(mark2);
//                        Log.e("距离",""+AMapUtils.calculateLineDistance(latLngshuju,latLngshuju1));
//                    }else if(lastDistance1 >=distance1){
//                        lastDistance1 = distance1;
//                        if(marker.isVisible()){
//                            marker.remove();
//                        }
//
//                        mark2 = new MarkerOptions().position(latLngshuju2).title("多设备测试").snippet("移动端2").visible(true);
//
//
//                        marker = aMap.addMarker(mark2);
//
//
//                        Log.e("距离",""+AMapUtils.calculateLineDistance(latLngshuju,latLngshuju1));
//                    }
//                    Log.e("定位信息：","zhoubing1 GPRMC  "+(moshiFour.dataFragment.latLng1.latitude-aMapLocation.getLatitude())+"    "+ (moshiFour.dataFragment.latLng1.longitude-aMapLocation.getLongitude()));
//                    Log.e("纬度偏差"," "+(moshiFour.dataFragment.latLng1.latitude-aMapLocation.getLatitude()));
//                    Log.e("经度偏差"," "+(moshiFour.dataFragment.latLng1.longitude-aMapLocation.getLongitude()));
//                }

                if(circleOptions == null){
                    circleOptions = new CircleOptions();
                    circleOptions.center(new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude()));
                    circleOptions.radius(200);
                    aMap.addCircle(circleOptions);
                }
                mListener.onLocationChanged(aMapLocation);
            }
            else Log.e("定位结果","定位失败");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener=onLocationChangedListener;


        option=new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//高进度定位
        option.setNeedAddress(true);
        mLocationClient.setLocationOption(option);//加载定位参数
        mLocationClient.startLocation();//开始定位
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if(mLocationClient!=null){
            mLocationClient.onDestroy();
        }
    }
}
