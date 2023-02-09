package com.tantan.study.studyobject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.tantan.base.RouterConstants;
import com.tantan.base.utils.ToastUtils;
import com.tantan.mydata.SettingItem;
import com.tantan.mydata.greendao.StudyObject;
import com.tantan.study.R;
import com.tantan.study.adapter.StudyObjectAdapter;
import com.ziroom.base.BaseActivity;
import com.ziroom.base.RouterUtils;
import java.util.ArrayList;
import java.util.List;

@Route(path = RouterConstants.Study.StudyObject)
public class StudyObjectActivity extends BaseActivity implements View.OnClickListener {

  @Autowired(name = "isEdit")
  boolean isEdit;
  @Autowired(name = "type")
  int type;

  private TextView mTvTitle;
  private TextView mTvInsert;
  private RecyclerView mRvObject;

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
      case SettingItem.TYPE_ANIMAL:
        mTvTitle.setText("识别动物");
        break;
      case SettingItem.TYPE_TOOL:
        mTvTitle.setText("学习交通工具");
        break;
      case SettingItem.TYPE_FRUIT:
        mTvTitle.setText("学习蔬菜水果");
        break;
      default:
        break;
    }
    studyObjectAdapter = new StudyObjectAdapter(mListObject);
    studyObjectAdapter.setEdit(isEdit);
    studyObjectAdapter.setOnDeleteObjectListener(this::deleteMyObject);
    studyObjectAdapter.setOnItemClickListener((adapter, view, position) -> {
      Bundle bundle = new Bundle();
      bundle.putString("imagePath", mListObject.get(position).getObjUrl());
      RouterUtils.jump(RouterConstants.App.BigPic, bundle);
    });
    mRvObject.setAdapter(studyObjectAdapter);
  }

  @Override
  public void initDatas() {
    queryObject();
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.tv_back) {
      finish();
    } else if (view.getId() == R.id.tv_insert) {
      BottomAddObjectDialog bottomAddObjectDialog = new BottomAddObjectDialog(
          StudyObjectActivity.this);
      bottomAddObjectDialog.setType(type);
      bottomAddObjectDialog.setOnAddObjectListener(
          this::addObject);
      bottomAddObjectDialog.show();
    }
  }

  private void addObject(String objectName, String objectUrl, String objectWord) {
    //先判断该对象是否有
    isContainsObject(objectName, new OnExistListener() {
      @Override
      public void isExist(boolean isExist) {
        if (!isExist) {
          StudyObject studyObject = new StudyObject();
          studyObject.setObjName(objectName);
          studyObject.setObjUrl(objectUrl);
          studyObject.setType(type);
          studyObject.setObjWord(objectWord);
//          BmobUtils.getInstance().addData(studyObject, new BmobUtils.OnBmobListener() {
//            @Override
//            public void onSuccess(String objectId) {
//              queryObject();
//            }
//
//            @Override
//            public void onError(String err) {
//              ToastUtils.showToast(err);
//            }
//          });
        } else {
          ToastUtils.showToast("该对象已存在!");
        }
      }
    });
  }

  private void deleteMyObject(int position) {
    StudyObject studyObject = mListObject.get(position);
//    studyObject.delete(studyObject.getObjectId(), new UpdateListener() {
//      @Override
//      public void done(BmobException e) {
//        if (e == null) {
//          queryObject();
//        } else {
//          ToastUtils.showToast(e.getMessage());
//        }
//      }
//    });
  }

  private void queryObject() {
//    BmobQuery<StudyObject> bmobQuery = new BmobQuery<>();
//    bmobQuery.addWhereEqualTo("token", loginToken);
//    bmobQuery.addWhereEqualTo("type", type);
//    bmobQuery.findObjects(new FindListener<StudyObject>() {
//      @Override
//      public void done(List<StudyObject> list, BmobException e) {
//        if (e != null) {
//          ToastUtils.showToast(e.getMessage());
//        } else {
//          mListObject.clear();
//          mListObject.addAll(list);
//          studyObjectAdapter.notifyDataSetChanged();
//        }
//      }
//    });
  }

  private void isContainsObject(String objName, OnExistListener onExistListener) {
//    BmobQuery<StudyObject> bmobQuery = new BmobQuery<>();
//    bmobQuery.addWhereEqualTo("token", loginToken);
//    bmobQuery.addWhereEqualTo("objName", objName);
//    bmobQuery.addWhereEqualTo("type", type);
//    bmobQuery.findObjects(new FindListener<StudyObject>() {
//      @Override
//      public void done(List<StudyObject> list, BmobException e) {
//        if (list != null && list.size() > 0) {
//          onExistListener.isExist(true);
//        } else {
//          onExistListener.isExist(false);
//        }
//      }
//    });
  }

  private interface OnExistListener {

    void isExist(boolean isExist);
  }


}
