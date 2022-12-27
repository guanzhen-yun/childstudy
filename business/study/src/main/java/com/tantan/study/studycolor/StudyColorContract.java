package com.tantan.study.studycolor;

import android.content.Context;
import com.tantan.mydata.greendao.StudyColor;
import com.ziroom.mvp.IMvpView;
import java.util.List;

public interface StudyColorContract {

  interface IView extends IMvpView {

    Context getContext();

    void setColors(List<StudyColor> colors);
  }

  interface IPresenter {

    void queryColor();

    void addNewColor();

    void deleteColor(StudyColor studyColor);
  }
}
