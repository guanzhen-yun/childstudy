package com.inke.childstudy.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.inke.childstudy.entity.AddressTrace;
import com.inke.childstudy.utils.DateUtils;
import com.inke.childstudy.utils.ToastUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;


public class LocationService extends Service {
    public LocationClient mLocationClient;
    private StringBuilder currentPosition;
    private StringBuilder currentLat;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationClient = new LocationClient((getApplicationContext()));
        mLocationClient.registerLocationListener(new MylocationListener());
        initLocation();
        mLocationClient.start();
    }

    /*初始化函数*/
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    /*监听线程，获得当前的经纬度，并显示*/
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation location) {
            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation) {
                Message message = new Message();
                message.obj = location;
                handler.sendMessage(message);
            }
        }
    }

    /*监听线程，获得当前的经纬度，并显示*/
    public class MylocationListener extends BDAbstractLocationListener {
        //定位请求回调接口
        private boolean isFirstIn = true;

        //定位请求回调函数,这里面会得到定位信息
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //BDLocation 回调的百度坐标类，内部封装了如经纬度、半径等属性信息
            //MyLocationData 定位数据,定位数据建造器
            /**
             * 可以通过BDLocation配置如下参数
             * 1.accuracy 定位精度
             * 2.latitude 百度纬度坐标
             * 3.longitude 百度经度坐标
             * 4.satellitesNum GPS定位时卫星数目 getSatelliteNumber() gps定位结果时，获取gps锁定用的卫星数
             * 5.speed GPS定位时速度 getSpeed()获取速度，仅gps定位结果时有速度信息，单位公里/小时，默认值0.0f
             * 6.direction GPS定位时方向角度
             * */
            //配置定位图层显示方式,三个参数的构造器
            /**
             * 1.定位图层显示模式
             * 2.是否允许显示方向信息
             * 3.用户自定义定位图标
             * */
            //判断是否为第一次定位,是的话需要定位到用户当前位置
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                if (isFirstIn) {
                    //地理坐标基本数据结构
                    LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                    //描述地图状态将要发生的变化,通过当前经纬度来使地图显示到该位置
                    //改变地图状态
                    isFirstIn = false;
                    Message message = new Message();
                    message.obj = bdLocation;
                    handler.sendMessage(message);
                }
            }
        }
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            currentPosition = new StringBuilder();
            currentLat = new StringBuilder();
            BDLocation location = (BDLocation) msg.obj;
            currentLat.append("纬度:").append(location.getLatitude()).append("\n");
            currentLat.append("经度:").append(location.getLongitude()).append("\n");
            currentPosition.append("国家:").append(location.getCountry()).append("\n");
            currentPosition.append("省:").append(location.getProvince()).append("\n");
            currentPosition.append("市:").append(location.getCity()).append("\n");
            currentPosition.append("区:").append(location.getDistrict()).append("\n");
            currentPosition.append("街道:").append(location.getStreet()).append("\n");
            currentPosition.append("定位方式：");
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                currentPosition.append("GPS");
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                currentPosition.append("网络");
            }
            sendMsg();
        }
    };

    private void sendMsg() {
        String otherAccount = "18324602696";
        AddressTrace addressTrace = new AddressTrace();
        addressTrace.setAddress(currentPosition.toString());
        addressTrace.setTime(DateUtils.stampToDate(System.currentTimeMillis()));
        addressTrace.setLatlng(currentLat.toString());
        // 以单聊类型为例
        SessionTypeEnum sessionType = SessionTypeEnum.P2P;
        // 创建一个文本消息
        IMMessage textMessage = MessageBuilder.createCustomMessage(otherAccount, sessionType, addressTrace);
        // 发送给对方
        NIMClient.getService(MsgService.class).sendMessage(textMessage, false).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {

            }

            @Override
            public void onFailed(int code) {
                ToastUtils.showToast("发送失败");
            }

            @Override
            public void onException(Throwable exception) {
                ToastUtils.showToast(exception.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
