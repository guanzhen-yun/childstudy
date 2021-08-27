package com.inke.childstudy.userinfo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.inke.childstudy.R;
import com.inke.childstudy.routers.RouterConstants;
import com.ziroom.base.BaseActivity;

import butterknife.BindView;

/**
 * 查看大图页
 */
@Route(path = RouterConstants.App.BigHead)
public class BigHeadActivity extends BaseActivity {
    @BindView(R.id.iv_pic)
    ImageView mIvPic;

    @Autowired(name = "imagePath")
    String imagePath;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bighead;
    }

    @Override
    public void initViews() {
        displayImage(imagePath);

    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mIvPic.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "获取相册图片失败", Toast.LENGTH_SHORT).show();
        }
    }
}
