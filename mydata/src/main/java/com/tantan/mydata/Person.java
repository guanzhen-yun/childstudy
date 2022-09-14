package com.tantan.mydata;

import cn.bmob.v3.BmobUser;

/**
 * 注册用户
 */
public class Person extends BmobUser {

  public Person() {
  }

  private String age;
  private String nickname;
  private boolean isParent;

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public Person(String userName, String password, String name, String age, boolean isParent) {
    this.setUsername(userName);
    this.age = age;
    this.nickname = name;
    this.setPassword(password);
    this.isParent = isParent;
  }
}
