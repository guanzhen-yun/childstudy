package com.tantan.study.studycolor;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.tantan.base.RouterConstants;
import com.tantan.base.utils.ViewUtils;
import com.tantan.mydata.greendao.StudyColor;
import com.tantan.study.R;
import com.tantan.study.adapter.StudyColorAdapter;
import com.ziroom.base.BaseActivity;
import java.util.ArrayList;
import java.util.List;

@Route(path = RouterConstants.Study.StudyColor)
public class StudyColorActivity extends BaseActivity<StudyColorPresenter> implements
    View.OnClickListener, StudyColorContract.IView {

  @Autowired(name = "isEdit")
  boolean isEdit;

  private final List<StudyColor> mListColor = new ArrayList<>();
  private StudyColorAdapter studyColorAdapter;

  @Override
  public int getLayoutId() {
    return R.layout.activity_studycolor;
  }

  @Override
  public StudyColorPresenter getPresenter() {
    return new StudyColorPresenter(this);
  }

  @Override
  public void initViews() {
    TextView tvInsert = findViewById(R.id.tv_right);
    tvInsert.setText("添加");
    tvInsert.setOnClickListener(this);
    ViewUtils.setViewsVisible(tvInsert);
    TextView tvTitle = findViewById(R.id.tv_title);
    tvTitle.setText("学习颜色");
    RecyclerView rvColor = findViewById(R.id.rv_color);
    findViewById(R.id.view_left).setOnClickListener(this);
    if (isEdit) {
      tvInsert.setVisibility(View.VISIBLE);
    }
    studyColorAdapter = new StudyColorAdapter(mListColor);
    studyColorAdapter.setEdit(isEdit);
    studyColorAdapter.setOnDeleteColorListener(this::deleteColorObject);
    rvColor.setAdapter(studyColorAdapter);
  }

  @Override
  public void initDatas() {
    mPresenter.queryColor();
  }

  @Override
  public void setColors(List<StudyColor> colors) {
    mListColor.clear();
    if (!colors.isEmpty()) {
      mListColor.addAll(colors);
    }
    studyColorAdapter.notifyItemRangeChanged(0, colors.size());
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.view_left) {
      finish();
    } else if (view.getId() == R.id.tv_right) {
      mPresenter.addNewColor();
    }
  }

  @Override
  public Context getContext() {
    return this;
  }

  private void deleteColorObject(int position) {
    mPresenter.deleteColor(mListColor.get(position));
  }
}
