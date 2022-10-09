package com.child.plugins.myplugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

/**
 * mvp插件任务
 */
public class MyMvpPluginTask extends DefaultTask {

  private MyMvpInfo mvpInfo;//mvp信息对象
  private String buildSrcBuildDir;//工程路径  //   /Users/guanzhen/androidproject/childstudy

  private String classShortName;//Login

  // /Users/guanzhen/androidproject/childstudy/login/src/main/java/com/tantan/login/login
  private String fileLastPath;//文件绝对路径

  private String packageStr;//包地址 com.tantan.login.login

  public MyMvpPluginTask() {
    setGroup("'mvptask'");
    setDescription("my plugin task");
    mvpInfo = new MyMvpInfo();
  }

  //相当于Task的执行入口
  @TaskAction
  void doAction() throws Exception {
    buildSrcBuildDir = getProject().getProjectDir().getParent();
    if (!"".equals(mvpInfo.preName)) {
      buildSrcBuildDir = buildSrcBuildDir + File.separator + mvpInfo.preName;
    }
    if ("".equals(mvpInfo.className) || "".equals(mvpInfo.moduleName)) {
      return;
    }
    createActivity();
    if (mvpInfo.isCreateMvpFuncture) {
      createContract();
      createPresenter();
    }
    createLayout();
    registerActivity();
    resetInfo();
  }

  //创建Activity
  private void createActivity() throws Exception {
    String preActPath = "/src/main/java/com/tantan/"; //前置类路径
    String actPrePath =
        buildSrcBuildDir + File.separator + mvpInfo.moduleName + preActPath
            + mvpInfo.moduleName;
    String className = mvpInfo.className + ".java";
    File file = new File(actPrePath, className);

    if (!file.exists()) {
      fileLastPath = MvpUtils.findClassPath(actPrePath, className);
    } else {
      fileLastPath = actPrePath;
    }

    classShortName = mvpInfo.className.replace("Activity", "").replace("Fragment", "");
    //login
    String layoutExtraPath = classShortName.toLowerCase();

    String lastPath = fileLastPath.replaceAll(actPrePath, "").replace(File.separator, ".");

    packageStr = "com.tantan." + mvpInfo.moduleName + lastPath;
    //需要创建新的文件夹
    if (mvpInfo.isCreateNewPackage) {
      packageStr = packageStr + "." + layoutExtraPath;
    }

    if (!MvpUtils.isEmptyStr(fileLastPath)) {
      File cFile = new File(fileLastPath, className);
      if (cFile.exists()) {
        cFile.delete();
      }
      if (mvpInfo.isCreateNewPackage) {
        cFile = new File(fileLastPath + File.separator + layoutExtraPath);
        cFile.mkdirs();
        cFile = new File(fileLastPath + File.separator + layoutExtraPath, className);
        fileLastPath = fileLastPath + File.separator + layoutExtraPath;
      }
      cFile.createNewFile();
      FileWriter writer = new FileWriter(cFile.getPath());
      writer.write("package " + packageStr + ";");
      MvpUtils.addNewLine(writer, true);
      //导包
      MvpUtils.addNewStrWithNewLine(writer,
          "import com.alibaba.android.arouter.facade.annotation.Route;", true);
      MvpUtils.addNewStrWithNewLine(writer, "import com.tantan.base.RouterConstants;", true);
      MvpUtils.addNewStrWithNewLine(writer, "import com.tantan." + mvpInfo.moduleName + ".R;",
          true);
      writer.write("import com.ziroom.base.");
      writer.write("BaseActivity");//activity
      writer.write(";");
      MvpUtils.addNewLine(writer, true);
      MvpUtils.addNewStrWithNewLine(writer, "/**", true);
      MvpUtils.addNewStrWithNewLine(writer, " * xx页", true);
      MvpUtils.addNewStrWithNewLine(writer, " */", true);
      //class类
      MvpUtils.addNewStrWithNewLine(writer, "@Route(path = RouterConstants.xx)", true);
      MvpUtils.addNewStrWithNewLine(writer, "public class ", false);
      MvpUtils.addNewStrWithNewLine(writer, mvpInfo.className, false);
      MvpUtils.addNewStrWithNewLine(writer, " extends ", false);
      MvpUtils.addNewStrWithNewLine(writer, "BaseActivity", false);
      if (mvpInfo.isCreateMvpFuncture) {
        MvpUtils.addNewStrWithNewLine(writer, "<" + classShortName + "Presenter> implements ",
            false);
        MvpUtils.addNewStrWithNewLine(writer, classShortName + "Contract.IView", false);
      }
      MvpUtils.addNewStrWithNewLine(writer, " {", false);
      MvpUtils.addNewLine(writer, true);
      MvpUtils.addNewStrWithNewLine(writer, "  @Override", true);
      //布局文件
      MvpUtils.addNewStrWithNewLine(writer, "  public int getLayoutId() {", true);
      MvpUtils.addNewStrWithNewLine(writer, "    return R.layout.", false);
      MvpUtils.addNewStrWithNewLine(writer, "activity", false);
      MvpUtils.addNewStrWithNewLine(writer, MvpUtils.humpToLine(classShortName), false);
      MvpUtils.addNewStrWithNewLine(writer, ";", true);
      MvpUtils.addNewStrWithNewLine(writer, "  }", false);
      //initViews
      MvpUtils.addNewLine(writer, true);
      MvpUtils.addNewStrWithNewLine(writer, "  @Override", true);
      MvpUtils.addNewStrWithNewLine(writer, "  public void initViews() {", true);
      MvpUtils.addNewStrWithNewLine(writer, "  }", true);

      if (mvpInfo.isCreateMvpFuncture) {
        MvpUtils.addNewLine(writer, false);
        //设置presenter
        MvpUtils.addNewStrWithNewLine(writer, "  @Override", true);
        MvpUtils.addNewStrWithNewLine(writer,
            "  public " + classShortName + "Presenter getPresenter() {", true);
        MvpUtils.addNewStrWithNewLine(writer,
            "    return new " + classShortName + "Presenter(this);", true);
        MvpUtils.addNewStrWithNewLine(writer, "  }", true);
      }

      MvpUtils.addNewStrWithNewLine(writer, "}", true);
      writer.close();
    }
  }

