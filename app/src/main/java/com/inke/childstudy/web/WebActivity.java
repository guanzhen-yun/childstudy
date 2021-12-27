package com.inke.childstudy.web;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.inke.childstudy.R;
import com.inke.childstudy.entity.Child;
import com.inke.childstudy.routers.RouterConstants;
import com.inke.childstudy.utils.BmobUtils;
import com.ziroom.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RouterConstants.App.Web)
public class WebActivity extends BaseActivity {
    @BindView(R.id.webview)
    WebView webview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initViews() {
        webview.setWebViewClient(webViewClient);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDefaultTextEncodingName("utf-8");//文本编码
        webview.getSettings().setDomStorageEnabled(true);//设置DOM存储已启用（注释后文本显示变成js代码）
//        webview.loadUrl("http://192.168.2.12:8080/testjsp/childstudyinfo.jsp");
        webview.loadUrl("file:///android_asset/childstudyinfo.html");
        webview.addJavascriptInterface(new MyJavascriptInterface(this), "injectedObject");

//        webview.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                final WebView.HitTestResult hitTestResult = webview.getHitTestResult();
//                // 如果是图片类型或者是带有图片链接的类型
//                if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
//                        hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
//                    // 弹出保存图片的对话框
//                    new AlertDialog.Builder(WebActivity.this)
//                            .setItems(new String[]{"查看大图", "保存图片到相册"}, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    String picUrl = hitTestResult.getExtra();
//                                    //获取图片
//                                    Log.e("picUrl", picUrl);
//                                    switch (which) {
//                                        case 0:
//                                            break;
//                                        case 1:
//                                            break;
//                                        default:
//                                            break;
//                                    }
//                                }
//                            })
//                            .show();
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            // 无参数调用
//            webview.loadUrl("javascript:javacalljs()");
            // 传递参数调用
            Child currentLoginChild = BmobUtils.getInstance().getCurrentLoginChild();
            webview.loadUrl("javascript:javacalljswithargs('" + currentLoginChild.getNickname() + "妈妈')");

            // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
//            webview.loadUrl("javascript:(function(){" +
//                    "var objs = document.getElementsByTagName(\"img\");" +
//                    "for(var i=0;i<objs.length;i++)" +
//                    "{" +
//                    "objs[i].onclick=function(){window.injectedObject.imageClick(this.getAttribute(\"src\"));}" +
//                    "}" +
//                    "})()");
            super.onPageFinished(view, url);
        }
    };

    /**
     * 4.4以上可用 evaluateJavascript 效率高
     */
//    private void load(String jsCode) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            evaluateJavascript(jsCode, null);
//        } else {
//            loadUrl(jsCode);
//        }
//    }

    @OnClick(R.id.tv_back)
    public void onClickView() {
        finish();
    }
}
