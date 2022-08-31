package com.tantan.mydata;

import cn.bmob.v3.BmobObject;

/**
 * 登录Token
 */
public class LoginToken extends BmobObject {

  private String tokenId;//本地的tokenId 和用户的id保持一致
  private boolean isLogin;//是否登录

  public String getTokenId() {
    return tokenId;
  }

  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }

  public boolean isLogin() {
    return isLogin;
  }

  public void setLogin(boolean login) {
    isLogin = login;
  }
}
