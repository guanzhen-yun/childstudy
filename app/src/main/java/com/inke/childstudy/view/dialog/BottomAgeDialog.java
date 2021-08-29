package com.inke.childstudy.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.inke.childstudy.R;

import java.util.ArrayList;
import java.util.List;

public class BottomAgeDialog extends Dialog implements View.OnClickListener {
    private OnSelectListener onSelectListener;
    private WheelView wheelview;
    private String mSelectAge;
    private List<String> ageItems;

    public BottomAgeDialog(@NonNull Context context) {
        super(context, R.style.BottomDialogStyle);
        // 拿到Dialog的Window, 修改Window的属性
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_age_dialog);
        wheelview = findViewById(R.id.wheelview);
        wheelview.setCyclic(false);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_ok).setOnClickListener(this);
        initDatas();
    }

    private void initDatas() {
        ageItems = new ArrayList<>();
        for (int i = 2; i < 101; i++) {
            ageItems.add(i + "岁");
        }
        wheelview.setAdapter(new ArrayWheelAdapter(ageItems));
        wheelview.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mSelectAge = ageItems.get(index);
            }
        });
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_ok:
                if(TextUtils.isEmpty(mSelectAge)) {
                    mSelectAge = ageItems.get(0);
                }
                if (!TextUtils.isEmpty(mSelectAge) && onSelectListener != null) {
                    onSelectListener.onSelect(mSelectAge);
                }
                dismiss();
                break;
        }
    }

    public interface OnSelectListener {
        void onSelect(String age);
    }

    class ArrayWheelAdapter implements WheelAdapter<String> {
        private List<String> ageList;
        public ArrayWheelAdapter(List<String> ageList) {
            this.ageList= ageList;
        }

        @Override
        public int getItemsCount() {
            return ageList.size();
        }

        @Override
        public String getItem(int index) {
            return ageList.get(index);
        }

        @Override
        public int indexOf(String o) {
            return o.length();
        }
    }
}
