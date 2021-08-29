package com.inke.childstudy.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.inke.childstudy.GlideApp;
import com.inke.childstudy.R;
import com.inke.childstudy.entity.StudyObject;

import java.util.List;

public class StudyObjectAdapter extends BaseQuickAdapter<StudyObject, BaseViewHolder> {
    private boolean isEdit;
    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }
    private OnDeleteObjectListener onDeleteObjectListener;
    public void setOnDeleteObjectListener(OnDeleteObjectListener onDeleteObjectListener) {
        this.onDeleteObjectListener = onDeleteObjectListener;}

    public StudyObjectAdapter(@Nullable List<StudyObject> data) {
        super(R.layout.item_object, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StudyObject item) {
        if(isEdit) {
            helper.setGone(R.id.tv_delete, true);
        } else {
            helper.setGone(R.id.tv_delete, false);
        }
        helper.setText(R.id.tv_object, item.getObjName());
        helper.setText(R.id.tv_word, item.getObjWord());
        ImageView ivObject = helper.getView(R.id.iv_object);
        GlideApp.with(mContext).load(item.getObjUrl()).into(ivObject);
        helper.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onDeleteObjectListener != null) {
                    onDeleteObjectListener.deleteObject(helper.getAdapterPosition());
                }
            }
        });
    }

    public interface OnDeleteObjectListener {
        void deleteObject(int position);
    }
}
