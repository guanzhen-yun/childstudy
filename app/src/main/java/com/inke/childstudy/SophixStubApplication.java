package com.inke.childstudy;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Keep;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
/**
 * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
 * 此类必须继承自SophixApplication，onCreate方法不需要实现。
 * 此类不应与项目中的其他类有任何互相调用的逻辑，必须完全做到隔离。
 * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
 * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
 * 如有其它自定义改造，请咨询官方后妥善处理。
 */
public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";
    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(App.class)
    static class RealApplicationStub {}
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//         MultiDex.install(this);
        initSophix();
    }
    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData("333518348", "9f4ffbfa7f9d42ab9da3c9ea59c0465c", "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCrz8j5wmVN3uAwtyk8PCQ7bYaAQJfA5TG1UrRJW1gW5HxAGHBbuPw4aI/A7c/NGFsI4+mo5+Gs8o2CSni9g3Ys8B2Gb6G2j5YDV7jLuKSfphfYqbq8zziov24PPfhS6gc9EJiUQHUE1x7XkVCOhL/l0t1c0rqlorDlCpSFZpP+IKIzSJzIWCthuT5/4suHYAi+4sB7FD/XDHixlioOa56Qm1LzgtN/Aer+57tjDSgvcHUd8KYYFUF4k/x5wSQDAVx4qXUlpL/Yd8zUrjr1zSE+UXZAC08UAdwnV8vvAu+W6oLjTfTK/H9DHcUIWq/TDuFhl+g+XmhuBCuxRZf/dUJ1AgMBAAECggEAIYJfIkdF1tA3TYkLo9HfBzAvYWg3sMmCzmiY/Q2xupVJnn6lu13y41WRZAF92/R6HNvGwMio/UA5VUYNEnlHXTBfsc6n7aFHktNTGXijsveEAx/nadfIpzDe2F4vxowaaVCJUbDYod8LBuQyn/lILBzaS7ZV7OQ0OBe+K9570r5cuAcWGV0zeToYBPXUMgRHJBXhl5h7717tF8EWz24xlk7rttXImJ2GcHTMPBnjLHsUUNetKgqHxJ+rTo603c7pmfODf5/hMYUzPZSn5xalbceuYzJaiA4K8f50YKpL3nNZnuzfhSXaFtm/lGvoVi0HkC3flQP9Cz0kC67jLBv7AQKBgQDdInPiEUEp93ZMzucVnKDr/VnL6m9qplJUPcgNfICMeA/dCwu4sSbKDBtMCbxQBg7vVPruP+KDLNSizgYkX5MHVvP6O4wZlgstQbfAyXoQUisZRlsr+Qahu+TIUQDawq3KLvlJg2ECkq6LiEHTmpAmw9SvqEn0ttr+uVNjGQC61QKBgQDG5okPdyJI3fPtlaC4NApGXgO8BBWuTG3JJn4l2kepGSKsOrYBKPtQGFi9Ckg/Ldy4TC89KFtngsGOBRiKqeJtd/fIzRujJafueCrNGEkKqkZnpx36Hp1vsaPV2bf3aam2nbBjnleG2XSVYt3DEEqKccCAgnX87IMdz4z3mBT5IQKBgG6xnlyd0obVbwyHzblNzGgVTGv9EITMeQ3eV1QY7KhkOVym+NkdJJOjd86aWP+d6L/5RBsbM6ICQPa2NvDiPtE/v6WVRU5Rg4rGCNuif03IM/bU9zKN17oCOkXj9o+LQD+jP/qCn38QOfty27C3AE3HXaIIley/0+QKlSlYDLt5AoGAe5wgB915ECHoC3oW9QNac1rQJVPAiZ70B7OFaE7chCmb0791BwmKDb/jvjAasDD3YA3wuahQDedEFNfz5ZEop/GLq15U07J+SASkTOvP0PH7aUWBqsTGAPk8IZX576faEiSzz2VDbgnMm15jBWwldx60jOkHHJV78tXe/KymNCECgYBpVYOjV6zU8ScJZlskkJ6Dqepvxn1zDeccCqYbkgSLrHA/JBxyJWeSJWyzldnevbU35R6r0LfKO2UUj0/8vc1nHt7GNrZFppV7G5YnkjUaNFZDsjJWnwFDBVC+qYRffWff9uNVshBT8gtvsSPix9T+R2FqD9ofEf5BtOHJ25HoGw==")
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                        }
                    }
                }).initialize();
    }
}
