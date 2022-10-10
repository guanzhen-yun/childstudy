package com.tantan.setting;

import android.app.Activity;
import com.tantan.mydata.utils.SharedPrefUtils;
import com.tantan.setting.bean.SettingItem;
import java.util.ArrayList;
import java.util.List;

/**
 * 设置项工厂类
 */
public class SettingItemFactory {

  private SettingItemFactory() {
  }

  private static SettingItemFactory instance;

  public static SettingItemFactory getInstance() {
    if (instance == null) {
      synchronized (SettingItemFactory.class) {
        if (instance == null) {
          instance = new SettingItemFactory();
        }
      }
    }
    return instance;
  }

  public List<SettingItem> getSettingItemList() {
    List<SettingItem> list = new ArrayList<>();
    SettingItem userInfo = new SettingItem(SettingItem.ITEMTYPE_USERINFO, "个人资料");
    list.add(userInfo);
    SettingItem colorItem = new SettingItem(SettingItem.ITEMTYPE_COLOR, "识别颜色");
    list.add(colorItem);
    SettingItem animalItem = new SettingItem(SettingItem.ITEMTYPE_ANIMAL, "看看小动物");
    list.add(animalItem);
    SettingItem englishItem = new SettingItem(SettingItem.ITEMTYPE_WORDS, "英语单词");
    list.add(englishItem);
    SettingItem transportItem = new SettingItem(SettingItem.ITEMTYPE_TRANS, "交通工具");
    list.add(transportItem);
    SettingItem vegetableFruitItem = new SettingItem(SettingItem.ITEMTYPE_FRUITS, "蔬菜水果");
    list.add(vegetableFruitItem);

    boolean isParent = SharedPrefUtils.getInstance().isParent();
    if (isParent) {
      SettingItem chatWithChildItem = new SettingItem(SettingItem.ITEMTYPE_CHATTOBABY, "和宝宝聊天");
      list.add(chatWithChildItem);
      SettingItem addressTraceItem = new SettingItem(SettingItem.ITEMTYPE_TRACE_ADDRESS, "定位追踪");
      list.add(addressTraceItem);
      SettingItem useInfoItem = new SettingItem(SettingItem.ITEMTYPE_USE_INFO, "使用说明");
      list.add(useInfoItem);
      SettingItem studyCourceItem = new SettingItem(SettingItem.ITEMTYPE_STUDY, "学习课程");
      list.add(studyCourceItem);
    } else {
      SettingItem chatWithMotherItem = new SettingItem(SettingItem.ITEMTYPE_CHAT_MOTHER, "和妈妈聊天");
      list.add(chatWithMotherItem);
      SettingItem addressItem = new SettingItem(SettingItem.ITEMTYPE_ADDRESS_TOPLAY, "去哪玩");
      list.add(addressItem);
    }

    return list;
  }

  //根据item类型跳转相应页面
  public void jumpToPage(Activity activity, int itemType) {
    switch (itemType) {
      case SettingItem.ITEMTYPE_USERINFO://个人资料
        break;
      case SettingItem.ITEMTYPE_COLOR://识别颜色
        break;
      case SettingItem.ITEMTYPE_ANIMAL://看看小动物
        break;
      case SettingItem.ITEMTYPE_WORDS://英语单词
        break;
      case SettingItem.ITEMTYPE_TRANS://交通工具
        break;
      case SettingItem.ITEMTYPE_FRUITS://蔬菜水果
        break;
      case SettingItem.ITEMTYPE_CHATTOBABY://和宝宝聊天
        break;
      case SettingItem.ITEMTYPE_TRACE_ADDRESS://定位追踪
        break;
      case SettingItem.ITEMTYPE_USE_INFO://使用说明
        break;
      case SettingItem.ITEMTYPE_STUDY://学习课程
        break;
      case SettingItem.ITEMTYPE_CHAT_MOTHER://和妈妈聊天
        break;
      case SettingItem.ITEMTYPE_ADDRESS_TOPLAY://去哪玩
        break;
      default:
        break;
    }
  }
}
