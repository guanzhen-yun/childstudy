package com.tantan.mydata.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "STUDYOBJECT")
public class StudyObject extends DbBean {

  @Id(autoincrement = true)
  Long id;
  @Property(nameInDb = "objUrl")
  private String objUrl;
  @Property(nameInDb = "type")
  private int type;
  @Property(nameInDb = "objName")
  private String objName;
  @Property(nameInDb = "objWord")
  private String objWord;

  @Generated(hash = 1889143951)
  public StudyObject(Long id, String objUrl, int type, String objName,
          String objWord) {
      this.id = id;
      this.objUrl = objUrl;
      this.type = type;
      this.objName = objName;
      this.objWord = objWord;
  }

  @Generated(hash = 1559962116)
  public StudyObject() {
  }

  public String getObjUrl() {
    return objUrl;
  }

  public void setObjUrl(String objUrl) {
    this.objUrl = objUrl;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getObjName() {
    return objName;
  }

  public void setObjName(String objName) {
    this.objName = objName;
  }

  public String getObjWord() {
    return objWord;
  }

  public void setObjWord(String objWord) {
    this.objWord = objWord;
  }

  public Long getId() {
      return this.id;
  }

  public void setId(Long id) {
      this.id = id;
  }
}
