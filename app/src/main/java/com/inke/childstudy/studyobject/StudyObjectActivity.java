package com.inke.childstudy.studyobject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.inke.childstudy.R;
import com.inke.childstudy.adapter.StudyObjectAdapter;
import com.inke.childstudy.entity.StudyObject;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.BmobUtils;
import com.inke.childstudy.utils.SharedPrefUtils;
import com.inke.childstudy.utils.ToastUtils;
import com.inke.childstudy.view.dialog.BottomAddObjectDialog;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

@Route(path = RouterConstants.App.StudyObject)
public class StudyObjectActivity extends BaseActivity {
    public static final int TYPE_ANIMAL = 0;//动物
    public static final int TYPE_TOOL = 1;//交通工具
    public static final int TYPE_FRUIT = 2;//蔬菜水果

    @Autowired(name = "isEdit")
    boolean isEdit;
    @Autowired(name = "type")
    int type;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_insert)
    TextView mTvInsert;
    @BindView(R.id.rv_object)
    RecyclerView mRvObject;

    private String loginToken;

    private List<StudyObject> mListObject = new ArrayList<>();
    private StudyObjectAdapter studyObjectAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_study_object;
    }

    @Override
    public void initViews() {
        if (isEdit) {
            mTvInsert.setVisibility(View.VISIBLE);
        }
        switch (type) {
            case TYPE_ANIMAL:
                mTvTitle.setText("识别动物");
                break;
            case TYPE_TOOL:
                mTvTitle.setText("学习交通工具");
                break;
            case TYPE_FRUIT:
                mTvTitle.setText("学习蔬菜水果");
                break;
            default:
                break;
        }
        studyObjectAdapter = new StudyObjectAdapter(mListObject);
        studyObjectAdapter.setEdit(isEdit);
        studyObjectAdapter.setOnDeleteObjectListener(new StudyObjectAdapter.OnDeleteObjectListener() {
            @Override
            public void deleteObject(int position) {
                deleteMyObject(position);
            }
        });
        studyObjectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("imagePath", mListObject.get(position).getObjUrl());
                RouterUtils.jump(RouterConstants.App.BigPic, bundle);
            }
        });
        mRvObject.setAdapter(studyObjectAdapter);
    }

    @Override
    public void initDatas() {
        loginToken = SharedPrefUtils.getInstance().getLoginToken();
        queryObject();
    }

    @OnClick({R.id.tv_back, R.id.tv_insert})
    public void onClickView(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_insert:
                BottomAddObjectDialog bottomAddObjectDialog = new BottomAddObjectDialog(StudyObjectActivity.this);
                bottomAddObjectDialog.setType(type);
                bottomAddObjectDialog.setOnAddObjectListener(new BottomAddObjectDialog.OnAddObjectListener() {
                    @Override
                    public void onAdd(String objectName, String objectUrl, String objectWord) {
                        addObject(objectName, objectUrl, objectWord);
                    }
                });
                bottomAddObjectDialog.show();
                break;
            default:
                break;
        }
    }

    private void addObject(String objectName, String objectUrl, String objectWord) {
        //先判断该对象是否有
        isContainsObject(objectName, new OnExistListener() {
            @Override
            public void isExist(boolean isExist) {
                if (!isExist) {
                    StudyObject studyObject = new StudyObject();
                    studyObject.setToken(loginToken);
                    studyObject.setObjName(objectName);
                    studyObject.setObjUrl(objectUrl);
                    studyObject.setObjWord(objectWord);
                    BmobUtils.getInstance().addData(studyObject, new BmobUtils.OnBmobListener() {
                        @Override
                        public void onSuccess(String objectId) {
                            queryObject();
                        }

                        @Override
                        public void onError(String err) {
                            ToastUtils.showToast(err);
                        }
                    });
                } else {
                    ToastUtils.showToast("该对象已存在!");
                }
            }
        });
    }

    private void deleteMyObject(int position) {
        StudyObject studyObject = mListObject.get(position);
        studyObject.delete(studyObject.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    queryObject();
                } else {
                    ToastUtils.showToast(e.getMessage());
                }
            }
        });
    }

    private void queryObject() {
        BmobQuery<StudyObject> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("token", loginToken);
        bmobQuery.addWhereEqualTo("type", type);
        bmobQuery.findObjects(new FindListener<StudyObject>() {
            @Override
            public void done(List<StudyObject> list, BmobException e) {
                if (e != null) {
                    ToastUtils.showToast(e.getMessage());
                } else {
                    mListObject.clear();
                    mListObject.addAll(list);
                    studyObjectAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void isContainsObject(String objName, OnExistListener onExistListener) {
        BmobQuery<StudyObject> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("token", loginToken);
        bmobQuery.addWhereEqualTo("objName", objName);
        bmobQuery.addWhereEqualTo("type", type);
        bmobQuery.findObjects(new FindListener<StudyObject>() {
            @Override
            public void done(List<StudyObject> list, BmobException e) {
                if (list != null && list.size() > 0) {
                    onExistListener.isExist(true);
                } else {
                    onExistListener.isExist(false);
                }
            }
        });
    }

    private interface OnExistListener {
        void isExist(boolean isExist);
    }


}
