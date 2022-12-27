package com.tantan.userinfo.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.tantan.userinfo.R;

public class BottomEditDialog extends Dialog implements View.OnClickListener {

  private OnChangeListener onChangeListener;
  private EditText mEtContent;
  private String mCurrentNickName;

  public BottomEditDialog(@NonNull Context context) {
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
    setContentView(R.layout.view_bottom_edit_dialog);
    mEtContent = findViewById(R.id.et_content);
    if (!TextUtils.isEmpty(mCurrentNickName)) {
      mEtContent.setText(mCurrentNickName);
    }
    findViewById(R.id.tv_cancel).setOnClickListener(this);
    findViewById(R.id.tv_ok).setOnClickListener(this);
  }

  public void setOnChangeListener(OnChangeListener onChangeListener) {
    this.onChangeListener = onChangeListener;
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.tv_cancel) {
      dismiss();
    } else if (v.getId() == R.id.tv_ok) {
      String nick = mEtContent.getText().toString();
      if (!TextUtils.isEmpty(nick) && onChangeListener != null) {
        onChangeListener.onChange(nick);
      }
      dismiss();
    }
  }

  public void setCurrentNickName(String nick) {
    mCurrentNickName = nick;
  }

  public interface OnChangeListener {

    void onChange(String nick);
  }
}
