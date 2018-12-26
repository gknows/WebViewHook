package io.github.gknows.webviewhook;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by gknows on 2018/12/26.
 */

public class WebViewHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        final String packageName = lpparam.packageName;
        XposedBridge.log("WebViewHook Load: " + packageName);

        XposedBridge.hookAllConstructors(WebView.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                XposedHelpers.callStaticMethod(WebView.class, "setWebContentsDebuggingEnabled", true);
            }
        });

        XposedBridge.hookAllMethods(WebView.class, "setWebContentsDebuggingEnabled", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                param.args[0] = true;
            }
        });

        XposedHelpers.findAndHookMethod("android.webkit.WebView", lpparam.classLoader, "setWebViewClient", WebViewClient.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                param.args[0] = new InjectJSWebViewClient();
            }
        });
    }
}
