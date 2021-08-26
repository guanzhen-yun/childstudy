package com.inke.childstudy.userinfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.entity.UserInfo;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.BitmapUtils;
import com.inke.childstudy.utils.BmobUtils;
import com.inke.childstudy.utils.SecreteUtils;
import com.inke.childstudy.utils.SharedPrefUtils;
import com.inke.childstudy.utils.ToastUtils;
import com.inke.childstudy.view.pop.MyPopView;
import com.ziroom.base.BaseActivity;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 个人信息页
 */

@Route(path = RouterConstants.App.UserInfo)
public class UserInfoActivity extends BaseActivity {
    private File file;

    private Uri ImgUri;

    private Type type;

    private MyPopView puWindow;

    @BindView(R.id.iv_head)
    ImageView mIvHead;

    private String mObjectId;

    public enum Type {
        PHONE, CAMERA
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_userinfo;
    }

    @Override
    public void initDatas() {
        BmobQuery<UserInfo> bmobQuery = new BmobQuery<>();
        String loginToken = SharedPrefUtils.getInstance().getLoginToken();
        bmobQuery.addWhereEqualTo("token", loginToken);
        bmobQuery.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> list, BmobException e) {
                if (e != null) {
                    if (e.getErrorCode() == 101) {
                        UserInfo info = new UserInfo();
                        info.setToken(loginToken);
                        info.setBitmap(null);
                        info.setSex("");
                        BmobUtils.getInstance().addData(info, new BmobUtils.OnBmobListener() {
                            @Override
                            public void onSuccess(String objectId) {
                                mObjectId = objectId;
                            }

                            @Override
                            public void onError(String err) {
                                ToastUtils.showToast(err);
                            }
                        });
                    } else {
                        ToastUtils.showToast(e.getMessage());
                    }
                } else if (list != null && list.size() > 0) {
                    UserInfo userInfo = list.get(0);
                    mObjectId = userInfo.getObjectId();
                    if (userInfo.getBitmap() != null) {
                        mIvHead.setImageBitmap(userInfo.getBitmap());
                    }
                } else {
                    UserInfo info = new UserInfo();
                    info.setToken(loginToken);
                    info.setBitmap(null);
                    info.setSex("");
                    BmobUtils.getInstance().addData(info, new BmobUtils.OnBmobListener() {
                        @Override
                        public void onSuccess(String objectId) {
                            mObjectId = objectId;
                        }

                        @Override
                        public void onError(String err) {
                            ToastUtils.showToast(err);
                        }
                    });
                }
            }
        });
    }

    @OnClick({R.id.tv_addphoto})
    public void onClickView(View v) {
        switch (v.getId()) {
            case R.id.tv_addphoto:
                addPhoto();
                break;
            default:
                break;
        }
    }

    private void addPhoto() {
        puWindow = new MyPopView(UserInfoActivity.this);
        puWindow.showPopupWindow(findViewById(R.id.rl_body));
        puWindow.setOnGetTypeClckListener(new MyPopView.onGetTypeClckListener() {

            @Override
            public void getType(Type type) {
                UserInfoActivity.this.type = type;
            }

            @Override
            public void getImgUri(Uri ImgUri, File file) {
                UserInfoActivity.this.ImgUri = ImgUri;
                UserInfoActivity.this.file = file;
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (ImgUri != null) {
                puWindow.onPhotoNew(ImgUri);
            }
        } else if (requestCode == 2) {
            if (data != null) {
                Uri uri = data.getData();
                puWindow.onPhoto(uri, 300, 300);
            }
        } else if (requestCode == 3) {
            if (type == Type.PHONE) {
                if (data != null) {
                    Bundle extras = data.getExtras();
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    if (bitmap != null) {
                        changeHeadPhoto(bitmap);
                    }
                }
            } else if (type == Type.CAMERA) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                changeHeadPhoto(bitmap);
            }
        }
    }

    private void changeHeadPhoto(Bitmap bitmap) {
        UserInfo userInfo = new UserInfo();
        userInfo.setBitmap(bitmap);
        BmobUtils.getInstance().updateBmobDate(mObjectId, userInfo, new BmobUtils.OnBmobListener() {

            @Override
            public void onSuccess(String objectId) {
                mIvHead.setImageBitmap(bitmap);
            }

            @Override
            public void onError(String err) {
                ToastUtils.showToast(err + "修改失败");
            }
        });
    }
}
