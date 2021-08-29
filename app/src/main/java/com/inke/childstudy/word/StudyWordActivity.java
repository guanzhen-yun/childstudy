package com.inke.childstudy.word;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.adapter.StudyWordAdapter;
import com.inke.childstudy.routers.RouterConstants;
import com.ziroom.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(path = RouterConstants.App.StudyWord)
public class StudyWordActivity extends BaseActivity {
    @BindView(R.id.rv_word)
    RecyclerView mRvWord;

    @Override
    public int getLayoutId() {
        return R.layout.activity_studyword;
    }

    @Override
    public void initViews() {
        List<String> listWord = new ArrayList<>();
        for (int i=0;i<26;i++) {
            listWord.add(String.valueOf((char)('A' + i)));
        }
        StudyWordAdapter adapter = new StudyWordAdapter(listWord);
        mRvWord.setAdapter(adapter);
    }
}
