package com.inke.childstudy.adapter;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.inke.childstudy.R;
import com.inke.childstudy.entity.StudyColor;

import java.util.List;

public class StudyColorAdapter extends BaseQuickAdapter<StudyColor, BaseViewHolder> {
    private boolean isEdit;
    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }
    private OnDeleteColorListener onDeleteColorListener;
    public void setOnDeleteColorListener(OnDeleteColorListener onDeleteColorListener) {
        this.onDeleteColorListener = onDeleteColorListener;}
    public StudyColorAdapter(@Nullable List<StudyColor> data) {
        super(R.layout.item_color, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StudyColor item) {
        if(isEdit) {
            helper.setGone(R.id.tv_delete, true);
        } else {
            helper.setGone(R.id.tv_delete, false);
        }
        if(item.isBgWhite()) {
            helper.setBackgroundRes(R.id.ll_color, R.drawable.bg_color_rect);
        } else {
            helper.setBackgroundColor(R.id.ll_color, Color.parseColor("#000000"));
        }

        helper.setBackgroundColor(R.id.view_color, Color.parseColor(item.getColorStr()));

        helper.setText(R.id.tv_color, item.getColorText());
        helper.setTextColor(R.id.tv_color, Color.parseColor(item.getColorStr()));

        helper.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onDeleteColorListener != null) {
                    onDeleteColorListener.deleteColor(helper.getAdapterPosition());
                }
            }
        });
    }

    public interface OnDeleteColorListener {
        void deleteColor(int position);
    }
}
