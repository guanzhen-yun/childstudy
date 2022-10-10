package com.tantan.login.regist;

import com.tantan.base.utils.ToastUtils;
import com.tantan.base.utils.greendao.DaoSessionUtils;
import com.tantan.base.utils.greendao.DaoSessionUtils.OnDaoListener;
import com.tantan.base.utils.LoginUtils;
import com.tantan.login.regist.RegistContract.IView;
import com.tantan.mydata.greendao.DbBean;
import com.tantan.mydata.greendao.UserInfoEntity;
import com.tantan.mydata.greendao.UserInfoEntityDao.Properties;
import com.ziroom.mvp.base.BaseMvpPresenter;
import java.util.List;

public class RegistPresenter extends BaseMvpPresenter<RegistContract.IView> implements
    RegistContract.IPresenter {

  public RegistPresenter(IView view) {
    super(view);
  }


  @Override
  public void regist(UserInfoEntity userInfo) {
    //查询该用户是否已经注册过
    List<? extends DbBean> dbBeans = DaoSessionUtils.getInstance()
        .querySqlAll(userInfo, Properties.AccountNum.columnName + "=" + userInfo.getAccountNum());
    if (dbBeans.size() > 0) {
      String nickName = ((UserInfoEntity) dbBeans.get(0)).getNick();
      ToastUtils.showToast("该用户:" + nickName + "已注册，自动登录...");
      LoginUtils.saveLoginInfo(userInfo.getAccountNum());
      mView.registSuccess();
    } else {
      //没有注册过
      DaoSessionUtils.getInstance().insertDbBean(userInfo, new OnDaoListener() {
        @Override
        public void onSuccess() {
          LoginUtils.saveLoginInfo(userInfo.getAccountNum());
          ToastUtils.showToast("注册成功, 将自动登录...");
          mView.registSuccess();
        }

        @Override
        public void onError(String err) {
          ToastUtils.showToast("注册失败" + err);
        }
      });
    }
  }
}
