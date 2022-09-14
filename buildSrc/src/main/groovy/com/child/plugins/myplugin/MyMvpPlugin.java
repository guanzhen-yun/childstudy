package com.child.plugins.myplugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * mvp插件类
 */
public class MyMvpPlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    project.getTasks().create("myMvpPluginModel", MyMvpPluginTask.class);
  }
}
