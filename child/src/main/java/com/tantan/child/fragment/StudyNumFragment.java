package com.tantan.child.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.tantan.child.R;
import com.tantan.child.view.numview.NumberParentView;
import com.umeng.analytics.MobclickAgent;
import com.ziroom.base.BaseFragment;

/**
 * 学习数字
 */
public class StudyNumFragment extends BaseFragment implements OnClickListener {

  private TextView mTvNum;
  private TextView mTvMinus;
  private TextView mTvAdd;
  private TextView mTvAutoAdd;
  private TextView mTvAutoMinus;
  private NumberParentView mNumParent;

  private int num;
  private final int maxNum = 100;

  private static final int ADD = 0; //增加
  private static final int MINUS = 1; //减少

  public static StudyNumFragment getInstance() {
    return new StudyNumFragment();
  }

  @Override
  public int getLayoutId() {
    return R.layout.child_fragment_study_num;
  }

  @Override
  public void initViews(View mView) {
    mTvNum = mView.findViewById(R.id.tv_num);
    mTvMinus = mView.findViewById(R.id.tv_minus);
    mTvMinus.setOnClickListener(this);
    mTvAdd = mView.findViewById(R.id.tv_add);
    mTvAdd.setOnClickListener(this);
    mTvAutoAdd = mView.findViewById(R.id.tv_auto_add);
    mTvAutoAdd.setOnClickListener(this);
    mTvAutoMinus = mView.findViewById(R.id.tv_auto_minus);
    mTvAutoMinus.setOnClickListener(this);
    mNumParent = mView.findViewById(R.id.num_parent);
    mNumParent.init();
  }

  @Override
  public void onResume() {
    super.onResume();
    MobclickAgent.onPageStart("StudyNum"); //统计页面
  }

  @Override
  public void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd("StudyNum");
  }


  @Override
  public void onClick(View view) {
    int id = view.getId();
    handler.removeCallbacksAndMessages(null);
    if (id == R.id.tv_add) {
      addNum();
    } else if (id == R.id.tv_minus) {
      minusNum();
    } else if (id == R.id.tv_auto_add) {
      autoAdd();
    } else if (id == R.id.tv_auto_minus) {
      autoMinus();
    }
  }

  Handler handler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(@NonNull Message msg) {
      super.handleMessage(msg);
      if (msg.what == ADD) {
        autoAdd();
      } else if (msg.what == MINUS) {
        autoMinus();
      }
    }
  };

  //加
  private void addNum() {
    if (num < maxNum - 1) {
      num++;
      mTvAutoMinus.setTextColor(Color.BLACK);
      mTvMinus.setTextColor(Color.BLACK);
    }
    if (num == maxNum - 1) {
      mTvAutoAdd.setTextColor(Color.parseColor("#999999"));
      mTvAdd.setTextColor(Color.parseColor("#999999"));
    }
    mTvNum.setText(String.valueOf(num));
    mNumParent.setNum(num);
  }

  //减
  private void minusNum() {
    if (num > 0) {
      num--;
      mTvAutoAdd.setTextColor(Color.BLACK);
      mTvAdd.setTextColor(Color.BLACK);
    }
    if (num == 0) {
      mTvAutoMinus.setTextColor(Color.parseColor("#999999"));
      mTvMinus.setTextColor(Color.parseColor("#999999"));
    }
    mTvNum.setText(String.valueOf(num));
    mNumParent.setNum(num);
  }

  //自动加
  private void autoAdd() {
    if (num < maxNum - 1) {
      addNum();
    }
    if (num < maxNum - 1) {
      handler.sendEmptyMessageDelayed(ADD, 1000);
    }
  }

  //自动减
  private void autoMinus() {
    if (num > 0) {
      minusNum();
    }
    if (num > 0) {
      handler.sendEmptyMessageDelayed(MINUS, 1000);
    }
  }
}
