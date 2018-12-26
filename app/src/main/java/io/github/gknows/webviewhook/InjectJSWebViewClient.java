package io.github.gknows.webviewhook;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by gknows on 2018/12/26.
 */

public class InjectJSWebViewClient extends WebViewClient {

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        String jsfile = "http://gknows.github.io/app/config/inject.js?t="+String.valueOf(System.currentTimeMillis());
        String js = "var newscript = document.createElement(\"script\");";
        js += "newscript.src=\""+ jsfile + "\";";
        js += "(document.head || document.documentElement).appendChild(newscript);";
        view.loadUrl("javascript:" + js);
    }
}
