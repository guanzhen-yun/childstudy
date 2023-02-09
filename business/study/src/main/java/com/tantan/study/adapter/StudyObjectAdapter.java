package com.tantan.study.adapter;

import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tantan.mydata.greendao.StudyObject;
import com.tantan.study.R;
import java.util.List;

public class StudyObjectAdapter extends BaseQuickAdapter<StudyObject, BaseViewHolder> {

  private boolean isEdit;

  public void setEdit(boolean isEdit) {
    this.isEdit = isEdit;
  }

  private OnDeleteObjectListener onDeleteObjectListener;

  public void setOnDeleteObjectListener(OnDeleteObjectListener onDeleteObjectListener) {
    this.onDeleteObjectListener = onDeleteObjectListener;
  }

  public StudyObjectAdapter(@Nullable List<StudyObject> data) {
    super(R.layout.item_object, data);
  }

  @Override
  protected void convert(BaseViewHolder helper, StudyObject item) {
    if (isEdit) {
      helper.setGone(R.id.tv_delete, true);
    } else {
      helper.setGone(R.id.tv_delete, false);
    }
    helper.setText(R.id.tv_object, item.getObjName());
    helper.setText(R.id.tv_word, item.getObjWord());
    ImageView ivObject = helper.getView(R.id.iv_object);

    Glide.with(mContext).load(item.getObjUrl()).into(ivObject);
    helper.getView(R.id.tv_delete).setOnClickListener(v -> {
      if (onDeleteObjectListener != null) {
        onDeleteObjectListener.deleteObject(helper.getAdapterPosition());
      }
    });
  }

  public interface OnDeleteObjectListener {

    void deleteObject(int position);
  }
}
