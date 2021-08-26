package com.inke.childstudy.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.inke.childstudy.R;
import com.inke.childstudy.view.numview.NumberParentView;
import com.ziroom.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 学习数字
 */
public class StudyNumFragment extends BaseFragment {
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.tv_minus)
    TextView mTvMinus;
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.tv_auto_add)
    TextView mTvAutoAdd;
    @BindView(R.id.tv_auto_minus)
    TextView mTvAutoMinus;
    @BindView(R.id.num_parent)
    NumberParentView mNumParent;

    private int num;
    private int maxNum = 100;

    private static final int ADD = 0; //增加
    private static final int MINUS = 1; //减少

    public static StudyNumFragment getInstance() {
        return new StudyNumFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_study_num;
    }

    @Override
    public void initViews(View mView) {
        super.initViews(mView);
        mNumParent.init();
    }

    @OnClick({R.id.tv_add, R.id.tv_minus, R.id.tv_auto_add, R.id.tv_auto_minus})
    public void onClickView(View v) {
        handler.removeCallbacksAndMessages(null);
        switch (v.getId()) {
            case R.id.tv_add:
                addNum();
                break;
            case R.id.tv_minus:
                minusNum();
                break;
            case R.id.tv_auto_add:
                autoAdd();
                break;
            case R.id.tv_auto_minus:
                autoMinus();
                break;
        }
    }

    private void autoAdd() {
        if (num < maxNum - 1) {
            addNum();
        }
        if (num < maxNum - 1) {
            handler.sendEmptyMessageDelayed(ADD, 1000);
        }
    }

    private void autoMinus() {
        if (num > 0) {
            minusNum();
        }
        if (num > 0) {
            handler.sendEmptyMessageDelayed(MINUS, 1000);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
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

    private void addNum() {
        if (num < maxNum - 1) {
            num++;
            mTvAutoMinus.setTextColor(Color.parseColor("#000000"));
            mTvMinus.setTextColor(Color.parseColor("#000000"));
        }
        if (num == maxNum - 1) {
            mTvAutoAdd.setTextColor(Color.parseColor("#999999"));
            mTvAdd.setTextColor(Color.parseColor("#999999"));
        }
        mTvNum.setText(String.valueOf(num));
        mNumParent.setNum(num);
    }

    private void minusNum() {
        if (num > 0) {
            num--;
            mTvAutoAdd.setTextColor(Color.parseColor("#000000"));
            mTvAdd.setTextColor(Color.parseColor("#000000"));
        }
        if (num == 0) {
            mTvAutoMinus.setTextColor(Color.parseColor("#999999"));
            mTvMinus.setTextColor(Color.parseColor("#999999"));
        }
        mTvNum.setText(String.valueOf(num));
        mNumParent.setNum(num);
    }
}