  //创建Contract
  private void createContract() throws Exception {
    String contractName = classShortName + "Contract";
    FileWriter writer = MvpUtils.createNewFileWriter(fileLastPath, contractName);
    writer.write("package " + packageStr + ";");
    MvpUtils.addNewLine(writer, true);
    //导包
    MvpUtils.addNewStrWithNewLine(writer, "import com.ziroom.mvp.IMvpView;", false);
    MvpUtils.addNewLine(writer, true);
    //class类
    MvpUtils.addNewStrWithNewLine(writer, "public interface " + contractName + " {", false);
    MvpUtils.addNewLine(writer, true);
    MvpUtils.addNewStrWithNewLine(writer, "  interface IView extends IMvpView {", false);
    MvpUtils.addNewLine(writer, true);
    MvpUtils.addNewStrWithNewLine(writer, "  }", false);
    MvpUtils.addNewLine(writer, true);
    MvpUtils.addNewStrWithNewLine(writer, "  interface IPresenter {", false);
    MvpUtils.addNewLine(writer, true);
    MvpUtils.addNewStrWithNewLine(writer, "  }", true);
    MvpUtils.addNewStrWithNewLine(writer, "}", true);
    writer.close();
  }

  //创建Presenter
  private void createPresenter() throws Exception {
    String presenterName = classShortName + "Presenter";
    String contractName = classShortName + "Contract";
    FileWriter writer = MvpUtils.createNewFileWriter(fileLastPath, presenterName);
    writer.write("package " + packageStr + ";");
    MvpUtils.addNewLine(writer, true);
    //导包
    MvpUtils.addNewStrWithNewLine(writer, "import " + packageStr + "." + contractName + ".IView;",
        true);
    MvpUtils.addNewStrWithNewLine(writer, "import com.ziroom.mvp.base.BaseMvpPresenter;", false);
    MvpUtils.addNewLine(writer, true);
    //class类
    MvpUtils.addNewStrWithNewLine(writer, "public class " + presenterName, false);
    MvpUtils.addNewStrWithNewLine(writer, " extends BaseMvpPresenter<", false);
    MvpUtils.addNewStrWithNewLine(writer, contractName + ".IView> implements", true);
    MvpUtils.addNewStrWithNewLine(writer, "    " + contractName + ".IPresenter {", false);
    MvpUtils.addNewLine(writer, true);
    //构造方法
    MvpUtils.addNewStrWithNewLine(writer, "  public " + presenterName + "(IView view) {", true);
    MvpUtils.addNewStrWithNewLine(writer, "    super(view);", true);
    MvpUtils.addNewStrWithNewLine(writer, "  }", true);
    MvpUtils.addNewStrWithNewLine(writer, "}", false);
    writer.close();
  }

