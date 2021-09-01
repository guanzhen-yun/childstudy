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
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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
        mLocationClient.registerLocationListener(new MyLocationListener());
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
