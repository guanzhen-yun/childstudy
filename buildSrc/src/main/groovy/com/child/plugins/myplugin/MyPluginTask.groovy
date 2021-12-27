package com.child.plugins.myplugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class MyPluginTask extends DefaultTask {
    MyPluginTask() {
        group = 'selftask'
        description = 'my plugin task'
    }
    //相当于Task的执行入口
    @TaskAction
    void doAction() {
        String buildSrcBuildDir = project.projectDir.parent
        println buildSrcBuildDir
        exec("${buildSrcBuildDir}" + File.separator + "test.bat");
    }

    //执行bat文件 path bat文件路径
    void exec(String path) {
        Runtime.getRuntime().exec(path)
    }
}