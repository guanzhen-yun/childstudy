package com.inke.childstudy.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.inke.childstudy.R;
import com.inke.childstudy.view.DownloadCircleView;

public class DownloadCircleDialog extends Dialog {
    public DownloadCircleDialog(Context context) {
        super(context, R.style.Theme_Circle_Dialog);
    }

    DownloadCircleView circleView;
    TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_circle_dialog_layout);
        this.setCancelable(false);//设置点击弹出框外部，无法取消对话框
        circleView = findViewById(R.id.circle_view);
        tvMsg = findViewById(R.id.tv_msg);
    }

    public void setProgress(int progress) {
        circleView.setProgress(progress);
    }

    public void setMsg(String msg) {
        tvMsg.setText(msg);
    }
}
