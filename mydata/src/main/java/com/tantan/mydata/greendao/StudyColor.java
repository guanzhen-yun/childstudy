package com.tantan.mydata.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "STUDYCOLOR")
public class StudyColor extends DbBean {

  @Id(autoincrement = true)
  Long id;
  @Property(nameInDb = "colorStr")
  private String colorStr;
  @Property(nameInDb = "isBgWhite")
  private boolean isBgWhite;
  @Property(nameInDb = "colorText")
  private String colorText;

  @Generated(hash = 1331092396)
  public StudyColor(Long id, String colorStr, boolean isBgWhite,
      String colorText) {
    this.id = id;
    this.colorStr = colorStr;
    this.isBgWhite = isBgWhite;
    this.colorText = colorText;
  }

  @Generated(hash = 495228260)
  public StudyColor() {
  }

  public StudyColor(String colorName) {
    this.colorStr = colorName;
  }

  public boolean isBgWhite() {
    return isBgWhite;
  }

  public void setBgWhite(boolean bgWhite) {
    isBgWhite = bgWhite;
  }

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

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean getIsBgWhite() {
    return this.isBgWhite;
  }

  public void setIsBgWhite(boolean isBgWhite) {
    this.isBgWhite = isBgWhite;
  }
}
