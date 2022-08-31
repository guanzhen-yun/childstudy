package com.inke.childstudy.set;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.entity.event.FinishHomeEvent;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.studyobject.StudyObjectActivity;
import com.tantan.mydata.utils.BmobUtils;
import com.inke.childstudy.utils.SharedPrefUtils;
import com.inke.childstudy.utils.ViewUtils;
import com.inke.childstudy.view.dialog.ExitDialog;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.tantan.base.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置
 */
@Route(path = RouterConstants.App.Set)
public class SetActivity extends BaseActivity {

  @BindView(R.id.tv_change)
  TextView mTvChange;
  @BindView(R.id.tv_im)
  TextView mTvIm;
  @BindView(R.id.tv_back)
  TextView mTvBack;
  @BindView(R.id.view_info)
  View mViewInfo;
  @BindView(R.id.tv_changeinfo)
  TextView mTvChangeinfo;
  @BindView(R.id.tv_color)
  TextView mTvColor;
  @BindView(R.id.view_color)
  View mViewColor;
  @BindView(R.id.tv_animal)
  TextView mTvAnimal;
  @BindView(R.id.view_animal)
  View mViewAnimal;
  @BindView(R.id.tv_word)
  TextView mTvWord;
  @BindView(R.id.view_word)
  View mViewWord;
  @BindView(R.id.tv_tool)
  TextView mTvTool;
  @BindView(R.id.view_tool)
  View mViewTool;
  @BindView(R.id.tv_fruit)
  TextView mTvFruit;
  @BindView(R.id.view_fruit)
  View mViewFruit;
  @BindView(R.id.tv_address)
  TextView mTvAddress;
  @BindView(R.id.view_address)
  View mViewAddress;
  @BindView(R.id.tv_address_toplay)
  TextView mTvAddressToplay;
  @BindView(R.id.view_address_toplay)
  View mViewAddressToplay;
  @BindView(R.id.tv_info)
  TextView mTvInfo;
  @BindView(R.id.view_useinfo)
  View mViewUseinfo;
  @BindView(R.id.tv_tostudy)
  TextView mTvTostudy;
  @BindView(R.id.view_study)
  View mViewStudy;

  private boolean isChange;

  @Override
  public int getLayoutId() {
    return R.layout.activity_set;
  }

  @Override
  public void initViews() {
    boolean isMother = SharedPrefUtils.getInstance().isMother();
    if (isMother) {
      ViewUtils.setViewsGone(mTvBack, mTvChange, mViewInfo, mTvChangeinfo, mTvColor, mViewColor,
          mTvAnimal, mViewAnimal, mTvWord, mViewWord, mTvTool, mViewTool, mTvFruit, mViewFruit,
          mTvAddressToplay, mViewAddressToplay);
      mTvIm.setText("和宝宝聊天");
      ViewUtils.setViewsVisible(mTvAddress, mViewAddress, mTvInfo, mViewUseinfo, mTvTostudy,
          mViewStudy);
    }
  }

  @OnClick({R.id.tv_loginout, R.id.tv_back, R.id.tv_changeinfo, R.id.tv_change, R.id.tv_color,
      R.id.tv_word,
      R.id.tv_animal, R.id.tv_tool, R.id.tv_fruit, R.id.tv_im, R.id.tv_address,
      R.id.tv_address_toplay, R.id.tv_info, R.id.tv_tostudy})
  public void onClickView(View v) {
    switch (v.getId()) {
      case R.id.tv_loginout:
        ExitDialog exitDialog = new ExitDialog(this);
        exitDialog.setOnExitListener(this::loginout);
        exitDialog.show();
        break;
      case R.id.tv_back:
        onBackPressed();
        break;
      case R.id.tv_changeinfo:
        RouterUtils.jump(RouterConstants.App.UserInfo);
        break;
      case R.id.tv_change:
        isChange = !isChange;
        if (isChange) {//该状态为修改
          mTvChange.setText("切换查看");
        } else {//该状态为查看
          mTvChange.setText("切换修改");
        }
        break;
      case R.id.tv_color:
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEdit", isChange);
        RouterUtils.jump(RouterConstants.App.StudyColor, bundle);
        break;
      case R.id.tv_word:
        RouterUtils.jump(RouterConstants.App.StudyWord);
        break;
      case R.id.tv_animal:
        jumpObject(StudyObjectActivity.TYPE_ANIMAL);
        break;
      case R.id.tv_tool:
        jumpObject(StudyObjectActivity.TYPE_TOOL);
        break;
      case R.id.tv_fruit:
        jumpObject(StudyObjectActivity.TYPE_FRUIT);
        break;
      case R.id.tv_im:
        RouterUtils.jump(RouterConstants.App.Chat);
        break;
      case R.id.tv_address:
        RouterUtils.jump(RouterConstants.App.AddressTrace);
        break;
      case R.id.tv_address_toplay:
        RouterUtils.jump(RouterConstants.App.MyAddress);
        break;
      case R.id.tv_info:
        RouterUtils.jump(RouterConstants.App.Web);
        break;
      case R.id.tv_tostudy:
        RouterUtils.jump(RouterConstants.App.Native);
        break;
      default:
        break;
    }
  }

  private void jumpObject(int type) {
    Bundle bundle = new Bundle();
    bundle.putBoolean("isEdit", isChange);
    bundle.putInt("type", type);
    RouterUtils.jump(RouterConstants.App.StudyObject, bundle);
  }

  private void loginout() {
    BmobUtils.getInstance().updateLoginState(false, new BmobUtils.OnBmobListener() {
      @Override
      public void onSuccess(String objectId) {
        MobclickAgent.onProfileSignOff();
        NIMClient.getService(AuthService.class).logout();
        SharedPrefUtils.getInstance().saveLoginToken("");
        EventBus.getDefault().post(new FinishHomeEvent());
        RouterUtils.jumpWithFinish(SetActivity.this, RouterConstants.App.Main);
      }

      @Override
      public void onError(String err) {
        ToastUtils.showToast("退出失败");
      }
    });
  }

  @Override
  public void onBackPressed() {
    ActivityCompat.finishAfterTransition(this);
  }
}
