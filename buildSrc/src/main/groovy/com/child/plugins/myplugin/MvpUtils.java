package com.child.plugins.myplugin;

import java.io.File;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//工具类
public class MvpUtils {

  public static boolean isEmptyStr(String str) {
    return str == null || "".equals(str) || "null".equals(str);
  }

  public static void addNewLine(FileWriter writer, boolean isInsertNewLine) throws Exception {
    writer.write("\n");
    if (isInsertNewLine) {
      writer.write("\n");
    }
  }

  public static void addNewStrWithNewLine(FileWriter writer, String str, boolean isNewLine)
      throws Exception {
    writer.write(str);
    if (isNewLine) {
      writer.write("\n");
    }
  }

  /**
   * 驼峰转下划线
   */
  static String humpToLine(String str) {
    Pattern humpPattern = Pattern.compile("[A-Z]");
    Matcher matcher = humpPattern.matcher(str);
    StringBuffer sb = new StringBuffer();
    while (matcher.find()) {
      matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
    }
    matcher.appendTail(sb);
    return sb.toString();
  }

  //遍历获取文件路径
  public static String findClassPath(String actPrePath, String className) {
    File file = new File(actPrePath);
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      for (int i = 0; i < files.length; i++) {
        File childFile = files[i];
        if (childFile.isDirectory()) {
          File lastFile = new File(childFile.getAbsolutePath(), className);
          if (lastFile.exists()) {
            return childFile.getAbsolutePath();
          }
        }
      }
    }
    return null;
  }

  public static FileWriter createNewFileWriter(String filePath, String fileName) throws Exception {
    File file = new File(filePath, fileName + ".java");
    if (file.exists()) {
      file.delete();
    }
    file.createNewFile();
    FileWriter writer = new FileWriter(file.getPath());
    return writer;
  }
}
