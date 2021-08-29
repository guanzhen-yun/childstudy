package com.inke.childstudy.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.inke.childstudy.R;
import com.inke.childstudy.studyobject.StudyObjectActivity;

public class BottomAddObjectDialog extends Dialog implements View.OnClickListener {
    private OnAddObjectListener onAddObjectListener;
    private EditText et_object_name;
    private EditText et_object_content;
    private EditText et_object_word;
    private TextView tv_title;
    private int mType;

    public BottomAddObjectDialog(@NonNull Context context) {
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
        setContentView(R.layout.view_add_object_dialog);
        et_object_name = findViewById(R.id.et_object_name);
        et_object_content = findViewById(R.id.et_object_content);
        et_object_word = findViewById(R.id.et_object_word);
        tv_title = findViewById(R.id.tv_title);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_ok).setOnClickListener(this);
        initView();
    }

    private void initView() {
        switch (mType) {
            case StudyObjectActivity
                    .TYPE_ANIMAL:
                tv_title.setText("添加动物");
                et_object_name.setHint("输入动物名称");
                et_object_content.setHint("输入动物链接");
                et_object_word.setHint("输入动物单词");
                break;
            case StudyObjectActivity
                    .TYPE_TOOL:
                tv_title.setText("添加交通工具");
                et_object_name.setHint("输入交通工具名称");
                et_object_content.setHint("输入交通工具链接");
                et_object_word.setHint("输入交通工具单词");
                break;
            case StudyObjectActivity
                    .TYPE_FRUIT:
                tv_title.setText("添加蔬菜水果");
                et_object_name.setHint("输入蔬菜水果名称");
                et_object_content.setHint("输入蔬菜水果链接");
                et_object_word.setHint("输入蔬菜水果单词");
                break;
            default:
                break;
        }
    }

    public void setOnAddObjectListener(OnAddObjectListener onAddObjectListener) {
        this.onAddObjectListener = onAddObjectListener;
    }

    public void setType(int type) {
        mType = type;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_ok:
                String objectName = et_object_name.getText().toString();
                String objectUrl = et_object_content.getText().toString();
                String objectWord = et_object_word.getText().toString();

                if(!TextUtils.isEmpty(objectName) && !TextUtils.isEmpty(objectUrl) && !TextUtils.isEmpty(objectWord) && onAddObjectListener != null) {
                    onAddObjectListener.onAdd(objectName, objectUrl, objectWord);
                }
                dismiss();
                break;
        }
    }

    public interface OnAddObjectListener {
        void onAdd(String objectName, String objectUrl, String objectWord);
    }
}
