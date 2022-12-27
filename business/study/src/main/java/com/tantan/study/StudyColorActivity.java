package com.tantan.study;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.tantan.base.RouterConstants;
import com.tantan.base.utils.ToastUtils;
import com.tantan.mydata.utils.SharedPrefUtils;
import com.tantan.study.adapter.StudyColorAdapter;
import com.tantan.study.entity.StudyColor;
import com.tantan.study.view.BottomAddColorDialog;
import com.ziroom.base.BaseActivity;
import java.util.ArrayList;
import java.util.List;

@Route(path = RouterConstants.Study.StudyColor)
public class StudyColorActivity extends BaseActivity implements View.OnClickListener {

  @Autowired(name = "isEdit")
  boolean isEdit;

  private TextView mTvInsert;
  private RecyclerView mRvColor;

  private List<StudyColor> mListColor = new ArrayList<>();
  private StudyColorAdapter studyColorAdapter;
  private String loginToken;

  @Override
  public int getLayoutId() {
    return R.layout.activity_studycolor;
  }

  @Override
  public void initViews() {
    mTvInsert = findViewById(R.id.tv_insert);
    mRvColor = findViewById(R.id.rv_color);
    findViewById(R.id.tv_back).setOnClickListener(this);
    mTvInsert.setOnClickListener(this);
    if (isEdit) {
      mTvInsert.setVisibility(View.VISIBLE);
    }
    studyColorAdapter = new StudyColorAdapter(mListColor);
    studyColorAdapter.setEdit(isEdit);
    studyColorAdapter.setOnDeleteColorListener(this::deleteColorObject);
    mRvColor.setAdapter(studyColorAdapter);
  }

  @Override
  public void initDatas() {
    loginToken = SharedPrefUtils.getInstance().getLoginToken();
    queryColor();
  }

  private void queryColor() {
//    BmobQuery<StudyColor> bmobQuery = new BmobQuery<>();
//    bmobQuery.addWhereEqualTo("token", loginToken);
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

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.tv_back) {
      finish();
    } else if (view.getId() == R.id.tv_insert) {
      BottomAddColorDialog bottomAddColorDialog = new BottomAddColorDialog(
          StudyColorActivity.this);
      bottomAddColorDialog.setOnAddColorListener(
          this::addColor);
      bottomAddColorDialog.show();
    }
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
//          BmobUtils.getInstance().addData(studyColor, new BmobUtils.OnBmobListener() {
//            @Override
//            public void onSuccess(String objectId) {
//              queryColor();
//            }
//
//            @Override
//            public void onError(String err) {
//              ToastUtils.showToast(err);
//            }
//          });
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
