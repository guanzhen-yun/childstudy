package com.inke.childstudy.entity;

import cn.bmob.v3.BmobObject;

public class StudyColor extends BmobObject {

  public String getColorStr() {
    return colorStr;
  }

  public void setColorStr(String colorStr) {
    this.colorStr = colorStr;
  }

  public String getColorText() {
    return colorText;
  }

  public void setColorText(String colorText) {
    this.colorText = colorText;
  }

  private String colorStr;
  private boolean isBgWhite;
  private String colorText;
  private String token;

  public boolean isBgWhite() {
    return isBgWhite;
  }

  public void setBgWhite(boolean bgWhite) {
    isBgWhite = bgWhite;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
