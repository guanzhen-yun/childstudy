package com.tantan.study.view;

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
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tantan.study.R;
import com.tantan.study.adapter.IsOrNotAdapter;
import java.util.ArrayList;
import java.util.List;

public class BottomAddColorDialog extends Dialog implements View.OnClickListener {

  private OnAddColorListener onAddColorListener;
  private EditText et_color_name;
  private EditText et_color_content;
  private RecyclerView rv_color_isnot;
  private boolean isBgWhite = true;

  public BottomAddColorDialog(@NonNull Context context) {
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
    setContentView(R.layout.view_add_color_dialog);
    et_color_name = findViewById(R.id.et_color_name);
    et_color_content = findViewById(R.id.et_color_content);
    rv_color_isnot = findViewById(R.id.rv_color_isnot);
    List<String> list = new ArrayList<>();
    list.add("是");
    list.add("否");
    IsOrNotAdapter isOrNotAdapter = new IsOrNotAdapter(list);
    isOrNotAdapter.setSelected("是");
    isOrNotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        isOrNotAdapter.setSelected(list.get(position));
        isBgWhite = position == 0;
      }
    });
    rv_color_isnot.setAdapter(isOrNotAdapter);
    findViewById(R.id.tv_cancel).setOnClickListener(this);
    findViewById(R.id.tv_ok).setOnClickListener(this);
  }

  public void setOnAddColorListener(OnAddColorListener onAddColorListener) {
    this.onAddColorListener = onAddColorListener;
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.tv_cancel) {
      dismiss();
    } else if (v.getId() == R.id.tv_ok) {
      String colorName = et_color_name.getText().toString();
      String colorContent = et_color_content.getText().toString();

      if (!TextUtils.isEmpty(colorName) && !TextUtils.isEmpty(colorContent)
          && onAddColorListener != null) {
        onAddColorListener.onAdd(colorName, colorContent, isBgWhite);
      }
      dismiss();
    }
  }

  public interface OnAddColorListener {

    void onAdd(String colorName, String colorContent, boolean isBgWhite);
  }
}
