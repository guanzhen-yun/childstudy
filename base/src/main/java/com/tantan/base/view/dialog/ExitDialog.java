package com.tantan.base.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import com.tantan.base.R;

/**
 * 退出
 */
public class ExitDialog extends Dialog implements View.OnClickListener {

  private OnExitListener onExitListener;

  public ExitDialog(@NonNull Context context) {
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
    setContentView(R.layout.view_exitdialog);
    findViewById(R.id.tv_cancel).setOnClickListener(this);
    findViewById(R.id.tv_ok).setOnClickListener(this);
  }

  public void setOnExitListener(OnExitListener onExitListener) {
    this.onExitListener = onExitListener;
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.tv_cancel) {
      dismiss();
    } else if (id == R.id.tv_ok) {
      if (onExitListener != null) {
        onExitListener.onExit();
      }
      dismiss();
    }
  }

  public interface OnExitListener {

    void onExit();
  }
}
