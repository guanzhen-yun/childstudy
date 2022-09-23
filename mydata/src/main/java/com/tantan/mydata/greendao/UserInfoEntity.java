package com.tantan.mydata.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 本地存储的用户信息
 */
@Entity(nameInDb = "USERINFO")
public class UserInfoEntity extends DbBean {

  @Id(autoincrement = true)
  Long id;
  @Property(nameInDb = "headPath")
  String headPath;
  @Property(nameInDb = "nick")
  String nick;
  @Property(nameInDb = "sex")
  String sex;
  @Property(nameInDb = "age")
  int age;
  @Property(nameInDb = "accountNum")
  String accountNum;
  @Property(nameInDb = "isParent")
  boolean isParent;
  @Property(nameInDb = "password")
  String password;

  @Generated(hash = 1111537556)
  public UserInfoEntity(Long id, String headPath, String nick, String sex, int age,
      String accountNum,
      boolean isParent, String password) {
    this.id = id;
    this.headPath = headPath;
    this.nick = nick;
    this.sex = sex;
    this.age = age;
    this.accountNum = accountNum;
    this.isParent = isParent;
    this.password = password;
  }

  @Generated(hash = 2042969639)
  public UserInfoEntity() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getHeadPath() {
    return this.headPath;
  }

  public void setHeadPath(String headPath) {
    this.headPath = headPath;
  }

  public String getNick() {
    return this.nick;
  }

  public void setNick(String nick) {
    this.nick = nick;
  }

  public String getSex() {
    return this.sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public int getAge() {
    return this.age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getAccountNum() {
    return this.accountNum;
  }

  public void setAccountNum(String accountNum) {
    this.accountNum = accountNum;
  }

  public boolean getIsParent() {
    return this.isParent;
  }

  public void setIsParent(boolean isParent) {
    this.isParent = isParent;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserInfoEntity(String userName, String password, String name, int age, boolean isParent) {
    this.accountNum = userName;
    this.age = age;
    this.nick = name;
    this.password = password;
    this.isParent = isParent;
  }

  public UserInfoEntity(String userName, String password) {
    this.accountNum = userName;
    this.password = password;
  }

  public UserInfoEntity(String userName) {
    this.accountNum = userName;
  }

}
