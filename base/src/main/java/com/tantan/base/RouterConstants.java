package com.tantan.base;

/**
 * Author:关震 Date:2020/5/29 11:01 Description:RouterConstants 路由常量类
 **/
public interface RouterConstants {

  interface Main {

    String Root = "/main/";
    String Main = Root + "main";
  }

  interface Child {

    String Root = "/child/";
    String Home = Root + "home";
  }

  interface Parent {

    String Root = "/parent/";
    String Home = Root + "home";
  }

  interface App {

    String Root = "/app/";
    String Main = Root + "main";
    String Regist = Root + "regist";
    String Login = Root + "login";
    String Home = Root + "home";
    String Set = Root + "set";
    String UserInfo = Root + "userinfo";
    String BigHead = Root + "bighead";
    String StudyColor = Root + "studycolor";
    String StudyWord = Root + "studyword";
    String StudyObject = Root + "studyobject";
    String BigPic = Root + "bigpic";
    String Chat = Root + "chat";
    String AddressTrace = Root + "addresstrace";
    String MyAddress = Root + "myaddress";
    String Web = Root + "web";
    String Native = Root + "native";
    String Video = Root + "video";
    String Png = Root + "png";
    String FileSave = Root + "filesave";
  }
}