  //创建布局文件
  private void createLayout() throws Exception {
    // /Users/guanzhen/androidproject/childstudy/login/src/main/res/layout/activity_regist.xml
    String preXmlPath = "/src/main/res/layout/";
    String xmlPath = buildSrcBuildDir + File.separator + mvpInfo.moduleName + preXmlPath;
    String xmlName = "activity" + MvpUtils.humpToLine(classShortName) + ".xml";
    File filePath = new File(xmlPath);
    if (!filePath.exists()) {
      filePath.mkdirs();
    }

    File file = new File(xmlPath, xmlName);
    if (file.exists()) {
      file.delete();
    }
    file.createNewFile();
    FileWriter writer = new FileWriter(file.getPath());
    MvpUtils.addNewStrWithNewLine(writer, "<?xml version=\"1.0\" encoding=\"utf-8\"?>", true);
    MvpUtils.addNewStrWithNewLine(writer,
        "<androidx.constraintlayout.widget.ConstraintLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"",
        true);
    MvpUtils.addNewStrWithNewLine(writer, "  android:layout_width=\"match_parent\"", true);
    MvpUtils.addNewStrWithNewLine(writer, "  android:layout_height=\"match_parent\">", true);
    MvpUtils.addNewStrWithNewLine(writer, "</androidx.constraintlayout.widget.ConstraintLayout>",
        false);
    writer.close();
  }

  //注册Activity
  private void registerActivity() throws Exception {
    // /Users/guanzhen/androidproject/childstudy/login/src/main/AndroidManifest.xml
    String packageName = "com.tantan." + mvpInfo.moduleName;
    String extraPath = packageStr.replace(packageName, "");
    String preManifestPath = "/src/main/";
    String manifestFilePath =
        buildSrcBuildDir + File.separator + mvpInfo.moduleName + preManifestPath;
    RandomAccessFile raf1 = new RandomAccessFile(manifestFilePath + "AndroidManifest.xml", "rw");
    String line = null;
    long lastPoint = 0;
    boolean isContainsApplication = false;
    boolean isRegistedClass = false;

    while ((line = raf1.readLine()) != null) {
      if (line.contains("</application>")) {
        isContainsApplication = true;
      }
      if (line.contains(mvpInfo.className)) {
        isRegistedClass = true;
      }
    }
    raf1.close();

    if (isRegistedClass) {//已经注册过
      return;
    }

    StringBuilder sb = new StringBuilder();
    if (!isContainsApplication) {
      sb.append("  <application>\n");
    }
    sb.append("\n");
    sb.append(
        "    <activity android:name=" + "\"" + extraPath + "." + mvpInfo.className + "\"");
    sb.append("\n");
    sb.append("      android:theme=\"@style/ActivityStyle\" />");
    sb.append("\n");
    sb.append("  </application>");
    sb.append("\n");
    sb.append("\n");
    sb.append("</manifest>");
    RandomAccessFile raf = new RandomAccessFile(manifestFilePath + "AndroidManifest.xml", "rw");
    while ((line = raf.readLine()) != null) {
      final long point = raf.getFilePointer();
      if (!isContainsApplication && line.contains("</manifest>")) {
        String str = line.replace("</manifest>", sb.toString());
        raf.seek(lastPoint);
        raf.writeBytes(str);
      } else if (isContainsApplication && line.contains("</application>")) {
        String str = line.replace("</application>", sb.toString());
        raf.seek(lastPoint);
        raf.writeBytes(str);
      }
      lastPoint = point;
    }
    raf.close();
  }

  //重置mvpInfo信息
  private void resetInfo() throws Exception {
    String extraPath = "buildSrc" + File.separator + "src" + File.separator + "main" +
        File.separator + "groovy";
    String packagePath =
        "com" + File.separator + "child" + File.separator + "plugins" + File.separator + "myplugin";
    String mvpInfoPath =
        getProject().getProjectDir().getParent() + File.separator + extraPath + File.separator
            + packagePath;
    RandomAccessFile rafFile = new RandomAccessFile(mvpInfoPath + File.separator + "MyMvpInfo.java",
        "rw");
    String line = null;
    int i = 0;
    List<String> headList = new ArrayList<>();
    List<String> mainList = new ArrayList<>();
    mainList.add("  public String preName = \"\";//前缀文件名");
    mainList.add("  public String moduleName = \"\";//模块名");
    mainList.add("  public String className = \"\";//类名");
    mainList.add("\n");
    mainList.add("  public boolean isCreateNewPackage = false;//是否新建包");
    mainList.add("  public boolean isCreateMvpFuncture = false;//是否创建mvp架构");
    mainList.add("\n");
    mainList.add("}");
    while ((line = rafFile.readLine()) != null) {
      if (i <= 6) {
        headList.add(new String(line.getBytes("ISO8859-1"), "UTF-8"));
      } else {
        break;
      }
      i++;
    }
    rafFile.close();
    File file = new File(mvpInfoPath, "MyMvpInfo.java");
    if (file.exists()) {
      file.delete();
    }
    file.createNewFile();
    FileWriter writer = new FileWriter(file.getPath());
    for (String s : headList) {
      MvpUtils.addNewStrWithNewLine(writer, s, true);
    }
    for (int j = 0; j < mainList.size(); j++) {
      MvpUtils.addNewStrWithNewLine(writer, mainList.get(j),
          j != mainList.size() - 1 && j != 3 && j != mainList.size() - 2);
    }
    writer.close();
  }
}
