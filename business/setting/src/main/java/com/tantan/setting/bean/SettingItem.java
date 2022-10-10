package com.tantan.setting.bean;

/**
 * 设置项
 */
public class SettingItem {

  public static final int ITEMTYPE_USERINFO = 1;//个人资料
  public static final int ITEMTYPE_COLOR = 2;//识别颜色
  public static final int ITEMTYPE_ANIMAL = 3;//看看小动物
  public static final int ITEMTYPE_WORDS = 4;//英语单词
  public static final int ITEMTYPE_TRANS = 5;//交通工具
  public static final int ITEMTYPE_FRUITS = 6;//蔬菜水果
  public static final int ITEMTYPE_CHATTOBABY = 7;//和宝宝聊天
  public static final int ITEMTYPE_TRACE_ADDRESS = 8;//定位追踪
  public static final int ITEMTYPE_USE_INFO = 9;//使用说明
  public static final int ITEMTYPE_STUDY = 10;//学习课程
  public static final int ITEMTYPE_CHAT_MOTHER = 11;//和妈妈聊天
  public static final int ITEMTYPE_ADDRESS_TOPLAY = 12;//去哪玩

  public SettingItem(int itemType, String title) {
    this.itemType = itemType;
    this.title = title;
  }

  public String title;

  public int itemType;
}
