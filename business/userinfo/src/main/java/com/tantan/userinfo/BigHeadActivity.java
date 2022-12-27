package com.tantan.userinfo;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.tantan.base.RouterConstants.UserInfo;
import com.tantan.userinfo.utils.PhotoUtils;
import com.ziroom.base.BaseActivity;

/**
 * 查看大图页
 */
@Route(path = UserInfo.BigHead)
public class BigHeadActivity extends BaseActivity implements OnClickListener {

  private ImageView mIvPic;

  @Autowired(name = "imagePath")
  String imagePath;

  @Override
  public int getLayoutId() {
    return R.layout.activity_bighead;
  }

  @Override
  public void initViews() {
    mIvPic = findViewById(R.id.iv_pic);
    displayImage(imagePath);
    findViewById(R.id.view_left).setOnClickListener(this);
  }

  private void displayImage(String imagePath) {
    if (!TextUtils.isEmpty(imagePath)) {
      Thread thread = new Thread(() -> {
        Message message = new Message();
        message.obj = PhotoUtils.loadCompletePic(imagePath);
        handler.sendMessage(message);
      });
      thread.start();
    } else {
      Toast.makeText(this, "获取相册图片失败", Toast.LENGTH_SHORT).show();
    }
  }

  Handler handler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(@NonNull Message msg) {
      super.handleMessage(msg);
      mIvPic.setImageBitmap((Bitmap) msg.obj);
    }
  };

  @Override
  public void onClick(View view) {
    finish();
  }
}
