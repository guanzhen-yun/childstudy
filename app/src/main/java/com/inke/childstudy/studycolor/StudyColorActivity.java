package com.inke.childstudy.studycolor;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.adapter.StudyColorAdapter;
import com.inke.childstudy.entity.StudyColor;
import com.inke.childstudy.routers.RouterConstants;
import com.tantan.mydata.utils.BmobUtils;
import com.inke.childstudy.view.dialog.BottomAddColorDialog;
import com.tantan.base.utils.ToastUtils;
import com.tantan.mydata.utils.SharedPrefUtils;
import com.ziroom.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

@Route(path = RouterConstants.App.StudyColor)
public class StudyColorActivity extends BaseActivity {

  @Autowired(name = "isEdit")
  boolean isEdit;

  @BindView(R.id.tv_insert)
  TextView mTvInsert;
  @BindView(R.id.rv_color)
  RecyclerView mRvColor;

  private List<StudyColor> mListColor = new ArrayList<>();
  private StudyColorAdapter studyColorAdapter;
  private String loginToken;

  @Override
  public int getLayoutId() {
    return R.layout.activity_studycolor;
  }

  @Override
  public void initViews() {
    if (isEdit) {
      mTvInsert.setVisibility(View.VISIBLE);
    }
    studyColorAdapter = new StudyColorAdapter(mListColor);
    studyColorAdapter.setEdit(isEdit);
    studyColorAdapter.setOnDeleteColorListener(new StudyColorAdapter.OnDeleteColorListener() {
      @Override
      public void deleteColor(int position) {
        deleteColorObject(position);
      }
    });
    mRvColor.setAdapter(studyColorAdapter);
  }

  @Override
  public void initDatas() {
    loginToken = SharedPrefUtils.getInstance().getLoginToken();
    queryColor();
  }

  @OnClick({R.id.tv_back, R.id.tv_insert})
  public void onClickView(View v) {
    switch (v.getId()) {
      case R.id.tv_back:
        finish();
        break;
      case R.id.tv_insert:
        BottomAddColorDialog bottomAddColorDialog = new BottomAddColorDialog(
            StudyColorActivity.this);
        bottomAddColorDialog.setOnAddColorListener(new BottomAddColorDialog.OnAddColorListener() {
          @Override
          public void onAdd(String colorName, String colorContent, boolean isBgWhite) {
            addColor(colorName, colorContent, isBgWhite);
          }
        });
        bottomAddColorDialog.show();
        break;
      default:
        break;
    }
  }

  private void queryColor() {
    BmobQuery<StudyColor> bmobQuery = new BmobQuery<>();
    bmobQuery.addWhereEqualTo("token", loginToken);
//    bmobQuery.findObjects(new FindListener<StudyColor>() {
//      @Override
//      public void done(List<StudyColor> list, BmobException e) {
//        if (e != null) {
//          ToastUtils.showToast(e.getMessage());
//        } else {
//          mListColor.clear();
//          mListColor.addAll(list);
//          studyColorAdapter.notifyDataSetChanged();
//        }
//      }
//    });
  }

  private void isContainsColor(String colorText, OnExistListener onExistListener) {
//    BmobQuery<StudyColor> bmobQuery = new BmobQuery<>();
//    bmobQuery.addWhereEqualTo("token", loginToken);
//    bmobQuery.addWhereEqualTo("colorText", colorText);
//    bmobQuery.findObjects(new FindListener<StudyColor>() {
//      @Override
//      public void done(List<StudyColor> list, BmobException e) {
//        if (list != null && list.size() > 0) {
//          onExistListener.isExist(true);
//        } else {
//          onExistListener.isExist(false);
//        }
//      }
//    });
  }

  private interface OnExistListener {

    void isExist(boolean isExist);
  }

  private void addColor(String colorName, String colorContent, boolean isBgWhite) {
    //先判断该颜色是否有
    isContainsColor(colorName, new OnExistListener() {
      @Override
      public void isExist(boolean isExist) {
        if (!isExist) {
          StudyColor studyColor = new StudyColor();
          studyColor.setToken(loginToken);
          studyColor.setColorText(colorName);
          studyColor.setBgWhite(isBgWhite);
          studyColor.setColorStr(colorContent);
          BmobUtils.getInstance().addData(studyColor, new BmobUtils.OnBmobListener() {
            @Override
            public void onSuccess(String objectId) {
              queryColor();
            }

            @Override
            public void onError(String err) {
              ToastUtils.showToast(err);
            }
          });
        } else {
          ToastUtils.showToast("该颜色已存在!");
        }
      }
    });
  }

  private void deleteColorObject(int position) {
    StudyColor studyColor = mListColor.get(position);
//    studyColor.delete(studyColor.getObjectId(), new UpdateListener() {
//      @Override
//      public void done(BmobException e) {
//        if (e == null) {
//          queryColor();
//        } else {
//          ToastUtils.showToast(e.getMessage());
//        }
//      }
//    });
  }
}
