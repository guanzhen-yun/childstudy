package com.tantan.study.studycolor;

import com.tantan.base.utils.DataUtils;
import com.tantan.base.utils.ToastUtils;
import com.tantan.base.utils.greendao.DaoSessionUtils;
import com.tantan.base.utils.greendao.DaoSessionUtils.OnDaoListener;
import com.tantan.mydata.greendao.StudyColor;
import com.tantan.study.studycolor.StudyColorContract.IView;
import com.tantan.study.view.BottomAddColorDialog;
import com.ziroom.mvp.base.BaseMvpPresenter;
import java.util.List;

public class StudyColorPresenter extends BaseMvpPresenter<IView> implements
    StudyColorContract.IPresenter {

  public StudyColorPresenter(IView view) {
    super(view);
  }

  @Override
  public void queryColor() {
    List<StudyColor> colors = (List<StudyColor>) DaoSessionUtils.getInstance()
        .queryAll(new StudyColor());
    mView.setColors(colors);
  }

  @Override
  public void addNewColor() {
    BottomAddColorDialog bottomAddColorDialog = new BottomAddColorDialog(
        mView.getContext());
    bottomAddColorDialog.setOnAddColorListener(
        this::addColor);
    bottomAddColorDialog.show();
  }

  @Override
  public void deleteColor(StudyColor studyColor) {
    DaoSessionUtils.getInstance().deleteDbBean(studyColor, new OnDaoListener() {
      @Override
      public void onSuccess() {
        queryColor();
      }

      @Override
      public void onError(String err) {
        ToastUtils.showToast("删除失败" + err);
      }
    });
  }

  private void addColor(String colorName, String colorContent, boolean isBgWhite) {
    //先判断该颜色是否有
    if (DataUtils.isContainsColor(new StudyColor(colorName))) {
      ToastUtils.showToast("该颜色已存在!");
    } else {
      StudyColor studyColor = new StudyColor();
      studyColor.setColorText(colorName);
      studyColor.setBgWhite(isBgWhite);
      studyColor.setColorStr(colorContent);
      DaoSessionUtils.getInstance().insertDbBean(studyColor, new OnDaoListener() {
        @Override
        public void onSuccess() {
          ToastUtils.showToast("添加颜色成功");
          queryColor();
        }

        @Override
        public void onError(String err) {
          ToastUtils.showToast("添加颜色失败" + err);
        }
      });
    }
  }
}
